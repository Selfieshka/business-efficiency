package com.technokratos.kirillakhmetov.form;

import java.time.LocalDate;

public record ExpenseForm(
        Double amount,
        String category,
        LocalDate date) {
}
