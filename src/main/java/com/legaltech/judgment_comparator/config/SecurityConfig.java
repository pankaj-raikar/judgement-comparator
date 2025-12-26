package com.legaltech.judgment_comparator.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.h2.server.web.JakartaWebServlet;

/**
 * Security Configuration for Spring Boot 4.0
 * Includes H2 Console servlet registration
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Register H2 Console Servlet (Spring Boot 4.0 compatible)
     * Uses JakartaWebServlet instead of WebServlet
     */
    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
    public ServletRegistrationBean<JakartaWebServlet> h2Console() {
        ServletRegistrationBean<JakartaWebServlet> registration = new ServletRegistrationBean<>(
                new JakartaWebServlet());
        registration.addUrlMappings("/h2-console/*");
        return registration;
    }

    /**
     * Security configuration
     * - Disables CSRF for development
     * - Allows H2 console frames
     * - Permits all requests without authentication
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection
                .csrf(csrf -> csrf.disable())

                // Allow all requests
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // Explicitly allow H2 console
                        .anyRequest().permitAll())

                // Allow frames for H2 console UI
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}
