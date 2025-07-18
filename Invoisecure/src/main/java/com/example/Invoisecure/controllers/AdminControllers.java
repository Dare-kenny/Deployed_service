package com.example.Invoisecure.controllers;

import com.example.Invoisecure.models.InvoiceRules;
import com.example.Invoisecure.services.AdminService;
import com.example.Invoisecure.services.GeneralServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminControllers {

    @Autowired
    private AdminService adminService;

    @Autowired
    private GeneralServices generalService;

    @GetMapping("")
    public String showAdminPage(Model model){
        model.addAttribute("authentication.name",generalService.getUsername());
        adminService.LoadInvoices(model);
        return "admin_dashboard";
    }

    @GetMapping("/rules")
    public String showRulesPage(@RequestParam(value = "saved",required = false)String saved, Model model){
        adminService.LoadInvoiceRules(model);
        model.addAttribute("saved",saved != null);
        return "rules";
    }

    @PostMapping("/rules/saved")
    public String savedRules(@ModelAttribute InvoiceRules updated_rules,Model model){
        adminService.updateRules(updated_rules, model);
        return "redirect:/admin/rules?saved=true";
    }
}
