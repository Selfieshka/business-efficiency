package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.form.BankAccountForm;
import com.technokratos.kirillakhmetov.form.ExpenseForm;
import com.technokratos.kirillakhmetov.form.RevenueForm;
import com.technokratos.kirillakhmetov.service.BankAccountService;
import com.technokratos.kirillakhmetov.service.OwnerService;
import com.technokratos.kirillakhmetov.service.impl.FinanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/finance")
public class FinanceController {
    private final OwnerService ownerService;
    private final FinanceServiceImpl financeService;
    private final BankAccountService bankAccountServiceImpl;

    @GetMapping
    public String getMonthInfo(Model model) {
        model.addAttribute("owner", ownerService.getProfileInfo("kirill@gmail.com"));
        model.addAttribute("monthInfo", financeService.getMonthInfo(100000L));
        return "finance";
    }

    @PostMapping("/expense")
    public String createExpense(@ModelAttribute("expenseForm") ExpenseForm expenseForm) {
        financeService.saveExpense(100000L, expenseForm);
        return "redirect:/finance";
    }

    @PostMapping("/money")
    public String createMoney(@ModelAttribute("bankAccountForm") BankAccountForm bankAccountForm) {
        bankAccountServiceImpl.saveBankAccount(100000L, bankAccountForm);
        return "redirect:/finance";
    }

    @PostMapping("/revenue")
    public String create(@ModelAttribute("revenueForm") RevenueForm revenueForm) {
        financeService.addRevenue(100000L, revenueForm);
        return "redirect:/finance";
    }
}
