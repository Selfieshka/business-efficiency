package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.dto.InvoiceDto;
import com.technokratos.kirillakhmetov.dto.ProductDto;
import com.technokratos.kirillakhmetov.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    void saveInvoiceWithProducts(InvoiceDto invoiceDto, List<ProductDto> products);

    List<Invoice> findAllLazyByOwnerId(Long id);
}
