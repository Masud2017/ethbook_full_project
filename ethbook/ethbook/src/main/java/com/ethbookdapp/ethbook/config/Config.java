package com.ethbookdapp.ethbook.config;

import com.ethbookdapp.ethbook.filters.JWTRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class Config {
    @Autowired
    private AuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JWTRequestFilter jwtRequestFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain filterChain  (HttpSecurity http) throws Exception {
        http.cors(cors-> cors.disable());

        http.authorizeHttpRequests(
                req-> req
                        .requestMatchers("/index","/users").permitAll()
                        .anyRequest().authenticated()
        );

//        http.logout(logout -> logout.logoutSuccessUrl("/index"));
//        http.formLogin(formLogin-> formLogin.loginPage("/login"));

        // update for spring boot 3
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedPage("/error"));
        http.sessionManagement((sessionManagmentObj -> sessionManagmentObj.sessionCreationPolicy(SessionCreationPolicy.STATELESS)));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }
}
