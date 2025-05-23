package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.api.FinanceApi;
import com.technokratos.kirillakhmetov.dto.response.FinanceResponse;
import com.technokratos.kirillakhmetov.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FinanceController implements FinanceApi {
    private final FinanceService financeService;

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

//    @Override
//    public void getMoney() {
//
//    }

    @Override
    public FinanceResponse getProfit() {
        Long ownerId = 123L;
        return financeService.calculateProfit(ownerId);
    }

//    @Override
//    public ProfitResponse getProfitAnalytics() {
//        Long ownerId = 123L;
//        return financeService.analyzeProfit(ownerId);
//        return null;
//    }

//    @Override
//    public ExpenseResponse getExpenseAnalytics() {
//        Long ownerId = 123L;
//        return financeService.analyzeExpense(ownerId);
//        return null;
//    }

//    @Override
//    public String getCountRevenuesExpenses() {
//        Long ownerId = 123L;
//        return financeService.getCountItems(ownerId);
//        return null;
//    }

//    @Override
//    public List<FinancePaginationResponse> getItemsRevenuesExpenses() {
//        Long ownerId = 123L;
//        int page = 1;
//        return financeService.getPage(ownerId, page);
//        return null;
//    }
}