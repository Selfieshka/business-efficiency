package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.dto.BankAccountDto;
import com.technokratos.kirillakhmetov.service.BankAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/finance/money")
public class MoneyController {
    private final BankAccountService bankAccountService;

    @PostMapping
    public String create(HttpServletRequest req, HttpServletResponse resp) {
        bankAccountService.addAccount(new BankAccountDto(
                (Long) req.getSession().getAttribute("id"),
                req.getParameter("bankName"),
                Double.parseDouble(req.getParameter("amount"))
        ));
        return "redirect:/finance";
    }
}
