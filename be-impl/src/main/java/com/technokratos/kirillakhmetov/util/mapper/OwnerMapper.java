package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.dto.OwnerDto;
import com.technokratos.kirillakhmetov.entity.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper {
    Owner toOwner(OwnerDto ownerDto);

    OwnerDto toOwnerDto(Owner owner);
}
