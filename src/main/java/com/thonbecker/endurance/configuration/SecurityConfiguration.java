package com.thonbecker.endurance.configuration;

import static org.springframework.security.config.http.SessionCreationPolicy.ALWAYS;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@AllArgsConstructor
public class SecurityConfiguration {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        String issuerUri = "https://cognito-idp.us-east-1.amazonaws.com/us-east-1_DZ8a8Cqaw";

        List<ClientRegistration> clientRegistrations = List.of(
                ClientRegistrations.fromOidcIssuerLocation(issuerUri)
                        .registrationId("global")
                        .clientId("7hrr4q4t8j5nha0gm9s0cu0lvj")
                        .clientSecret("gi4jc8md2p1h2cpqu7puvt89pk9rvh320djhm1lkm41o90ks440")
                        .redirectUri("https://global.thonbecker.solutions/login/oauth2/code/global")
                        .clientName("Global App Client")
                        .scope("openid")
                        .build(),
                ClientRegistrations.fromOidcIssuerLocation(issuerUri)
                        .registrationId("endurance")
                        .clientId("5uetsd87a3cd3so07omns5bi1q")
                        .clientSecret("sksa59sd4qhsm6s2gqgjhl8qtoe730k6ho2u6leqrh95iglu6sl")
                        .redirectUri("https://endurance.thonbecker.solutions/login/oauth2/code/endurance")
                        .clientName("Endurance App Client")
                        .scope("openid")
                        .build(),
                ClientRegistrations.fromOidcIssuerLocation(issuerUri)
                        .registrationId("local")
                        .clientId("e580lipkj7npukhk3o7j2tahp")
                        .clientSecret("d0ditdrkj0cr52de8hnj8t84103movk6hmfqf1etbarerhtospf")
                        .redirectUri("http://localhost:8080/login/oauth2/code/local")
                        .clientName("Local Development")
                        .scope("openid")
                        .build());
        return new InMemoryClientRegistrationRepository(clientRegistrations);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz.requestMatchers("/", "/login/**", "/actuator/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .oauth2Login(oauth2 -> oauth2.loginPage("/login/sso"))
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessUrl("/"))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(ALWAYS))
                .requestCache(requestCache -> requestCache.requestCache(new HttpSessionRequestCache()));
        return http.build();
    }
}
