package com.thonbecker.endurance.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@Slf4j
public class WebController implements WebMvcConfigurer {

    @GetMapping("/login/sso")
    public String login(HttpServletRequest request) {
        final String serverName = request.getServerName();
        log.info("Server name: {}", serverName);
        final String redirectUrl;

        if (serverName.contains("global")) {
            redirectUrl = "/oauth2/authorization/global";
        } else if (serverName.contains("endurance")) {
            redirectUrl = "/oauth2/authorization/endurance";
        } else {
            redirectUrl = "/oauth2/authorization/local";
        }

        return "redirect:" + redirectUrl;
    }

    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/chat").setViewName("chat");
    }
}
