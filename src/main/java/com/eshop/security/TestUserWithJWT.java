package com.eshop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.eshop.repository.UserAppRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class TestUserWithJWT {
    private final UserAppRepository userAppRepository;
    public TestUserWithJWT(UserAppRepository userAppRepository){
        this.userAppRepository = userAppRepository;
    }
    public boolean testJWTOfUser(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            try {
                Algorithm algorithm = Algorithm.HMAC256("herawi".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(authorizationHeader);
                String email = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                Arrays.stream(roles).forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }else {
            return false;
        }
    }
}