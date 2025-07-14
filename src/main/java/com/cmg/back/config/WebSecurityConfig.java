package com.cmg.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                .csrf(csrf -> csrf.disable()) // DURING DEVELOPMENT ONLY - re-enable for production!
                .authorizeHttpRequests(auth -> auth
                        // Permitted HTML pages and static resources
                        .requestMatchers(
                                "/signup",
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/index.html",
                                "/cbulkArgentifere.html",
                                "/chauxVive.html",
                                "/expZincSafi.html",
                                "/chauxViveLafarge.html",
                                "/controle-bascule-hjds.html",
                                "/controle-bascule-ka.html",
                                "/dsClassique.html",
                                "/dsNord.html",
                                "/expZincOncf.html",
                                "/expCuivreOncf.html",
                                "/expCuivreNord.html",
                                "/expPbCmgOnf.html",


                                // Permitted API Endpoints
                                "/api/cbulkargentifere/**",
                                "/api/chauxVive/**",
                                "/chauxVive/add", // Specific API endpoint not under /api
                                "/chauxVive/update", // Specific API endpoint not under /api
                                "/chauxVive/delete/**", // Specific API endpoint not under /api
                                "/api/chauxViveLafarge/**",
                                "/api/bascule-hj-ds/**",
                                "/api/ka/**",
                                "/api/ds-classique/**",
                                "/api/dsnord/**",
                                "/api/expzinconcf/**",
                                "/api/exp-zinc-safi/**",
                                "/api/exp-cuivre-oncf/**",
                                "/api/exp-cuivre-nord/**",
                                "/api/exp-pb-cmg-onf/**"
                        ).permitAll()
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .build();
    }
}