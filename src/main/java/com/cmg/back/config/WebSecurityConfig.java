package com.cmg.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // during development
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/signup",
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/cbulkArgentifere.html",
                                "/images/**",
                                "/index.html",
                                "/chauxVive.html",
                                "/cbulkArgentifere.html",
                                "/chauxViveLafarge.html",
                                "/controle-bascule-hjds.html",
                                "/controle-bascule-ka.html",
                                "/dsClassique.html",
                                "/dsNord.html",
                                "/expZincOncf.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .build();
    }
}
