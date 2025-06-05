package com.technokratos.kirillakhmetov.dto;

public record ProductDto(
        String name,
        String measurementUnit,
        int quantity,
        double costPerUnit) {
}
