package com.example.myFashionTrunk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Configuration Class for security settings for development purposes
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Allowed origins for CORS
     */
    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    /** * Configures the security filter chain.
     * It disables all csrf protection and allows any Http requests without authentication
     *  @param http The HttpSecurity object to configure.
     *  @return The configured SecurityFilterChain.
     *  @throws Exception If an error occurs during configuration. */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    /**
     * Configures CORS setting
     * Allows CORS requests to all endpoints and the specified Http methods allowed
     * @return WebMvcConfigurer that configures CORS mapping
     * **/
    @Bean public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins(allowedOrigins)
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }
    };
    }
}
