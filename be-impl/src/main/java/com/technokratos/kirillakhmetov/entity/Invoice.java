package com.technokratos.kirillakhmetov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class Invoice {
    private Long id;
    private Long owner_id;
    private String number;
    private LocalDate date;
    private List<Product> products;
}
