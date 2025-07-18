package com.example.Invoisecure.services;

import com.example.Invoisecure.configurations.InvoiceDetailsRepository;
import com.example.Invoisecure.configurations.InvoiceRulesRepository;
import com.example.Invoisecure.models.InvoiceDetails;
import com.example.Invoisecure.models.InvoiceRules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private GeneralServices generalService;

    @Autowired
    private InvoiceDetailsRepository invoiceDetailsRepository;

    @Autowired
    private InvoiceRulesRepository invoiceRulesRepository;


    public Optional<InvoiceRules> rules;

    public List<InvoiceDetails> LoadInvoices(){
        return invoiceDetailsRepository.findAll();
    }

    public Optional<InvoiceRules> LoadRules(){
        return invoiceRulesRepository.findById(1L);
    }


    public void LoadInvoices(Model model){
        List<InvoiceDetails> AllInvoices = invoiceDetailsRepository.findAll();
        model.addAttribute("invoices",AllInvoices);
    }

    public void LoadInvoiceRules(Model model){
        rules = LoadRules();
        model.addAttribute("rules",rules);
    }

    public void updateRules(@ModelAttribute InvoiceRules updated_rules, Model model){


        model.addAttribute("rules",updated_rules);
        invoiceRulesRepository.findById(1L).map(
                presentRule -> {
                    presentRule.setMaxAmount(updated_rules.getMaxAmount());
                    presentRule.setMinAmount(updated_rules.getMinAmount());
                    presentRule.setBlockFutureDate(updated_rules.isBlockFutureDate());
                    presentRule.setMaxDaysOld(updated_rules.getMaxDaysOld());

                    return invoiceRulesRepository.save(presentRule);
                }
        ).orElseThrow(()-> new RuntimeException("Rules not saved properly"));

    }

}
