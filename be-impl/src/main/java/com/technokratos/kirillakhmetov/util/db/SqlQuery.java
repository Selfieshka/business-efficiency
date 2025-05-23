package com.technokratos.kirillakhmetov.util.db;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlQuery {
    public static final class Finance {
        //language=sql
        public static final String SQL_GET_ALL = "SELECT * FROM finance";
        //language=sql
        public static final String SQL_SAVE = """
                INSERT INTO finance (owner_id, type, amount, category, date)
                VALUES (:%s, :%s, :%s, :%s, :%s)
                """
                .formatted(
                        SqlParameters.Finance.OWNER_ID,
                        SqlParameters.Finance.TYPE,
                        SqlParameters.Finance.AMOUNT,
                        SqlParameters.Finance.CATEGORY,
                        SqlParameters.Finance.DATE
                );
        //language=sql
        public static final String SQL_SUM_ALL_REVENUE = """
                SELECT SUM(amount) AS amount
                FROM finance
                WHERE owner_id = :%s AND category = 'Выручка'
                """
                .formatted(SqlParameters.Finance.OWNER_ID);

        //language=sql
        public static final String SQL_SUM_ALL_EXPENSE = """
                SELECT SUM(amount) AS amount
                FROM finance
                WHERE owner_id = :%s AND type = 'Расход'
                """
                .formatted(SqlParameters.Finance.OWNER_ID);

//        //language=sql
//        public static final String SQL_PROFIT_ANALYSIS_BY_OWNER_ID = """
//                WITH revenue AS (
//                    SELECT
//                        DATE_TRUNC('month', date) AS month,
//                        SUM(amount) AS total_revenue
//                    FROM finance
//                    WHERE owner_id = ? AND category = 'Выручка' AND date >= CURRENT_DATE - INTERVAL '5 months'
//                    GROUP BY month
//                ),
//                     expenses AS (
//                         SELECT
//                             DATE_TRUNC('month', date) AS month,
//                             SUM(amount) AS total_expenses
//                         FROM finance
//                         WHERE owner_id = ? AND type = 'Расход' AND date >= CURRENT_DATE - INTERVAL '5 months'
//                         GROUP BY month
//                     )
//                SELECT
//                    months.month AS date,
//                    COALESCE(r.total_revenue, 0) - COALESCE(e.total_expenses, 0) AS amount
//                FROM
//                    (SELECT DISTINCT month FROM revenue UNION SELECT DISTINCT month FROM expenses) AS months
//                        LEFT JOIN revenue r ON months.month = r.month
//                        LEFT JOIN expenses e ON months.month = e.month
//                ORDER BY months.month ASC
//                LIMIT 5
//                """;
//
//        //language=sql
//        public static final String SQL_EXPENSE_ANALYSIS_BY_OWNER_ID = """
//                SELECT category, SUM(amount) AS amount
//                FROM finance
//                WHERE owner_id = ? AND type = 'Расход'
//                    AND date >= DATE_TRUNC('month', CURRENT_DATE)
//                    AND date < DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '1 month'
//                GROUP BY category
//                ORDER BY category
//                """;
//
//        //language=sql
//        public static final String SQL_GET_PART_REVENUES_AND_EXPENSES = """
//                SELECT * FROM finance
//                WHERE owner_id = ?
//                ORDER BY date DESC
//                LIMIT ?
//                OFFSET ?
//                """;
//
//        //language=sql
//        public static final String SQL_COUNT_REVENUES_AND_EXPENSES_BY_OWNER_ID = """
//                SELECT COUNT(*) FROM finance
//                WHERE owner_id = ?
//                """;
//
//        //language=sql
//        public static final String SQL_GET_SUM_REVENUES_MONTH_BY_ID = """
//                SELECT SUM(amount) as amount
//                FROM finance
//                WHERE EXTRACT(MONTH FROM date) = EXTRACT(MONTH FROM CURRENT_DATE)
//                    AND EXTRACT(YEAR FROM date) = EXTRACT(YEAR FROM CURRENT_DATE)
//                    AND owner_id = ?
//                    AND category = 'Выручка';
//                """;
//
//        //language=sql
//        public static final String SQL_GET_SUM_EXPENSES_MONTH_BY_ID = """
//                SELECT SUM(amount) as amount
//                FROM finance
//                WHERE EXTRACT(MONTH FROM date) = EXTRACT(MONTH FROM CURRENT_DATE)
//                    AND EXTRACT(YEAR FROM date) = EXTRACT(YEAR FROM CURRENT_DATE)
//                    AND owner_id = ?
//                    AND type = 'Расход';
//                """;
//
//        //language=sql
//        public static final String SQL_GET_MONTH_PROFIT_BY_ID = """
//                SELECT (SUM(CASE WHEN type = 'Доход' THEN amount ELSE 0 END) -
//                         SUM(CASE WHEN type = 'Расход' THEN amount ELSE 0 END)) AS amount
//                FROM finance
//                WHERE EXTRACT(MONTH FROM date) = ?
//                    AND EXTRACT(YEAR FROM date) = ?
//                    AND owner_id = ?
//                GROUP BY owner_id;
//                """;
    }
}