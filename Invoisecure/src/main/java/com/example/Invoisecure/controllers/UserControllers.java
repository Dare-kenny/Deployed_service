package com.example.Invoisecure.controllers;

import com.example.Invoisecure.models.InvoiceDetails;
import com.example.Invoisecure.models.InvoiceDetailsDTO;
import com.example.Invoisecure.services.GeneralServices;
import com.example.Invoisecure.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserControllers {

    @Autowired
    private GeneralServices generalService;

    @Autowired
    private UserServices userService;

    @GetMapping("")
    public String showUserPage(Model model){
        model.addAttribute("authentication.name",generalService.getUsername());
        userService.loadInvoiceSchema(model);
        model.addAttribute("invoiceForm" ,new InvoiceDetailsDTO());
        return "finance_dashboard";
    }

    @PostMapping("/invoice/extract")
    public ResponseEntity<Map<String,Object>> extractInvoice(@RequestParam("file")MultipartFile file){
        System.out.println(" Received file: " + file.getOriginalFilename());
        InvoiceDetailsDTO dto = userService.extractInvoiceDetails(file);
        System.out.println(" Extracted: " + dto);

        boolean isInvalid = dto.getInvoiceNo().isBlank() || dto.getVendorName().isBlank()
                || dto.getInvoiceDate().isBlank() || dto.getItemDescription().isBlank()
                || dto.getUnitPrice().isBlank() || dto.getQuantity().isBlank() || dto.getTotalAmount().isBlank();



        Map<String, Object> body = new HashMap<>();
        body.put("invoiceNo", dto.getInvoiceNo());
        body.put("vendorName", dto.getVendorName());
        body.put("invoiceDate", dto.getInvoiceDate());
        body.put("itemDescription", dto.getItemDescription());
        body.put("unitPrice", dto.getUnitPrice());
        body.put("quantity", dto.getQuantity());
        body.put("totalAmount", dto.getTotalAmount());
        body.put("valid",!isInvalid);
        return ResponseEntity.ok(body);
    }


    @PostMapping("/invoice/submit")
    public String submitInvoice(@ModelAttribute InvoiceDetailsDTO dto , Model model){
        userService.SaveInvoice(dto);
        userService.loadInvoiceSchema(model);
        return "redirect:/user";
    }

}
