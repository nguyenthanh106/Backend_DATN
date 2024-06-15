package com.booking.sercurity;

import com.booking.jwt.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationEntryPoint customEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                            config.setAllowedMethods(Arrays.asList("*"));
                            config.setAllowedHeaders(Arrays.asList("*"));
                            config.setAllowCredentials(true);
                            config.setMaxAge(3600L);
                            return config;
                        }))
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth ->
                        auth
                                .requestMatchers(
                                        "/api/login",
                                        "/api/signup",
                                        "/api/get",
                                        "/api/add-role",
                                        "/api/cart",
                                        "/api/cart/**",
                                        "/api/cart/all",
                                        "/api/order",
                                        "/api/order/create",
                                        "/api/order/all",
                                        "/api/order/**",
                                        "/api/order/byName/**",
                                        "/api/order/byPhoneNumber/**",
                                        "/api/rooms", "/api/rooms/**",
                                        "/api/user","/api/user/**",
                                        "/api/owners","/api/owners/**",
                                        "/api/images","/api/images/**",
                                        "/api/categories","/api/categories/**",
                                        "/api/booking-orders","/api/booking-orders/**"
                                ).permitAll()
                                .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                                .anyRequest().authenticated())
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(customEntryPoint))
        ;
        return http.build();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }
}