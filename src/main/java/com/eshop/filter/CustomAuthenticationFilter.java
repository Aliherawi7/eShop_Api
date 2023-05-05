package com.eshop.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.eshop.constants.APIEndpoints;
import com.eshop.dto.LoginInformationDTO;
import com.eshop.dto.UserInformationDTO;
import com.eshop.model.UserApp;
import com.eshop.repository.OrderRepository;
import com.eshop.service.UserService;
import com.eshop.utils.BaseURI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // need authenticationManager instance
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final OrderRepository orderRepository;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
                                      UserService userService,
                                      OrderRepository orderRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.err.println("auth type:" + request.toString());
        String email = request.getParameter("email");
        System.out.println(email + "before if");
        String password = request.getParameter("password");
        UserApp user = null;

        if (email != null) {
            email = email.trim().toLowerCase();
            System.out.println(email + "in if");
            user = userService.getUser(email);

        }
        // check if user is not lock and reach to limit attempt failed. the user can't attempt to login
        if (user != null) {

            if (userService.isAccountLocked(user)) {
                long remainMillisSecond = user.getLockTime().getTime() - System.currentTimeMillis();

                // if user lock time is finished
                if (remainMillisSecond <= 0) {
                    userService.unlockWhenTimeExpired(user);

                    // if user lock time is remained yet
                } else {
                    Map<String, String> messages = new HashMap<>();
                    messages.put("error_message", "Your account is lock now try after expire date");
                    messages.put("lock_expireDate", user.getLockTime().toString());
                    response.setHeader("error_message", "Your account is lock now try after expire date");
                    response.setHeader("lock_expireDate", user.getLockTime().toString());
                    try {
                        new ObjectMapper().writeValue(response.getOutputStream(), messages);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    throw new RuntimeException("Your account is lock now try after expire date. try after: " + user.getLockTime().toString());
                }
            }

        }
        UsernamePasswordAuthenticationToken authenticateToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticateToken);
    }

    @Override
    @ResponseBody
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException, IOException {
        // we need the principal or authenticated user to create JWT token with its information
        User user = (User) authResult.getPrincipal();

        /*
         * if user was locked, dou to limit attempt failed.
         * we have to unlock the user and reset the failedAttempt
         * */
        UserApp checkUserActivation = userService.getUser(user.getUsername());

        //if user has failed attempts. then reset the failed attempt to zero
        if (checkUserActivation.getFailedAttempt() > 0) {
            userService.resetFailedAttempts(checkUserActivation.getEmail());
        }

        /*check if user is locked. then unlock the user
        if(userService.isAccountLocked(checkUserActivation)){
            userService.unlockWhenTimeExpired(checkUserActivation);
        }*/

        //we have an algorithm to to build the token with
        Algorithm algorithm = Algorithm.HMAC256("Bearer".getBytes());
        //then we create the access token and refresh token using auth0 library
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 10)) // 10 days
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 20)) // 20 days
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        // user location and ip
        // String ipAddress = IPFinderService.getClientIP(request);
        String countryName = "Unknown";// IPFinderService.getCountryName(ipAddress);
        // user common information
        double totalSpending = 0;
        int totalOrder = 0;
        if (orderRepository.findAllByUserId(checkUserActivation.getId()) != null) {
            totalOrder = orderRepository.findAllByUserId(checkUserActivation.getId()).size();
//            totalSpending = orderRepository.findAllByUserId(
//                    checkUserActivation.getId())
//                    .stream().mapToDouble(OrderApp::getAmount).sum();
        }
        String baseURI = BaseURI.getBaseURI(request);
        UserInformationDTO userInformationDTO = new UserInformationDTO(
                checkUserActivation.getId(),
                checkUserActivation.getName(),
                checkUserActivation.getLastName(),
                baseURI + APIEndpoints.USER_PICTURE.getValue() + checkUserActivation.getId(),
                checkUserActivation.getEmail(),
                user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()),
                countryName, totalOrder, totalSpending, checkUserActivation.isEnabled()
        );
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);
        // login information json array
        LoginInformationDTO loggingInfo = new LoginInformationDTO(access_token, refresh_token, userInformationDTO);
        new ObjectMapper().writeValue(response.getOutputStream(), loggingInfo);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String email = request.getParameter("email").trim().toLowerCase();
        UserApp user = userService.getUser(email);
        Map<String, String> responseMassage = new HashMap<>();
        //check user exist
        if (user != null) {
            // if user account is locked
            if (userService.isAccountLocked(user)) {
                responseMassage.put("limit_attempt", "Your account is lock now try after expire date");
                responseMassage.put("lock_expireDate", user.getLockTime().toString());
                responseMassage.put("error_message", failed.getMessage() + ", Your account is lock now ");
                responseMassage.put("remained_attempts", (UserService.MAX_FAILED_ATTEMPTS - user.getFailedAttempt()) + " attempts chance");
                responseMassage.put("failed_attempts", user.getFailedAttempt() + " failed attempts");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), responseMassage);
                return;
            }
            //check if user is not locked increase failed attempts
            else {
                userService.increaseFailedAttempts(user);
                user = userService.getUser(user.getEmail());
            }
            if (user.getFailedAttempt() >= UserService.MAX_FAILED_ATTEMPTS) {
                userService.lock(user);
                user = userService.getUser(user.getEmail());
                responseMassage.put("limit_attempt", "Your account is lock now try after expire date");
                responseMassage.put("error_message", "Your account is lock now try after expire date");
                responseMassage.put("remained_attempts", (UserService.MAX_FAILED_ATTEMPTS - user.getFailedAttempt()) + " attempts chance");
                responseMassage.put("failed_attempts", user.getFailedAttempt() + " failed attempts");
                responseMassage.put("lock_expireDate", user.getLockTime().toString());
                response.setHeader("error_message", failed.getMessage());
                new ObjectMapper().writeValue(response.getOutputStream(), responseMassage);
                return;
            }

            System.out.println("password is wrong");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("error_message", "password is wrong");
            responseMassage.put("error_message", "password is wrong");
            responseMassage.put("remained_attempts", (UserService.MAX_FAILED_ATTEMPTS - user.getFailedAttempt()) + " attempts chance");
            responseMassage.put("failed_attempts", user.getFailedAttempt() + " failed attempts");
            new ObjectMapper().writeValue(response.getOutputStream(), responseMassage);

        } else {
            System.out.println("email not found");
            response.setHeader("error_message", "email not found");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            responseMassage.put("error_message", "email not found");
            new ObjectMapper().writeValue(response.getOutputStream(), responseMassage);
        }

    }
}
