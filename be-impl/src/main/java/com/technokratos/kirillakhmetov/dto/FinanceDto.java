package com.technokratos.kirillakhmetov.dto;

import java.time.LocalDate;

public record FinanceDto(
        Long ownerId,
        double amount,
        String category,
        LocalDate date) {
}
