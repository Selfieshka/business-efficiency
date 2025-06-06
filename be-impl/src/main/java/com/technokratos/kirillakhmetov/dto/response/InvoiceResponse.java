package com.technokratos.kirillakhmetov.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record InvoiceResponse(
        Long ownerId,
        Long invoiceId,
        String number,
        LocalDate date,
        Double sum,
        Integer countPositions,
        Integer totalCountProducts) {
}
