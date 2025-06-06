package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.form.RegistrationForm;
import com.technokratos.kirillakhmetov.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final OwnerService ownerServiceImpl;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("registrationForm") RegistrationForm registrationForm) {
        ownerServiceImpl.save(registrationForm);
        return "redirect:/login";
    }
}
