package com.eshop.security;

import com.eshop.filter.CustomAuthenticationFilter;
import com.eshop.filter.CustomAuthorizationFilter;
import com.eshop.repository.OrderRepository;
import com.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OrderRepository orderRepository;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder,
                                 UserService userService,
                                 OrderRepository orderRepository) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter authenticationFilter =
                new CustomAuthenticationFilter(
                        authenticationManagerBean(),
                        userService,
                        orderRepository);
        authenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().antMatchers("/").permitAll();
        http.authorizeHttpRequests().antMatchers("/api/login", "/api/users/signup").permitAll();
        http.authorizeHttpRequests().antMatchers("/api/favorites");
        http.authorizeHttpRequests().antMatchers(HttpMethod.GET, "/api/products/pagination/*/*",
                "/api/products/*",
                "/api/brands",
                "/api/brands/*",
                "/api/comments/products/*", "/api/v1/files/**").permitAll();
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/products/save")
                .hasAuthority("ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE, "/api/products/delete")
                .hasAuthority("ADMIN");
        http.authorizeHttpRequests().antMatchers("/api/users").hasAuthority("ADMIN");
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedMethod("GET");
        corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("PUT");
        corsConfig.addAllowedMethod("DELETE");
        corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type"));
        corsConfig.setExposedHeaders(Collections.singletonList("X-Get-Header"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }


}
