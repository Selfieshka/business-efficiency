package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/finance")
public class FinanceController {
    private final FinanceService financeService;

    @GetMapping
    public String getMonthInfo(Model model) {
        model.addAttribute("monthInfo", financeService.getMonthInfo(100000L));
        return "finance";
    }
}
