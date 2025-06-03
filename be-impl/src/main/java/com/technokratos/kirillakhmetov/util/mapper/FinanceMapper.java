package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.dto.FinanceDto;
import com.technokratos.kirillakhmetov.entity.Finance;
import com.technokratos.kirillakhmetov.entity.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FinanceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type")
    @Mapping(target = "owner")
    Finance toFinance(FinanceDto financeDto,
                      Owner owner,
                      String type);
}
