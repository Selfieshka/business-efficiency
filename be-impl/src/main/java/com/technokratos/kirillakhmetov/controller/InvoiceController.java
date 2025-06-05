package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.dto.InvoiceDto;
import com.technokratos.kirillakhmetov.dto.OwnerDto;
import com.technokratos.kirillakhmetov.service.InvoiceService;
import com.technokratos.kirillakhmetov.service.OwnerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Controller()
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final OwnerService ownerService;

    @GetMapping
    public String getInvoice(Model model) {
        OwnerDto owner = ownerService.getProfileInfo("kirill@gmail.com");
        model.addAttribute("owner", owner);
        model.addAttribute("invoices", invoiceService.getAllInvoices(100000L));
        return "invoices";
    }

    @PostMapping
    public String create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part invoice = req.getPart("invoice");
        Map<String, String> headerNames = Map.of(
                "productName", req.getParameter("productName"),
                "unitMeasure", req.getParameter("unitMeasure"),
                "quantity", req.getParameter("quantity"),
                "costPerUnit", req.getParameter("costPerUnit")
        );

        if (invoiceService.checkExtension(invoice)) {
            invoiceService.saveInvoiceInfo(new InvoiceDto(
                    100000L,
                    null,
                    req.getParameter("number"),
                    LocalDate.parse(req.getParameter("date")),
                    null, null, null), invoice, headerNames);
            return "redirect:/invoices";
        } else {
            throw new RuntimeException("Неподдерживаемый тип файла");
        }
    }

    @DeleteMapping("/{invoiceId}")
    public void delete(@PathVariable Long invoiceId) {
        invoiceService.deleteInvoiceById(invoiceId);
    }
}
