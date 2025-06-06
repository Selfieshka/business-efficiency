package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.dto.response.OwnerResponse;
import com.technokratos.kirillakhmetov.form.ProfileForm;
import com.technokratos.kirillakhmetov.security.UserContextHolder;
import com.technokratos.kirillakhmetov.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final UserContextHolder userContextHolderImpl;
    private final OwnerService ownerServiceImpl;

    @GetMapping
    public String profile(Model model) {
        OwnerResponse owner = ownerServiceImpl.getProfileInfo(userContextHolderImpl
                .getUserIdFromSecurityContext());
        model.addAttribute("owner", owner);
        model.addAttribute("currentPage", "profile");
        return "profile";
    }

    @PostMapping
    public String updateOwnerInfo(@ModelAttribute("profileForm") ProfileForm profileForm, Model model) {
        OwnerResponse owner = ownerServiceImpl.changePersonalData(userContextHolderImpl
                .getUserIdFromSecurityContext(), profileForm);
        model.addAttribute("owner", owner);
        return "redirect:/profile";
    }

    @PostMapping("/delete")
    public String deleteOwner(SessionStatus sessionStatus) {
        ownerServiceImpl.deleteOwner(userContextHolderImpl
                .getUserIdFromSecurityContext());
        sessionStatus.setComplete();
        return "redirect:/login?deleted";
    }
}
