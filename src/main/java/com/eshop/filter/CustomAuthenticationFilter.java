package com.eshop.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.eshop.dto.LoginInformationDTO;
import com.eshop.dto.UserInformationDTO;
import com.eshop.model.UserApp;
import com.eshop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // need authenticationManager instance
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService){
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = request.getParameter("email").trim().toLowerCase();
        String password = request.getParameter("password");

        UserApp user = null;
        if(email != null)
            user = userService.getUser(email);

        // check if user is not lock and reach to limit attempt failed. the user can't attempt to login
        if(user != null){

            if(userService.isAccountLocked(user) ){
                long remainMillisSecond = user.getLockTime().getTime() - System.currentTimeMillis();

                // if user lock time is finished
                if(remainMillisSecond <= 0){
                    userService.unlockWhenTimeExpired(user);

                 // if user lock time is remained yet
                }else if(remainMillisSecond > 0){
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setHeader("error_message", "Your account is lock now try after expire date");
                    response.setHeader("lock_expireDate", user.getLockTime().toString());
                    throw new RuntimeException("Your account is lock now try after expire date. try after: "+ user.getLockTime().toString());
                }
            }

        }
        UsernamePasswordAuthenticationToken authenticateToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticateToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // we need the principal or authenticated user to create JWT token with its information
        User user = (User) authResult.getPrincipal();
         /*
         * if user was locked dou to limit attempt failed.
         * we have to unlock the user and reset the failedAttempt
         * */
        UserApp checkUserActivation = userService.getUser(user.getUsername());
        //if user has failed attempts. then reset the failed attempt to zero
        if(checkUserActivation.getFailedAttempt()>0){
            userService.resetFailedAttempts(checkUserActivation.getEmail());
        }

        //check if user is locked. then unlock the user
        if(userService.isAccountLocked(checkUserActivation)){
            userService.unlockWhenTimeExpired(checkUserActivation);
        }

        //we an algorithm to to build the token with
        Algorithm algorithm = Algorithm.HMAC256("herawi".getBytes());
        //then we create the access token and refresh token using auth0 library
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24*10)) // 10 days
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60*24*20)) // 20 days
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        // user common information
        UserInformationDTO userInformationDTO = new UserInformationDTO(
                checkUserActivation.getName(), checkUserActivation.getLastName(),
                checkUserActivation.getImgUrl(), checkUserActivation.getEmail());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);
        // login information json array
        LoginInformationDTO loggingInfo = new LoginInformationDTO(access_token, refresh_token, userInformationDTO);
        new ObjectMapper().writeValue(response.getOutputStream(),loggingInfo);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String email = request.getParameter("email");
        UserApp user = userService.getUser(email);
        //check user exist
        if(user != null){
            // if user account is locked
            if(userService.isAccountLocked(user)){
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setHeader("limit_attempt", "Your account is lock now try after expire date");
                response.setHeader("lock_expireDate", user.getLockTime().toString());
                response.setHeader("error_message",failed.getMessage());
                response.setHeader("remained_attempts",(UserService.MAX_FAILED_ATTEMPTS - user.getFailedAttempt())+" attempts chance");
                response.setHeader("failed_attempts",user.getFailedAttempt()+" failed attempts");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                return;
            }
            //check if user is not locked increase failed attempts
            if(!userService.isAccountLocked(user)){
                userService.increaseFailedAttempts(user);
                user = userService.getUser(user.getEmail());
            }
            if(user.getFailedAttempt() >= UserService.MAX_FAILED_ATTEMPTS){
                userService.lock(user);
                user = userService.getUser(user.getEmail());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setHeader("limit_attempt", "Your account is lock now try after expire date");
                response.setHeader("error_message",failed.getMessage());
                response.setHeader("remained_attempts",(UserService.MAX_FAILED_ATTEMPTS - user.getFailedAttempt())+" attempts chance");
                response.setHeader("failed_attempts",user.getFailedAttempt()+" failed attempts");
                response.setHeader("lock_expireDate", user.getLockTime().toString());
                return;
            }
        }

        response.setHeader("error_message",failed.getMessage());
        response.setHeader("remained_attempts",(UserService.MAX_FAILED_ATTEMPTS - user.getFailedAttempt())+" attempts chance");
        response.setHeader("failed_attempts",user.getFailedAttempt()+" failed attempts");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }
}
