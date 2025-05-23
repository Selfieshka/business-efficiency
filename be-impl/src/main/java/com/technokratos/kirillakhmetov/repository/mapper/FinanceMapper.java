package com.technokratos.kirillakhmetov.repository.mapper;

import com.technokratos.kirillakhmetov.entity.Finance;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FinanceMapper implements RowMapper<Finance> {
    @Override
    public Finance mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Finance.builder()
                .id(rs.getLong("finance_id"))
                .ownerId(rs.getLong("owner_id"))
                .type(rs.getString("type"))
                .amount(rs.getDouble("amount"))
                .category(rs.getString("category"))
                .date(rs.getDate("date").toLocalDate())
                .build();
    }
}
