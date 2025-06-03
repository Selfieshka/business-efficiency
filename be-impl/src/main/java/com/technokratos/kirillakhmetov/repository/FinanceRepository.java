package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface FinanceRepository extends JpaRepository<Finance, Long> {
    @Query("select BankAccount ba from BankAccount")
    double sumAllRevenueByOwnerId(Long id);

    @Query("select BankAccount ba from BankAccount")
    double sumAllExpenseByOwnerId(Long id);

    @Query("select BankAccount ba from BankAccount")
    List<Finance> profitAnalysisByOwnerId(Long ownerId);

    @Query("select BankAccount ba from BankAccount")
    List<Finance> expenseAnalysisByOwnerId(Long ownerId);

    @Query("select BankAccount ba from BankAccount")
    List<Finance> getPartRevenuesAndExpenses(Long ownerId, int limit, int i);

    @Query("select BankAccount ba from BankAccount")
    int countRevenuesAndExpensesByOwnerId(Long idOwner);

    @Query("""
            SELECT SUM(f.amount)
                   FROM Finance f
                   WHERE EXTRACT(MONTH FROM f.date) = EXTRACT(MONTH FROM CURRENT_DATE)
                        AND EXTRACT(YEAR FROM f.date) = EXTRACT(YEAR FROM CURRENT_DATE)
                        AND f.owner.id = :ownerId
                        AND f.category = 'Выручка'
            """
    )
    Optional<Double> getSumRevenuesMonthById(@Param("ownerId") Long ownerId);

    @Query("""
            SELECT SUM(f.amount)
                   FROM Finance f
                   WHERE EXTRACT(MONTH FROM f.date) = EXTRACT(MONTH FROM CURRENT_DATE)
                        AND EXTRACT(YEAR FROM f.date) = EXTRACT(YEAR FROM CURRENT_DATE)
                        AND f.owner.id = :ownerId
                        AND f.category = 'Расход'
            """)
    Optional<Double> getSumExpensesMonthById(@Param("ownerId") Long ownerId);

    @Query("""
                SELECT (SUM(CASE WHEN f.type = 'Доход' THEN f.amount ELSE 0 END) -
                            SUM(CASE WHEN f.type = 'Расход' THEN f.amount ELSE 0 END)) AS amount
                FROM Finance f
                WHERE EXTRACT(MONTH FROM f.date) = :month
                      AND EXTRACT(YEAR FROM f.date) = :year
                      AND f.owner.id = :ownerId
                GROUP BY f.owner.id
            """)
    Optional<Double> getMonthProfitById(@Param("ownerId") Long ownerId,
                                        @Param("month") Integer monthValue,
                                        @Param("year") Integer year);
}
