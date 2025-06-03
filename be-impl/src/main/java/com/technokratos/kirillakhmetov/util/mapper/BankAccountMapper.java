package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.dto.BankAccountDto;
import com.technokratos.kirillakhmetov.entity.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Controller;

@Controller
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankAccountMapper {
    BankAccount toBankAccount(BankAccountDto bankAccountDto);
}
