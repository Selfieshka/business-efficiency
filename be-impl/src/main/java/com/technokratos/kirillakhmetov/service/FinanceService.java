package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.response.*;
import com.technokratos.kirillakhmetov.form.ExpenseForm;
import com.technokratos.kirillakhmetov.form.RevenueForm;

import java.util.List;

public interface FinanceService {
    void saveExpense(long ownerId, ExpenseForm expenseForm);

    void addRevenue(long ownerId, RevenueForm revenueForm);

    FinanceResponse calculateRevenue(Long id);

    FinanceResponse calculateExpense(Long id);

    FinanceResponse calculateProfit(Long idOwner);

    ProfitResponse analyzeProfit(Long ownerId);

    ExpenseResponse analyzeExpense(Long ownerId);

    List<FinancePaginationResponse> getPage(Long ownerId, int page);

    PageResponse getCountItems(Long idOwner);

    MonthInfoResponse getMonthInfo(Long id);
}
