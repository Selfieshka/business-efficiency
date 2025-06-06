package com.technokratos.kirillakhmetov.dto.response;

import java.time.LocalDate;
import java.util.List;

public record EmployeeResponse(
        Long id,
        Long ownerId,
        String firstName,
        String lastName,
        String patronymic,
        LocalDate effectiveDate,
        List<String> positions,
        Integer salary) {
}
