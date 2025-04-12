package com.technokratos.kirillakhmetov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Builder
public class Finance {
    private Long id;
    private Long owner_id;
    private String type;
    private double amount;
    private String category;
    private LocalDate date;
}
