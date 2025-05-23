package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.entity.Finance;
import com.technokratos.kirillakhmetov.repository.FinanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FinanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FinanceRepository financeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Long TEST_OWNER_ID = 123L;

    @BeforeEach
    void setUp() {
        // Очищаем таблицы перед каждым тестом
        financeRepository.deleteAll();
        jdbcTemplate.update("DELETE FROM owner WHERE owner_id = ?", TEST_OWNER_ID);

        // Создаем тестового владельца
        jdbcTemplate.update(
                "INSERT INTO owner (owner_id, first_name, last_name, email, password, business_name) VALUES (?, ?, ?, ?, ?, ?)",
                TEST_OWNER_ID, "Test", "Owner", "test@example.com", "password", "Test Business"
        );

        // Добавляем тестовые данные
        Finance revenue = Finance.builder()
                .ownerId(TEST_OWNER_ID)
                .type("Доход")
                .amount(1000.0)
                .category("Выручка")
                .date(LocalDate.now())
                .build();
        financeRepository.save(revenue);

        Finance expense = Finance.builder()
                .ownerId(TEST_OWNER_ID)
                .type("Расход")
                .amount(500.0)
                .category("Зарплата")
                .date(LocalDate.now())
                .build();
        financeRepository.save(expense);
    }

    @Test
    void getRevenue_returnsFinanceResponse() throws Exception {
        mockMvc.perform(get("/api/stats/revenue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(1000.0));
    }

    @Test
    void getExpense_returnsFinanceResponse() throws Exception {
        mockMvc.perform(get("/api/stats/expense"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(500.0));
    }

    @Test
    void getProfit_returnsFinanceResponse() throws Exception {
        mockMvc.perform(get("/api/stats/profit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(500.0));
    }
}