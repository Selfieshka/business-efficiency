package com.technokratos.kirillakhmetov.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Product {
    private Long id;
    private Long invoice_id;
    private String name;
    private String measurementUnit;
    private int quantity;
    private double costPerUnit;
}
