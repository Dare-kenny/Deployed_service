package com.example.Invoisecure.services;

import com.example.Invoisecure.configurations.InvoiceDetailsRepository;
import com.example.Invoisecure.configurations.InvoiceRulesRepository;
import com.example.Invoisecure.configurations.PersonnelRepository;
import com.example.Invoisecure.models.InvoiceDetails;
import com.example.Invoisecure.models.InvoiceRules;
import com.example.Invoisecure.models.Personnel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneralServices {

    @Autowired
    private PersonnelRepository personnelRepository;



    public String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public List<Personnel> LoadIntoConfig(){
        return personnelRepository.findAll();
    }


}
