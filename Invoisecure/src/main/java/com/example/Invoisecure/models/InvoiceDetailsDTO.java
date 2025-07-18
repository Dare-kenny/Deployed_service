package com.example.Invoisecure.models;

public class InvoiceDetailsDTO {

    private String invoiceNo;
    private String vendorName;
    private String invoiceDate;
    private String itemDescription;
    private String unitPrice;
    private String quantity;
    private String totalAmount;

    public InvoiceDetailsDTO() {}

    public InvoiceDetailsDTO(String invoiceNo, String vendorName, String invoiceDate,
                             String itemDescription, String unitPrice, String quantity, String totalAmount) {
        this.invoiceNo = invoiceNo;
        this.vendorName = vendorName;
        this.invoiceDate = invoiceDate;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
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

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
