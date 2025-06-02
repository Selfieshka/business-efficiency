package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.InvoiceDto;
import com.technokratos.kirillakhmetov.dto.ProductDto;
import com.technokratos.kirillakhmetov.entity.Invoice;
import com.technokratos.kirillakhmetov.repository.InvoiceRepository;
import com.technokratos.kirillakhmetov.util.ExcelReader;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public void saveInvoiceInfo(InvoiceDto invoiceDto, Part invoice, Map<String, String> headerNames) {
        List<ProductDto> products = analyze(invoice, headerNames);
        invoiceRepository.saveInvoiceWithProducts(invoiceDto, products);
    }

    private List<ProductDto> analyze(Part invoice, Map<String, String> headerNames) {
        try (InputStream invoiceInputStream = invoice.getInputStream()) {
            return ExcelReader.readAllProductsByColumns(invoiceInputStream, headerNames);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkExtension(Part part) {
        String fileName = part.getSubmittedFileName();
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toUpperCase();
        return fileExtension.equals(XSSFWorkbookType.XLSX.name());
    }

    public List<InvoiceDto> getAllInvoices(Long id) {
        List<Invoice> invoices = invoiceRepository.findAllLazyByOwnerId(id);
        List<InvoiceDto> invoiceDtos = new ArrayList<>();
        for (Invoice invoice : invoices) {
            invoiceDtos.add(new InvoiceDto(
                    invoice.getOwner().getId(),
                    invoice.getId(),
                    invoice.getNumber(),
                    invoice.getDate(),
                    (double) (100000 + (int) (Math.random() * (400000 - 100000))),
                    (int) (1 + (Math.random() * (20 - 1))),
                    (int) (20 + (Math.random() * (200 - 20)))
            ));
        }
        return invoiceDtos;
    }

    public void deleteInvoiceById(long invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }
}
