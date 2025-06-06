package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.dto.response.OwnerResponse;
import com.technokratos.kirillakhmetov.entity.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerMapper {
    OwnerResponse toResponse(Owner owner);
}
