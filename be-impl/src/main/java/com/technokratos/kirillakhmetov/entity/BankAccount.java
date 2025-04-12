package com.technokratos.kirillakhmetov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class BankAccount {
    private Long id;
    private Long ownerId;
    private String bankName;
    private double amount;
}
