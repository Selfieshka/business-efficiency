package com.technokratos.kirillakhmetov.dto.response;

import java.util.List;

public record ProfitResponse(
        List<String> date,
        List<String> amount,
        String forecastDate,
        String forecastAmount) {
}
