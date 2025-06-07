package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.dto.response.OwnerResponse;
import com.technokratos.kirillakhmetov.form.BankAccountForm;
import com.technokratos.kirillakhmetov.form.ExpenseForm;
import com.technokratos.kirillakhmetov.form.RevenueForm;
import com.technokratos.kirillakhmetov.security.UserContextHolder;
import com.technokratos.kirillakhmetov.service.BankAccountService;
import com.technokratos.kirillakhmetov.service.OwnerService;
import com.technokratos.kirillakhmetov.service.impl.FinanceServiceImpl;
import com.technokratos.kirillakhmetov.validator.RevenueValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/finance")
public class FinanceController {
    private final OwnerService ownerServiceImpl;
    private final FinanceServiceImpl financeService;
    private final BankAccountService bankAccountServiceImpl;
    private final UserContextHolder userContextHolderImpl;
    private final RevenueValidator revenueValidator;

    @GetMapping
    public String getMonthInfo(Model model) {
        OwnerResponse owner = ownerServiceImpl.getProfileInfo(userContextHolderImpl
                .getUserIdFromSecurityContext());
        model.addAttribute("owner", owner);
        model.addAttribute("monthInfo", financeService.getMonthInfo(userContextHolderImpl
                .getUserIdFromSecurityContext()));
        model.addAttribute("revenueForm", new RevenueForm());
        return "finance";
    }

    @PostMapping("/expense")
    public String createExpense(@ModelAttribute("expenseForm") ExpenseForm expenseForm) {
        financeService.saveExpense(userContextHolderImpl
                .getUserIdFromSecurityContext(), expenseForm);
        return "redirect:/finance";
    }

    @PostMapping("/money")
    public String createMoney(@ModelAttribute("bankAccountForm") BankAccountForm bankAccountForm) {
        bankAccountServiceImpl.saveBankAccount(userContextHolderImpl
                .getUserIdFromSecurityContext(), bankAccountForm);
        return "redirect:/finance";
    }

    @PostMapping("/revenue")
    public String create(@ModelAttribute("revenueForm") RevenueForm revenueForm,
                         BindingResult bindingResult,
                         Model model) {
        revenueValidator.validate(revenueForm, bindingResult);

        if (bindingResult.hasErrors()) {
            OwnerResponse owner = ownerServiceImpl.getProfileInfo(userContextHolderImpl
                    .getUserIdFromSecurityContext());
            model.addAttribute("owner", owner);
            model.addAttribute("monthInfo", financeService.getMonthInfo(userContextHolderImpl
                    .getUserIdFromSecurityContext()));
            return "finance";
        }

        financeService.addRevenue(userContextHolderImpl
                .getUserIdFromSecurityContext(), revenueForm);
        return "redirect:/finance";
    }
}
