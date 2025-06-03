package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.api.FinanceApi;
import com.technokratos.kirillakhmetov.dto.response.*;
import com.technokratos.kirillakhmetov.service.BankAccountService;
import com.technokratos.kirillakhmetov.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiFinanceController implements FinanceApi {
    private final FinanceService financeService;
    private final BankAccountService bankAccountService;

    @Override
    public FinanceResponse getRevenue() {
        return financeService.calculateRevenue(100000L);
    }

    @Override
    public FinanceResponse getExpense() {
        return financeService.calculateExpense(100000L);
    }

    @Override
    public ApiFinanceDto getMoney() {
        return bankAccountService.calculateAllAmount(100000L);
    }

    @Override
    public FinanceResponse getProfit() {
        return financeService.calculateProfit(100000L);
    }

    @Override
    public ProfitResponse getProfitAnalytics() {
        return financeService.analyzeProfit(100000L);
    }

    @Override
    public ExpenseResponse getExpenseAnalytics() {
        return financeService.analyzeExpense(100000L);
    }

    @Override
    public String getCountRevenuesExpenses() {
        return financeService.getCountItems(100000L);
    }

    @Override
    public List<FinancePaginationDto> getItemsRevenuesExpenses() {
        return financeService.getPage(100000L, 1);
    }
}