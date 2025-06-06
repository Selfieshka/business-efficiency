package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.dto.response.OwnerResponse;
import com.technokratos.kirillakhmetov.entity.Owner;
import com.technokratos.kirillakhmetov.form.RegistrationForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper {
    OwnerResponse toResponse(Owner owner);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password")
    Owner toEntity(RegistrationForm registrationForm, String password);
}
