package com.thonbecker.endurance.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Configuration
public class SessionConfiguration {
    public static final String COOKIE_NAME = "JSESSIONID";
    public static final String COOKIE_PATH = "/";
    public static final String DOMAIN_NAME_PATTERN = "^.+?\\.(\\w+\\.[a-z]+)$";

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(COOKIE_NAME);
        serializer.setCookiePath(COOKIE_PATH);
        serializer.setDomainNamePattern(DOMAIN_NAME_PATTERN);
        return serializer;
    }
}
