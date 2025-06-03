package com.technokratos.kirillakhmetov.api;

import com.technokratos.kirillakhmetov.dto.response.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/api/v1/stats")
public interface FinanceApi {

    @GetMapping("/revenue")
    @ResponseStatus(HttpStatus.OK)
    FinanceResponse getRevenue();

    @GetMapping("/expense")
    @ResponseStatus(HttpStatus.OK)
    FinanceResponse getExpense();

    @GetMapping("/money")
    @ResponseStatus(HttpStatus.OK)
    ApiFinanceDto getMoney();

    @GetMapping("/profit")
    @ResponseStatus(HttpStatus.OK)
    FinanceResponse getProfit();

    @GetMapping("/profit-analytics")
    @ResponseStatus(HttpStatus.OK)
    ProfitResponse getProfitAnalytics();

    @GetMapping("/expense-analytics")
    @ResponseStatus(HttpStatus.OK)
    ExpenseResponse getExpenseAnalytics();

    @GetMapping("/revenues-expenses/count")
    @ResponseStatus(HttpStatus.OK)
    String getCountRevenuesExpenses();

    @GetMapping("/revenues-expenses/items")
    @ResponseStatus(HttpStatus.OK)
    List<FinancePaginationDto> getItemsRevenuesExpenses();

}
