package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.dto.FinanceDto;
import com.technokratos.kirillakhmetov.entity.Finance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FinanceMapper {
    @Mapping(target = "type")
    Finance toFinance(FinanceDto financeDto,
                      String type);
}
