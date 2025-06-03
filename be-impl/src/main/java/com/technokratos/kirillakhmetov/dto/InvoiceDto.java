package com.technokratos.kirillakhmetov.dto;

import java.time.LocalDate;

public record InvoiceDto(
        Long ownerId,
        Long invoiceId,
        String number,
        LocalDate date,
        Double sum,
        Integer count,
        Integer countTov) {
}
