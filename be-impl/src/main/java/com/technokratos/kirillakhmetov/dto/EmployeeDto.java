package com.technokratos.kirillakhmetov.dto;

import java.time.LocalDate;
import java.util.List;

public record EmployeeDto(Long ownerId, String firstName, String lastName, String patronymic, LocalDate effectiveDate,
                          List<String> position, Integer salary) {
}
