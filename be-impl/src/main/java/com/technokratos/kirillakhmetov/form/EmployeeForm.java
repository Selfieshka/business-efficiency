package com.technokratos.kirillakhmetov.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeForm {
    private Long ownerId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate effectiveDate;
    private List<String> positions;
    private Integer salary;
}
