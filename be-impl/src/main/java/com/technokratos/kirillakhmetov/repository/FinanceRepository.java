package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("""
                SELECT COALESCE(SUM(f.amount), 0.0)
                FROM Finance f
                WHERE f.owner.id = :id
                  AND YEAR(f.date) = YEAR(:localDate)
                  AND MONTH(f.date) = MONTH(:localDate)
            """)
    double getMonthProfitById(@Param("id") Long id, @Param("localDate") LocalDate localDate);
}
