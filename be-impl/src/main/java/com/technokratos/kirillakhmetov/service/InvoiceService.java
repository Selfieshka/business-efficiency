package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.response.InvoiceResponse;
import com.technokratos.kirillakhmetov.form.InvoiceForm;

import java.util.List;

public interface InvoiceService {
    void saveInvoiceInfo(Long ownerId, InvoiceForm invoiceForm);

    List<InvoiceResponse> getAllInvoicesByOwnerId(Long ownerId);

    void deleteInvoiceById(Long ownerId, long invoiceId);
}
