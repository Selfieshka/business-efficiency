package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.api.FinanceApi;
import com.technokratos.kirillakhmetov.dto.response.ExpenseResponse;
import com.technokratos.kirillakhmetov.dto.response.FinancePaginationDto;
import com.technokratos.kirillakhmetov.dto.response.FinanceResponse;
import com.technokratos.kirillakhmetov.dto.response.ProfitResponse;
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
        Long ownerId = 123L;
        return financeService.calculateRevenue(ownerId);
    }

    @Override
    public FinanceResponse getExpense() {
        Long ownerId = 123L;
        return financeService.calculateExpense(ownerId);
    }

    @Override
    public void getMoney() {
        bankAccountService.calculateAllAmount(100000L);
    }

    @Override
    public FinanceResponse getProfit() {
        Long ownerId = 123L;
        return financeService.calculateProfit(ownerId);
    }

    @Override
    public ProfitResponse getProfitAnalytics() {
        Long ownerId = 123L;
        return financeService.analyzeProfit(ownerId);
    }

    @Override
    public ExpenseResponse getExpenseAnalytics() {
        Long ownerId = 123L;
        return financeService.analyzeExpense(ownerId);
    }

    @Override
    public String getCountRevenuesExpenses() {
        Long ownerId = 123L;
        return financeService.getCountItems(ownerId);
    }

    @Override
    public List<FinancePaginationDto> getItemsRevenuesExpenses() {
        Long ownerId = 123L;
        int page = 1;
        return financeService.getPage(ownerId, page);
    }
}