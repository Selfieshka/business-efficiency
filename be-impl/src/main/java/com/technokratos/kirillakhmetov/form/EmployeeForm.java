package com.technokratos.kirillakhmetov.form;

import java.time.LocalDate;
import java.util.List;

public record EmployeeForm(
        String firstName,
        String lastName,
        String patronymic,
        LocalDate effectiveDate,
        List<String> positions,
        Integer salary) {
}
