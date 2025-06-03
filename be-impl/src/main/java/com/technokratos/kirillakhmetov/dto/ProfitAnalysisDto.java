package com.technokratos.kirillakhmetov.dto;

import java.sql.Date;

public record ProfitAnalysisDto(
        Date date,
        Double amount) {
}