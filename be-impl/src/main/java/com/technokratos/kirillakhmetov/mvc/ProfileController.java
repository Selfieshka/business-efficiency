package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.dto.OwnerDto;
import com.technokratos.kirillakhmetov.form.ProfileForm;
import com.technokratos.kirillakhmetov.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final OwnerService ownerServiceImpl;

    @GetMapping
    public String profile(Model model) {
        OwnerDto owner = ownerServiceImpl.getProfileInfo("kirill@gmail.com");
        model.addAttribute("owner", owner);
        return "profile";
    }

    @PostMapping
    public String doPost(@ModelAttribute("profileForm") ProfileForm profileForm, Model model) {
        model.addAttribute("owner", ownerServiceImpl.changePersonalData(100000L, profileForm));
        return "redirect:/profile";
    }
}
