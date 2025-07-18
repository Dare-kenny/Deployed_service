package com.example.Invoisecure.configurations;

import com.example.Invoisecure.models.InvoiceDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetails,Long> {

    List<InvoiceDetails> findAllBySubmittedName(String submittedName);
}
