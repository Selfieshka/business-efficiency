package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.form.RegistrationForm;
import com.technokratos.kirillakhmetov.service.OwnerService;
import com.technokratos.kirillakhmetov.validation.RegistrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final OwnerService ownerServiceImpl;
    private final RegistrationValidator registrationValidator;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Validated @ModelAttribute("registrationForm") RegistrationForm registrationForm,
                               BindingResult bindingResult,
                               Model model) {
        registrationValidator.validate(registrationForm, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", registrationForm);
            return "registration";
        }

        ownerServiceImpl.save(registrationForm);
        return "redirect:/login?registered";
    }
}
