package com.technokratos.kirillakhmetov.dto.response;

public record MonthInfoResponse(
        String currentDate,
        double revenue,
        double expenses,
        double record) {
}
