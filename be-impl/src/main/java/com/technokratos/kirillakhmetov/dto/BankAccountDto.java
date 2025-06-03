package com.technokratos.kirillakhmetov.dto;

public record BankAccountDto(
        Long ownerId,
        String bankName,
        double amount) {
}
