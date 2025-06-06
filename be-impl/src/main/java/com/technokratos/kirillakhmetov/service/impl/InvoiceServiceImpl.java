package com.technokratos.kirillakhmetov.service.impl;

import com.technokratos.kirillakhmetov.dto.ProductDto;
import com.technokratos.kirillakhmetov.dto.response.InvoiceResponse;
import com.technokratos.kirillakhmetov.entity.Invoice;
import com.technokratos.kirillakhmetov.entity.Owner;
import com.technokratos.kirillakhmetov.entity.Product;
import com.technokratos.kirillakhmetov.form.InvoiceForm;
import com.technokratos.kirillakhmetov.repository.InvoiceRepository;
import com.technokratos.kirillakhmetov.repository.OwnerRepository;
import com.technokratos.kirillakhmetov.service.InvoiceService;
import com.technokratos.kirillakhmetov.util.ExcelReader;
import com.technokratos.kirillakhmetov.util.mapper.InvoiceMapper;
import com.technokratos.kirillakhmetov.util.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final OwnerRepository ownerRepository;
    private final InvoiceMapper invoiceMapper;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public void saveInvoiceInfo(Long ownerId, InvoiceForm invoiceForm) {
        validateFileExtension(invoiceForm.invoice().getOriginalFilename());

        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Владелец не найден"));

        Map<String, String> headerNames = Map.of(
                "productName", invoiceForm.productName(),
                "unitMeasure", invoiceForm.unitMeasure(),
                "quantity", invoiceForm.quantity(),
                "costPerUnit", invoiceForm.costPerUnit()
        );

        Invoice invoice = invoiceRepository.save(invoiceMapper.toEntity(
                invoiceForm,
                owner));

        List<Product> products = productMapper.toEntity(getProductsFromExcel(invoiceForm, headerNames));
        products.forEach(p -> p.setInvoice(invoice));

        invoice.setProducts(products);
        invoiceRepository.save(invoice);
    }

    @Override
    public List<InvoiceResponse> getAllInvoicesByOwnerId(Long ownerId) {
        return invoiceMapper.toResponse(invoiceRepository.findAllLazyByOwnerId(ownerId));
    }

    @Override
    public void deleteInvoiceById(Long ownerId, long invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }

    private List<ProductDto> getProductsFromExcel(InvoiceForm invoiceForm, Map<String, String> headerNames) {
        try (InputStream invoiceInputStream = invoiceForm.invoice().getInputStream()) {
            return ExcelReader.readAllProductsByColumns(invoiceInputStream, headerNames);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении файла накладной");
        }
    }

    private void validateFileExtension(String filename) {
        if (filename == null || !filename.endsWith(".xlsx")) {
            throw new IllegalArgumentException("Неподдерживаемый формат файла");
        }
    }
}
