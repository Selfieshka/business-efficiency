package com.technokratos.kirillakhmetov.dto.response;

public record FinancePaginationDto(
        String type,
        double amount,
        String category,
        String date) {
}
