package com.technokratos.kirillakhmetov.dto;

public record FinancePaginationDto(
        String type,
        double amount,
        String category,
        String date) {
}
