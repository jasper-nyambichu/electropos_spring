package com.electropro.electropos.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class GiftCard {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String code;

    private BigDecimal initialValue;
    private BigDecimal currentBalance;
    private LocalDate expiryDate;
    private String status;

    public GiftCard() {
    }

    public GiftCard(String code, BigDecimal initialValue, BigDecimal currentBalance, LocalDate expiryDate, String status) {
        this.code = code;
        this.initialValue = initialValue;
        this.currentBalance = currentBalance;
        this.expiryDate = expiryDate;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}