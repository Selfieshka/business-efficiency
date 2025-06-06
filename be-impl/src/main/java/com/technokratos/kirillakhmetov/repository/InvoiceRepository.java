package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.dto.ProductDto;
import com.technokratos.kirillakhmetov.dto.response.InvoiceResponse;
import com.technokratos.kirillakhmetov.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query(
            """
                    SELECT i
                    FROM Invoice i
                    WHERE i.owner.id = :ownerId
                    """
    )
    void saveInvoiceWithProducts(InvoiceResponse invoiceResponse, List<ProductDto> products);

    @Query(
            """
                    SELECT i
                    FROM Invoice i
                    WHERE i.owner.id = :ownerId
                    """
    )
    List<Invoice> findAllLazyByOwnerId(@Param("ownerId") Long ownerId);
}
