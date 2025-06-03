package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.dto.ExpenseCategorySummary;
import com.technokratos.kirillakhmetov.dto.ProfitAnalysisDto;
import com.technokratos.kirillakhmetov.entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface FinanceRepository extends JpaRepository<Finance, Long> {
    @Query("""
            SELECT SUM(f.amount) AS amount
            FROM Finance f
            WHERE f.owner.id = :ownerId AND f.category = 'Выручка'
            """
    )
    Optional<Double> sumAllRevenueByOwnerId(@Param("ownerId") Long ownerId);

    @Query("""
            SELECT SUM(f.amount) AS amount
            FROM Finance f
            WHERE f.owner.id = :ownerId AND f.type = 'Расход'
            """)
    Optional<Double> sumAllExpenseByOwnerId(@Param("ownerId") Long ownerId);

    @Query(value = """
            WITH revenue AS (
                            SELECT
                                DATE(DATE_TRUNC('month', date)) AS month,
                                SUM(amount) AS total_revenue
                            FROM finance
                            WHERE owner_id = :ownerId AND category = 'Выручка' AND date >= CURRENT_DATE - INTERVAL '5 months'
                            GROUP BY month
                        ),
                expenses AS (
                 SELECT
                    DATE(DATE_TRUNC('month', date)) AS month,
                     SUM(amount) AS total_expenses
                 FROM finance
                 WHERE owner_id = :ownerId AND type = 'Расход' AND date >= CURRENT_DATE - INTERVAL '5 months'
                 GROUP BY month
                )
            SELECT
                DATE(months.month) AS date,
                COALESCE(r.total_revenue, 0) - COALESCE(e.total_expenses, 0) AS amount
            FROM
                (SELECT DISTINCT month FROM revenue UNION SELECT DISTINCT month FROM expenses) AS months
                    LEFT JOIN revenue r ON months.month = r.month
                    LEFT JOIN expenses e ON months.month = e.month
            ORDER BY months.month ASC
            LIMIT 5
            """, nativeQuery = true
    )
    List<ProfitAnalysisDto> profitAnalysisByOwnerId(@Param("ownerId") Long ownerId);

    @Query(value = """
            SELECT category, SUM(amount) AS amount
            FROM finance
            WHERE owner_id = :ownerId AND type = 'Расход'
                AND date >= DATE_TRUNC('month', CURRENT_DATE)
                AND date < DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '1 month'
            GROUP BY category
            ORDER BY category
            """, nativeQuery = true
    )
    List<ExpenseCategorySummary> expenseAnalysisByOwnerId(@Param("ownerId") Long ownerId);

    @Query("""
            SELECT f
            FROM Finance f
            WHERE f.owner.id = :ownerId
            ORDER BY f.date DESC
            LIMIT :limit
            OFFSET :offset
            """
    )
    List<Finance> getPartRevenuesAndExpenses(
            @Param("ownerId") Long ownerId,
            @Param("limit") int limit,
            @Param("offset") int i);

    @Query("""
            SELECT COUNT(*)
            FROM Finance f
            WHERE f.owner.id = :ownerId
            """)
    int countRevenuesAndExpensesByOwnerId(@Param("ownerId") Long idOwner);

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
                        AND f.type = 'Расход'
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
