package com.example.Invoisecure.models;

import com.example.Invoisecure.enum_package.Status;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "invoice_details_table")
public class InvoiceDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String invoiceNo;
    @Column
    private String vendorName;
    @Column
    private String submittedName;
    @Column
    private LocalDate invoiceDate;
    @Column
    private String itemDescription;
    @Column
    private Double unitPrice;
    @Column
    private Integer quantity;
    @Column
    private Double totalAmount;
    @Enumerated(EnumType.STRING)
    @Column
    private Status approvalStatus;
    @Column
    private Long personnel_id;

    public InvoiceDetails() {
    }

    public InvoiceDetails(String invoiceNo, String vendorName, String submittedName, LocalDate invoiceDate, String itemDescription, Double unitPrice, Integer quantity, Double totalAmount, Status approvalStatus,Long personnel_id) {
        this.invoiceNo = invoiceNo;
        this.vendorName = vendorName;
        this.submittedName = submittedName;
        this.invoiceDate = invoiceDate;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.approvalStatus = approvalStatus;
        this.personnel_id = personnel_id;
    }

    public InvoiceDetails(InvoiceDetailsDTO dto){
        this.invoiceNo = dto.getInvoiceNo();
        this.vendorName = dto.getVendorName();
        this.invoiceDate = LocalDate.parse(dto.getInvoiceDate());
        this.itemDescription = dto.getItemDescription();
        this.unitPrice = Double.valueOf(dto.getUnitPrice());
        this.quantity = Integer.valueOf(dto.getQuantity());
        this.totalAmount = Double.valueOf(dto.getTotalAmount());
    }

    public Long getId() {
        return id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getSubmittedName() {
        return submittedName;
    }

    public void setSubmittedName(String submittedName) {
        this.submittedName = submittedName;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Status getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Status approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonnel_id() {
        return personnel_id;
    }

    public void setPersonnel_id(Long personnel_id) {
        this.personnel_id = personnel_id;
    }
}
