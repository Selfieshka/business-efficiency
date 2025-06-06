package com.technokratos.kirillakhmetov.form;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record InvoiceForm(
        String number,
        LocalDate date,
        String productName,
        String unitMeasure,
        String quantity,
        String costPerUnit,
        MultipartFile invoice) {
}
