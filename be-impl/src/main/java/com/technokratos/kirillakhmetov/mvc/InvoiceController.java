package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.dto.response.OwnerResponse;
import com.technokratos.kirillakhmetov.form.InvoiceForm;
import com.technokratos.kirillakhmetov.security.UserContextHolder;
import com.technokratos.kirillakhmetov.service.InvoiceService;
import com.technokratos.kirillakhmetov.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceServiceImpl;
    private final OwnerService ownerServiceImpl;
    private final UserContextHolder userContextHolderImpl;

    @GetMapping
    public String getInvoice(Model model) {
        OwnerResponse owner = ownerServiceImpl.getProfileInfo(userContextHolderImpl
                .getUserIdFromSecurityContext());
        model.addAttribute("owner", owner);
        model.addAttribute("invoices",
                invoiceServiceImpl.getAllInvoicesByOwnerId(userContextHolderImpl
                        .getUserIdFromSecurityContext()));
        return "invoices";
    }

    @PostMapping
    public String create(@ModelAttribute InvoiceForm invoiceForm) {
        invoiceServiceImpl.saveInvoiceInfo(userContextHolderImpl
                .getUserIdFromSecurityContext(), invoiceForm);
        return "redirect:/invoices";
    }
}
