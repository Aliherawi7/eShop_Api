package com.eshop.security;

import com.eshop.filter.CustomAuthenticationFilter;
import com.eshop.filter.CustomAuthorizationFilter;
import com.eshop.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public SecurityConfiguration(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService){
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter authenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean(),userService);
        authenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().antMatchers("/api/login").permitAll();
        http.authorizeHttpRequests().antMatchers("/api/products").permitAll();
        http.authorizeHttpRequests().antMatchers(HttpMethod.POST,"/api/products/save")
                .hasAuthority("ADMIN");
        http.authorizeHttpRequests().antMatchers(HttpMethod.DELETE,"/api/products/delete")
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

}
