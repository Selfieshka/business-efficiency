package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.Finance;
import com.technokratos.kirillakhmetov.util.db.SqlParameters;
import com.technokratos.kirillakhmetov.util.db.SqlQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
@RequiredArgsConstructor
public class FinanceRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Finance> financeRowMapper;

//    public List<Finance> findAll() {
//        return jdbcTemplate.query(
//                SqlQuery.Finance.SQL_GET_ALL,
//                financeRowMapper
//        );
//    }

    public void save(Finance finance) {
        final MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(SqlParameters.Finance.OWNER_ID, finance.getOwnerId())
                .addValue(SqlParameters.Finance.TYPE, finance.getType())
                .addValue(SqlParameters.Finance.AMOUNT, finance.getAmount())
                .addValue(SqlParameters.Finance.CATEGORY, finance.getCategory())
                .addValue(SqlParameters.Finance.DATE, Date.valueOf(finance.getDate()));

        jdbcTemplate.update(
                SqlQuery.Finance.SQL_SAVE,
                parameters
        );
    }

    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM finance", new MapSqlParameterSource());
    }

    public Double sumAllRevenue(Long id) {
        return jdbcTemplate.queryForObject(
                SqlQuery.Finance.SQL_SUM_ALL_REVENUE,
                new MapSqlParameterSource(SqlParameters.Finance.OWNER_ID, id),
                Double.class
        );
    }

    public Double sumAllExpense(Long id) {
        return jdbcTemplate.queryForObject(
                SqlQuery.Finance.SQL_SUM_ALL_EXPENSE,
                new MapSqlParameterSource(SqlParameters.Finance.OWNER_ID, id),
                Double.class
        );
    }

//    public List<Finance> profitAnalysisByOwnerid(Long id) {
//        try (Connection connection = ConnectionProvider.getConnection();
//             PreparedStatement statement = connection.prepareStatement(SQL_PROFIT_ANALYSIS_BY_OWNER_ID)) {
//            statement.setLong(1, id);
//            statement.setLong(2, id);
//            ResultSet resultSet = statement.executeQuery();
//            List<Finance> result = new ArrayList<>();
//            while (resultSet.next()) {
//                result.add(Finance.builder()
//                        .amount(resultSet.getDouble("amount"))
//                        .date(resultSet.getDate("date").toLocalDate())
//                        .build());
//            }
//            return result;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//}

//    public List<Finance> expenseAnalysisByOwnerid(Long id) {
//        try (Connection connection = ConnectionProvider.getConnection();
//             PreparedStatement statement = connection.prepareStatement(SQL_EXPENSE_ANALYSIS_BY_OWNER_ID)) {
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            List<Finance> result = new ArrayList<>();
//            while (resultSet.next()) {
//                result.add(Finance.builder()
//                        .amount(resultSet.getDouble("amount"))
//                        .category(resultSet.getString("category"))
//                        .build());
//            }
//            return result;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public List<Finance> getPartRevenuesAndExpenses(Long id, int limit, int offset) {
//        try (Connection connection = ConnectionProvider.getConnection();
//             PreparedStatement statement = connection.prepareStatement(SQL_GET_PART_REVENUES_AND_EXPENSES)) {
//            statement.setLong(1, id);
//            statement.setInt(2, limit);
//            statement.setInt(3, offset);
//            ResultSet resultSet = statement.executeQuery();
//            List<Finance> result = new ArrayList<>();
//            while (resultSet.next()) {
//                result.add(mapper.mapRow(resultSet));
//            }
//            return result;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public int countRevenuesAndExpensesByOwnerId(Long id) {
//        try (Connection connection = ConnectionProvider.getConnection();
//             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_REVENUES_AND_EXPENSES_BY_OWNER_ID)) {
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return resultSet.getInt("count");
//            }
//            return 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public double getSumRevenuesMonthById(Long id) {
//        try (Connection connection = ConnectionProvider.getConnection();
//             PreparedStatement statement = connection.prepareStatement(SQL_GET_SUM_REVENUES_MONTH_BY_ID)) {
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return resultSet.getDouble("amount");
//            }
//            return 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public double getSumExpensesMonthById(Long id) {
//        try (Connection connection = ConnectionProvider.getConnection();
//             PreparedStatement statement = connection.prepareStatement(SQL_GET_SUM_EXPENSES_MONTH_BY_ID)) {
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return resultSet.getDouble("amount");
//            }
//            return 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public double getMonthProfitById(Long id, LocalDate localDate) {
//        try (Connection connection = ConnectionProvider.getConnection();
//             PreparedStatement statement = connection.prepareStatement(SQL_GET_MONTH_PROFIT_BY_ID)) {
//            statement.setInt(1, localDate.getMonthValue());
//            statement.setInt(2, localDate.getYear());
//            statement.setLong(3, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                return resultSet.getDouble("amount");
//            }
//            return 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
