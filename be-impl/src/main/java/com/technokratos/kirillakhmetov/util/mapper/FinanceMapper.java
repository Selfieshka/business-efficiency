package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.dto.response.FinancePaginationResponse;
import com.technokratos.kirillakhmetov.entity.Finance;
import com.technokratos.kirillakhmetov.entity.Owner;
import com.technokratos.kirillakhmetov.form.ExpenseForm;
import com.technokratos.kirillakhmetov.form.RevenueForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = java.time.format.DateTimeFormatter.class)
public interface FinanceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type")
    @Mapping(target = "owner")
    Finance toFinance(ExpenseForm expenseForm,
                      Owner owner,
                      String type);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type")
    @Mapping(target = "owner")
    Finance toFinance(RevenueForm revenueForm,
                      Owner owner,
                      String type);

    @Mapping(target = "date", expression = "java(finance.getDate().format(java.time.format.DateTimeFormatter.ofPattern(\"dd-MM-yyyy\")))")
    FinancePaginationResponse toResponse(Finance finance);

    List<FinancePaginationResponse> toResponse(List<Finance> finances);
}
