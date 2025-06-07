package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.security.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/auth/google")
public class GoogleOAuthController {
    private final OAuthService googleOAuthService;

    @GetMapping("/login")
    public String login() {
        log.info("Starting Google OAuth login process");
        return "redirect:%s".formatted(googleOAuthService.getAuthUrl());
    }

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code) {
        log.info("Received callback from Google with code: {}", code);
        googleOAuthService.processOAuthPostLogin(code);
        return "redirect:/main";
    }
} 