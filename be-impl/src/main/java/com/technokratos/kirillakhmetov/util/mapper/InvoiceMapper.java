package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.dto.response.InvoiceResponse;
import com.technokratos.kirillakhmetov.entity.Invoice;
import com.technokratos.kirillakhmetov.entity.Owner;
import com.technokratos.kirillakhmetov.form.InvoiceForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InvoiceMapper {
    @Mapping(target = "id", ignore = true)
    Invoice toEntity(
            InvoiceForm invoiceForm,
            Owner owner);

    @Mapping(target = "sum", expression = "java(invoice.getProducts().stream().mapToDouble(p -> p.getQuantity() * p.getCostPerUnit()).sum())")
    @Mapping(target = "totalCountProducts", expression = "java(invoice.getProducts().stream().mapToInt(p -> p.getQuantity()).sum())")
    @Mapping(target = "countPositions", expression = "java(invoice.getProducts().size())")
    @Mapping(source = "id", target = "invoiceId")
    InvoiceResponse toResponse(Invoice invoice);

    List<InvoiceResponse> toResponse(List<Invoice> invoices);
}
