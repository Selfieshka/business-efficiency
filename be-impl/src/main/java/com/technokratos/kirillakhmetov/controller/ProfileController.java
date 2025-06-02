package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.dto.OwnerDto;
import com.technokratos.kirillakhmetov.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final OwnerService ownerService;

    @GetMapping("/profile")
    public String profile(Model model) {
        OwnerDto owner = ownerService.getProfileInfo("kirill@gmail.com");

        model.addAttribute("currentPage", "profile");
        model.addAttribute("owner", owner);

        return "profile";
    }
}
