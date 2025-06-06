package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {
    private final OwnerService ownerServiceImpl;

    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("owner", ownerServiceImpl.getProfileInfo("kirill@gmail.com"));
        return "main";
    }
}
