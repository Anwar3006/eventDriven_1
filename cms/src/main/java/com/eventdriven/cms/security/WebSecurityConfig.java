package com.eventdriven.cms.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.eventdriven.cms.security.session.JwtValidator;
import com.eventdriven.cms.services.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig {
    
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtValidator jwtValidator;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(crsf -> crsf.disable())
            .cors(cors -> cors.configurationSource(corsConfigSource()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtValidator, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**").permitAll()
                                                     .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                                                     .anyRequest().authenticated()
            )
            .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder.bCryptPasswordEncoder());
        
        return authManagerBuilder.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigSource(){
        return (HttpServletRequest request) -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:3000", "*"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(List.of("*"));
            config.setMaxAge(3600L);
            return config;
        };
    }
}
