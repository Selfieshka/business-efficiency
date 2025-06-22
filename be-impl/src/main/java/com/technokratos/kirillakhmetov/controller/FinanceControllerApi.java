package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.api.FinanceApi;
import com.technokratos.kirillakhmetov.dto.response.*;
import com.technokratos.kirillakhmetov.security.UserContextHolder;
import com.technokratos.kirillakhmetov.service.BankAccountService;
import com.technokratos.kirillakhmetov.service.impl.FinanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FinanceControllerApi implements FinanceApi {
    private final FinanceServiceImpl financeService;
    private final BankAccountService bankAccountServiceImpl;
    private final UserContextHolder userContextHolderImpl;

    @Override
    public FinanceResponse getRevenue() {
        return financeService.calculateRevenue(userContextHolderImpl
                .getUserIdFromSecurityContext());
    }

    @Override
    public FinanceResponse getExpense() {
        return financeService.calculateExpense(userContextHolderImpl
                .getUserIdFromSecurityContext());
    }

    @Override
    public FinanceResponse getMoney() {
        return bankAccountServiceImpl.calculateAllAmount(userContextHolderImpl
                .getUserIdFromSecurityContext());
    }

    @Override
    public FinanceResponse getProfit() {
        return financeService.calculateProfit(userContextHolderImpl
                .getUserIdFromSecurityContext());
    }

    @Override
    public ProfitResponse getProfitAnalytics() {
        //todo: Здесь мы берем предикт значения для графика прогнозируемой прибыли
        return financeService.analyzeProfit(userContextHolderImpl
                .getUserIdFromSecurityContext());
    }

    @Override
    public ExpenseResponse getExpenseAnalytics() {
        return financeService.analyzeExpense(userContextHolderImpl
                .getUserIdFromSecurityContext());
    }

    @Override
    public PageResponse getCountRevenuesExpenses() {
        return financeService.getCountItems(userContextHolderImpl
                .getUserIdFromSecurityContext());
    }

    @Override
    public List<FinancePaginationResponse> getItemsRevenuesExpenses(Integer page) {
        return financeService.getPage(userContextHolderImpl
                .getUserIdFromSecurityContext(), page);
    }
}