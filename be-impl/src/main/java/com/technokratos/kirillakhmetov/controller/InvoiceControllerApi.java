package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.api.InvoiceApi;
import com.technokratos.kirillakhmetov.security.UserContextHolder;
import com.technokratos.kirillakhmetov.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InvoiceControllerApi implements InvoiceApi {
    private final InvoiceService invoiceServiceImpl;
    private final UserContextHolder userContextHolderImpl;

    @Override
    public void delete(Long invoiceId) {
        invoiceServiceImpl.deleteInvoiceById(userContextHolderImpl
                .getUserIdFromSecurityContext(), invoiceId);
    }
}
