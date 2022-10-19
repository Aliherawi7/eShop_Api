package com.eshop.filter;

import com.eshop.model.UserApp;
import com.eshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomAuthenticationFilterTest {
    private CustomAuthenticationFilter underTest;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserService userService;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse res;
    @Mock
    private Authentication authentication;
    @Mock
    FilterChain chain;
    @Mock
    ServletOutputStream servletOutputStream;

//
//    @BeforeEach
//    void setUp() {
//        underTest = new CustomAuthenticationFilter(authenticationManager, userService);
//    }

    @Test
        // attempt authentication when user is not lock
    void attemptAuthenticationTestIfUserIsNotLock() {
        //  given
        String email = "aliherawi7@gmail.com";
        String password = "1234";
        UserApp user = new UserApp();
        user.setEmail(email);
        user.setPassword(password);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        //  when
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("password")).thenReturn(password);
        when(userService.getUser(email)).thenReturn(user);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);

        //  then
        UserApp userTest = (UserApp) underTest.attemptAuthentication(req, res).getPrincipal();
        assertEquals(userTest.getEmail(), email);
        verify(userService).getUser(email);
        verify(userService).isAccountLocked(user);
        verify(req).getParameter("email");
        verify(req).getParameter("password");
        verify(authenticationManager).authenticate(authenticationToken);
        verify(authentication).getPrincipal();
    }

    @Test
        // attempt authentication when user is lock
    void attemptAuthenticationTestIfUserIsLock() {
        //  given
        String email = "aliherawi7@gmail.com";
        String password = "1234";
        UserApp user = new UserApp();
        user.setEmail(email);
        user.setPassword(password);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        //  when
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("password")).thenReturn(password);
        when(userService.getUser(email)).thenReturn(user);
        when(userService.isAccountLocked(user)).thenReturn(true);

        //  then

        assertThrows(RuntimeException.class, () -> underTest.attemptAuthentication(req, res));
        verify(userService).getUser(email);
        verify(userService).isAccountLocked(user);
        verify(req).getParameter("email");
        verify(req).getParameter("password");
    }

    @Test
        // attempt authentication when user is lock
    void attemptAuthenticationTestIfUserIsLockButLockTimeHasExpired() {
        //  given
        String email = "aliherawi7@gmail.com";
        String password = "1234";
        UserApp user = new UserApp();
        user.setEmail(email);
        user.setPassword(password);
        user.setLockTime(new Date(System.currentTimeMillis() - 10000));
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        //  when
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("password")).thenReturn(password);
        when(userService.getUser(email)).thenReturn(user);
        when(userService.isAccountLocked(user)).thenReturn(true);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);


        //  then
        UserApp userTest = (UserApp) underTest.attemptAuthentication(req, res).getPrincipal();
        assertEquals(userTest.getEmail(), email);
        verify(userService).getUser(email);
        verify(userService).unlockWhenTimeExpired(user);
        verify(userService).isAccountLocked(user);
        verify(req).getParameter("email");
        verify(req).getParameter("password");
    }


    @Test
        //  if user failed attempts is greater than zero
    void successfulAuthenticationIfFailedAttemptsIsGreaterThanZero() {
        //given
        String email = "aliherawi7@gmail.com";
        String password = "1234";
        UserApp user = new UserApp();
        user.setEmail(email);
        user.setPassword(password);
        user.setFailedAttempt(3);
        Collection<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));
        User testUser = new User(email, password, authorities);

        //when
        when(authentication.getPrincipal()).thenReturn(testUser);
        when(userService.getUser(email)).thenReturn(user);
        when(req.getRequestURL()).thenReturn(new StringBuffer("api-url"));
        try {
            when(res.getOutputStream()).thenReturn(servletOutputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //then
        try {
            underTest.successfulAuthentication(req, res, chain, authentication);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        verify(userService).resetFailedAttempts(email);

    }

    @Test
        //  create access token and refresh token
    void successfulAuthentication() {
        //given
        String email = "aliherawi7@gmail.com";
        String password = "1234";
        UserApp user = new UserApp();
        user.setEmail(email);
        user.setPassword(password);
        Collection<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));
        User testUser = new User(email, password, authorities);

        //when
        when(authentication.getPrincipal()).thenReturn(testUser);
        when(userService.getUser(email)).thenReturn(user);
        when(req.getRequestURL()).thenReturn(new StringBuffer("api-url"));
        try {
            when(res.getOutputStream()).thenReturn(servletOutputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //then
        try {
            underTest.successfulAuthentication(req, res, chain, authentication);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // if response content type set to application/json
        verify(res).setContentType(MediaType.APPLICATION_JSON_VALUE);

    }

    @Test
    @Disabled
    void unsuccessfulAuthentication() {

    }
}

