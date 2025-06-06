package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.entity.BankAccount;
import com.technokratos.kirillakhmetov.entity.Owner;
import com.technokratos.kirillakhmetov.form.BankAccountForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Controller;

@Controller
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankAccountMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner")
    BankAccount toBankAccount(
            BankAccountForm bankAccountForm,
            Owner owner);
}
