package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.dto.ProductDto;
import com.technokratos.kirillakhmetov.entity.Invoice;
import com.technokratos.kirillakhmetov.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "invoice")
    Product toEntity(ProductDto productDto,
                     Invoice invoice);

    List<Product> toEntity(
            List<ProductDto> products);
}
