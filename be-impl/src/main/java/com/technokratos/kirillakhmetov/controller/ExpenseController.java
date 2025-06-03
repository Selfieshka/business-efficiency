package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.dto.FinanceDto;
import com.technokratos.kirillakhmetov.service.FinanceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/finance/expense")
public class ExpenseController {
    private final FinanceService financeService;

    @PostMapping
    public String create(HttpServletRequest req, HttpServletResponse resp) {
        financeService.addExpense(new FinanceDto(
                100000L,
                Double.parseDouble(req.getParameter("amount")),
                req.getParameter("category"),
                LocalDate.parse(req.getParameter("date"))));
        return "redirect:/finance";
    }
}
