package com.technokratos.kirillakhmetov.dto;

import java.time.LocalDate;

public record FinanceDto(
        Long owner_id,
        double amount,
        String category,
        LocalDate date) {
}
