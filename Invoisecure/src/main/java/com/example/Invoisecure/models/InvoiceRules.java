package com.example.Invoisecure.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoice_rules_table")
public class InvoiceRules {

    @Id
    private Long id;
    @Column
    private Double minAmount;
    @Column
    private Double maxAmount;
    @Column(name = "block_future_dates")
    private boolean blockFutureDate;
    @Column(name = "max_days_old")
    private Long maxDaysOld;

    public InvoiceRules() {}

    public InvoiceRules(Double minAmount, Double maxAmount, boolean blockFutureDate, Long maxDaysOld) {
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.blockFutureDate = blockFutureDate;
        this.maxDaysOld = maxDaysOld;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public boolean isBlockFutureDate() {
        return blockFutureDate;
    }

    public void setBlockFutureDate(boolean blockFutureDate) {
        this.blockFutureDate = blockFutureDate;
    }

    public Long getMaxDaysOld() {
        return maxDaysOld;
    }

    public void setMaxDaysOld(Long maxDaysOld) {
        this.maxDaysOld = maxDaysOld;
    }
}
