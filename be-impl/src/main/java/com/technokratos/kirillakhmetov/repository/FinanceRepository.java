package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FinanceRepository extends JpaRepository<Finance, Long> {
    double sumAllRevenueByOwnerId(Long id);

    double sumAllExpenseByOwnerId(Long id);

    List<Finance> profitAnalysisByOwnerId(Long ownerId);

    List<Finance> expenseAnalysisByOwnerId(Long ownerId);

    List<Finance> getPartRevenuesAndExpenses(Long ownerId, int limit, int i);

    int countRevenuesAndExpensesByOwnerId(Long idOwner);

    double getSumRevenuesMonthById(Long id);

    double getSumExpensesMonthById(Long id);

    double getMonthProfitById(Long id, LocalDate localDate);
}
