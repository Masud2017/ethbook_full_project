package com.ethbookdapp.ethbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Beans {
    @Bean
    public PasswordEncoder getPassWordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
