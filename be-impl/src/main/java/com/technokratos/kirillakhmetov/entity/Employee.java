package com.technokratos.kirillakhmetov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Employee {
    private Long id;
    private Long ownerId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate effectiveDate;
    private Integer salary;
    private List<String> position;
}
