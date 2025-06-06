package com.technokratos.kirillakhmetov.form;

import java.time.LocalDate;

public record RevenueForm(
        Double amount,
        String category,
        LocalDate date) {
}
