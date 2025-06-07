package com.technokratos.kirillakhmetov.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueForm {
    @NotNull(message = "Поле \"Сумма\" не может быть пустым")
    @Positive(message = "Поле \"Сумма\" должно быть больше 0")
    private Double amount;
    @NotBlank(message = "Поле \"Категория\" не может быть пустым")
    private String category;
    @NotNull(message = "Поле \"Дата\" не может быть пустым")
    private LocalDate date;
}
