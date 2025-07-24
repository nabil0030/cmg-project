package com.cmg.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                                // 🎯 Pages HTML autorisées sans login
                                "/", "/home", "/home.html",
                                "/signup", "/login",
                                "/index.html", "/favicon.ico",
                                "/css/**", "/js/**", "/images/**",

                                // Pages spécifiques de ton projet
                                "/synthese.html",
                                "/rapportHajjar.html",
                                "/cbulkArgentifere.html",
                                "/chauxVive.html",
                                "/expZincSafi.html",
                                "/chauxViveLafarge.html",
                                "/controleBasculeHJDS.html",
                                "/controle-bascule-hjds.html",  // 🧠 important si le fichier est en minuscules
                                "/controle-bascule-ka.html",
                                "/dsClassique.html",
                                "/dsNord.html",
                                "/expZincOncf.html",
                                "/expCuivreOncf.html",
                                "/expCuivreNord.html",
                                "/expPbCmgOnf.html"
                        ).permitAll()

                        .requestMatchers(
                                // 🎯 Tous les endpoints REST publics
                                "/api/cbulkargentifere/**",
                                "/api/chauxVive/**",
                                "/api/chaux-vive/**",
                                "/api/chauxViveLafarge/**",
                                "/api/bascule-hj-ds/**",
                                "/api/ka/**",
                                "/api/ds-classique/**",
                                "/api/ds-classique/bl/**",     // ✅ recherche BL autorisée
                                "/api/dsnord/**",
                                "/api/expzinconcf/**",
                                "/api/exp-zinc-safi/**",
                                "/api/exp-cuivre-oncf/**",
                                "/api/exp-cuivre-nord/**",
                                "/api/exp-pb-cmg-onf/**",
                                "/api/synthese-jour",
                                "/api/synthese-mois",
                                "/api/synthese-annuelle",
                                "/api/rapport-hajjar"
                        ).permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/rapport-hajjar").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home.html", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/home")
                        .permitAll()
                )
                .build();
    }
}
