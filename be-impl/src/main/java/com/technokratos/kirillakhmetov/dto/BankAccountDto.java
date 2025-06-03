package com.technokratos.kirillakhmetov.dto;

public record BankAccountDto(
        Long owner_id,
        String bankName,
        double amount) {
}
