package com.thonbecker.endurance.configuration;

import static org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz.requestMatchers("/", "/login/**", "/actuator/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login(Customizer.withDefaults())
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessUrl("/"))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(IF_REQUIRED))
                .requestCache(requestCache -> requestCache.requestCache(new HttpSessionRequestCache()));
        return http.build();
    }
}
