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
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", // root URL that redirects to /home.html
                                "/home",          // ✅ autorise l'accès à http://localhost:8081/home
                                "/home.html",   // ✅ public landing page
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
                                "/login",      // ✅ allow login GET
                                "/signup",     // ✅ allow signup GET

                                // Permitted API Endpoints
                                "/api/cbulkargentifere/**",
                                "/api/chauxVive/**",

                                "/chauxVive/add",
                                "/chauxVive/update",
                                "/chauxVive/delete/**",
                                "/api/chauxViveLafarge/**",
                                "/api/bascule-hj-ds/**",
                                "/api/ka/**",
                                "/api/ds-classique/**",
                                "/api/dsnord/**",
                                "/api/expzinconcf/**",
                                "/api/exp-zinc-safi/**",
                                "/api/exp-cuivre-oncf/**",
                                "/api/exp-cuivre-nord/**",
                                "/api/exp-pb-cmg-onf/**",
                                "/api/chaux-vive/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/home") // ✅ redirect to landing after logout
                        .permitAll()
                )
                .build();
    }
}
