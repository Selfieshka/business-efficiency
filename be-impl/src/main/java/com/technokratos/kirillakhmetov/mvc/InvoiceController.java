package com.technokratos.kirillakhmetov.mvc;

import com.technokratos.kirillakhmetov.form.InvoiceForm;
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

    @GetMapping
    public String getInvoice(Model model) {
        model.addAttribute("owner", ownerServiceImpl.getProfileInfo("kirill@gmail.com"));
        model.addAttribute("invoices", invoiceServiceImpl.getAllInvoicesByOwnerId(100000L));
        return "invoices";
    }

    @PostMapping
    public String create(@ModelAttribute InvoiceForm invoiceForm) {
        invoiceServiceImpl.saveInvoiceInfo(100000L, invoiceForm);
        return "redirect:/invoices";
    }
}
