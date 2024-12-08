package com.example.myFashionTrunk.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration for user related beans
 * **/
@Configuration
public class UserConfig {

    /**
     * Creates a PasswordEncoder bean that uses BCrypt for hashing
     * @return PasswordEncoder
     * **/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
