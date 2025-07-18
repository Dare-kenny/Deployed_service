package com.example.Invoisecure.services;

import com.example.Invoisecure.configurations.InvoiceDetailsRepository;
import com.example.Invoisecure.configurations.InvoiceRulesRepository;
import com.example.Invoisecure.configurations.PersonnelRepository;
import com.example.Invoisecure.enum_package.Status;
import com.example.Invoisecure.models.InvoiceDetails;
import com.example.Invoisecure.models.InvoiceDetailsDTO;
import com.example.Invoisecure.models.InvoiceRules;
import com.example.Invoisecure.models.Personnel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
public class UserServices {

    @Autowired
    private GeneralServices generalService;

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;

    @Autowired
    private InvoiceRulesRepository invoiceRulesRepository;

    @Autowired
    private PersonnelRepository personnelRepository;


    private final Map<String, List<String>> fieldSynonyms = Map.of(
            "invoiceNo", List.of("Invoice No", "invoiceNo", "Invoice Number", "invoice_number", "InvoiceNumber", "Bill No", "Invoice #", "Bill_Number"),
            "vendorName", List.of("vendorName", "VendorName", "Vendor Name", "vendor name", "vendor", "Vendor", "Supplier", "Company", "vendor_name"),
            "invoiceDate", List.of("InvoiceDate", "invoice Date", "invoice date", "Invoice Date", "Bill Date", "Date", "date", "invoice_date"),
            "itemDescription", List.of("Description", "Item Description", "itemDescription", "item description", "Item", "Product","ProductName","ProductDescription","Product Name","Product Description", "Details", "item_details", "item_desc"),
            "unitPrice", List.of("Unit Price", "unitPrice", "unit_price", "Rate", "Price/Unit", "UnitRate"),
            "quantity", List.of("Quantity", "Qty", "quantity", "qty", "QTY", "Amount", "Amount/Unit"),
            "totalAmount", List.of("Total", "totalAmount", "total_amount", "Total Amount", "Amount Due", "TotalAmount", "AmountDue")
    );

    // Normalize any label or header to match a consistent format
    private String normalize(String input) {
        return input.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    // Clean currency string by removing non-numeric characters except the decimal
    private String cleanCurrency(String value) {
        if (value == null) return "0.0";
        return value.replaceAll("[^\\d.]", "");
    }

    // Main method to extract invoice details from PDF in label:value format
    public InvoiceDetailsDTO extractInvoiceDetails(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {

            // Extract all text from the PDF
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            System.out.println("Extracted text:\n" + text);

            Map<String, String> extracted = new HashMap<>();
            String[] lines = text.split("\\R");

            // Go through each line in format {label}:{value}
            for (String line : lines) {
                if (!line.contains(":") && !line.contains("-")) continue;

                String[] parts = line.split("[:\\-]", 2);
                if (parts.length < 2) continue;

                String rawKey = normalize(parts[0].trim());
                String value = parts[1].trim();

                for (Map.Entry<String, List<String>> entry : fieldSynonyms.entrySet()) {
                    for (String synonym : entry.getValue()) {
                        if (normalize(synonym).equals(rawKey)) {
                            extracted.putIfAbsent(entry.getKey(), value);
                            break;
                        }
                    }
                }
            }

            // Check if all required fields were extracted
            boolean valid = fieldSynonyms.keySet().stream().allMatch(extracted::containsKey);
            if (!valid) {
                System.out.println("INVALID INVOICE MISSING SOME FIELDS");
                return new InvoiceDetailsDTO(); // or return null / throw exception
            }

            // Clean and return the parsed values in a DTO
            return new InvoiceDetailsDTO(
                    extracted.get("invoiceNo"),
                    extracted.get("vendorName"),
                    extracted.get("invoiceDate"),
                    extracted.get("itemDescription"),
                    cleanCurrency(extracted.get("unitPrice")),
                    extracted.get("quantity"),
                    cleanCurrency(extracted.get("totalAmount"))
            );

        } catch (Exception e) {
            System.err.println("FAILED TO PARSE FILE:");
            e.printStackTrace();
            return new InvoiceDetailsDTO(); // avoid 500 error
        }
    }



    public Long findIdByLoggedInName(String username){
        Personnel personnel = personnelRepository.findByUsername(username);
        return personnel.getId();
    }
    public void SaveInvoice(@ModelAttribute InvoiceDetailsDTO dto) {
        InvoiceDetails invoiceDetails = new InvoiceDetails(dto);
        Optional<InvoiceRules> rules = invoiceRulesRepository.findById(1L);

        if (rules.isPresent()) {
            InvoiceRules rule = rules.get();


            boolean outOfAmountRange = invoiceDetails.getTotalAmount() >= rule.getMaxAmount()
                    || invoiceDetails.getTotalAmount() <= rule.getMinAmount();

            boolean isFutureDate = invoiceDetails.getInvoiceDate().isAfter(LocalDate.now());
            boolean isTooOld = ChronoUnit.DAYS.between(invoiceDetails.getInvoiceDate(), LocalDate.now()) > 8;

            if (outOfAmountRange || isFutureDate || isTooOld) {
                invoiceDetails.setApprovalStatus(Status.NOT_APPROVED);
            } else {
                invoiceDetails.setApprovalStatus(Status.APPROVED);
            }

            invoiceDetails.setSubmittedName(generalService.getUsername());
            invoiceDetails.setPersonnel_id(findIdByLoggedInName(generalService.getUsername()));

        }

        invoiceDetailsRepository.save(invoiceDetails);
    }


    public void loadInvoiceSchema(Model model) {
        List<InvoiceDetails> filteredInvoices = invoiceDetailsRepository.findAllBySubmittedName(generalService.getUsername());
        model.addAttribute("invoices", filteredInvoices);
    }
}
