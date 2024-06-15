package com.thonbecker.endurance.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class WebController implements WebMvcConfigurer {

    @GetMapping("/login/sso")
    public String login(HttpServletRequest request) {
        final String serverName = request.getServerName();
        final String redirectUrl =
                switch (serverName) {
                    case "global" -> "/oauth2/authorization/global";
                    case "endurance" -> "/oauth2/authorization/endurance";
                    default -> "/oauth2/authorization/local";
                };

        return "redirect:" + redirectUrl;
    }

    @Override
    public void addViewControllers(@NonNull ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/chat").setViewName("chat");
    }
}
