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
                                "/", "/home", "/home.html",
                                "/signup", "/login",
                                "/css/**", "/js/**", "/images/**",

                                // Pages accessibles directement
                                "/index.html",
                                "/synthese.html",                    // ✅ synthèse
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

                                // API autorisées
                                "/api/cbulkargentifere/**",
                                "/api/chauxVive/**",
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
                                "/api/chaux-vive/**",

                                // Synthèse API
                                "/api/synthese-jour",
                                "/api/synthese-mois",
                                "/api/synthese-annuelle"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/home")
                        .permitAll()
                )
                .build();
    }

}
