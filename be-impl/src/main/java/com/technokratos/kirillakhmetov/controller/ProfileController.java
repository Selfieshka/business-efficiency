package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.dto.OwnerDto;
import com.technokratos.kirillakhmetov.service.OwnerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final OwnerService ownerService;

    @GetMapping
    public String profile(Model model) {
        OwnerDto owner = ownerService.getProfileInfo("kirill@gmail.com");

        model.addAttribute("currentPage", "profile");
        model.addAttribute("owner", owner);

        return "profile";
    }

    @PostMapping
    public String doPost(HttpServletRequest req, HttpServletResponse resp, Model model) throws ServletException, IOException {
        OwnerDto owner = ownerService.getProfileInfo("kirill@gmail.com");

        ownerService.changePersonalData(new OwnerDto(
                req.getParameter("firstName"),
                req.getParameter("lastName"),
                req.getParameter("patronymic"),
                Integer.parseInt(req.getParameter("age")),
                owner.email(),
                req.getParameter("phoneNumber"),
                null
        ));
        OwnerDto newOwner = ownerService.getProfileInfo(owner.email());
        model.addAttribute("owner", newOwner);
        return "redirect:/profile";
    }

    @PostMapping("/upload")
    public String uploadAvatar(HttpServletRequest req, HttpServletResponse resp, Model model) throws ServletException, IOException {
        Part profilePhoto = req.getPart("profilePhoto");
        OwnerDto ownerFromSession = ownerService.getProfileInfo("kirill@gmail.com");
        String photoUrl = ownerService.uploadProfilePhoto(profilePhoto, ownerFromSession.email());
        OwnerDto newOwnerDto = new OwnerDto(
                ownerFromSession.firstName(),
                ownerFromSession.lastName(),
                ownerFromSession.patronymic(),
                ownerFromSession.age(),
                ownerFromSession.email(),
                ownerFromSession.phoneNumber(),
                photoUrl
        );
        model.addAttribute("owner", newOwnerDto);
        return "redirect:/profile";
    }
}
