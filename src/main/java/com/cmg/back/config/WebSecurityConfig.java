package com.cmg.back.config;

import com.cmg.back.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/home", "/home.html",
                                "/signup", "/login",
                                "/index.html", "/favicon.ico",
                                "/css/**", "/js/**", "/images/**",
                                "/synthese.html", "/rapportHajjar.html",
                                "/cbulkArgentifere.html", "/chauxVive.html",
                                "/expZincSafi.html", "/chauxViveLafarge.html",
                                "/controleBasculeHJDS.html", "/controle-bascule-hjds.html",
                                "/controle-bascule-ka.html", "/dsClassique.html",
                                "/dsNord.html", "/expZincOncf.html", "/expCuivreOncf.html",
                                "/expCuivreNord.html", "/expPbCmgOnf.html"
                        ).permitAll()
                        .requestMatchers(
                                "/api/cbulkargentifere/**",
                                "/api/synthese-cumul-annuel-groupes",
                                "/api/chauxVive/**", "/api/chaux-vive/**",
                                "/api/chauxViveLafarge/**", "/api/bascule-hj-ds/**",
                                "/api/ka/**", "/api/ds-classique/**",
                                "/api/ds-classique/bl/**", "/api/dsnord/**",
                                "/api/expzinconcf/**", "/api/exp-zinc-safi/**",
                                "/api/exp-cuivre-oncf/**", "/api/exp-cuivre-nord/**",
                                "/api/exp-pb-cmg-onf/**",
                                "/api/synthese-jour", "/api/synthese-mois",
                                "/api/synthese-annuelle", "/api/synthese-mensuelle",
                                "/api/synthese/export", "/api/rapport-hajjar",
                                "/api/rapport-hajjar/export"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/rapport-hajjar/export").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index.html", true) // ✅ OK !

                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .userDetailsService(customUserDetailsService); // 🔐 Lien avec ton CustomUserDetailsService

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }
}
