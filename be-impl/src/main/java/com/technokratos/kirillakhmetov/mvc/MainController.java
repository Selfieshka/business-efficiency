package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.dto.response.OwnerResponse;
import com.technokratos.kirillakhmetov.security.UserContextHolder;
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
    private final UserContextHolder userContextHolderImpl;

    @GetMapping
    public String mainPage(Model model) {
        OwnerResponse owner = ownerServiceImpl.getProfileInfo(userContextHolderImpl
                .getUserIdFromSecurityContext());
        model.addAttribute("owner", owner);
        return "main";
    }
}
