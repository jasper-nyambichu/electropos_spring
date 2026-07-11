package com.electropro.electropos.entity;

import jakarta.persistence.*;

@Entity
public class BusinessSettings {

    @Id
    private Integer id;

    private String businessName;
    private String kraPin;

    @Column(length = 1000)
    private String address;

    private String currency;
    private String timezone;

    @Column(length = 2000)
    private String receiptHeaderText;

    @Column(length = 2000)
    private String receiptFooterText;

    private boolean autoGenerateSerialNumbers;
    private boolean thermalPrinterMode;

    public BusinessSettings() {
    }

    public BusinessSettings(Integer id, String businessName, String kraPin, String address,
                            String currency, String timezone, String receiptHeaderText,
                            String receiptFooterText, boolean autoGenerateSerialNumbers,
                            boolean thermalPrinterMode) {
        this.id = id;
        this.businessName = businessName;
        this.kraPin = kraPin;
        this.address = address;
        this.currency = currency;
        this.timezone = timezone;
        this.receiptHeaderText = receiptHeaderText;
        this.receiptFooterText = receiptFooterText;
        this.autoGenerateSerialNumbers = autoGenerateSerialNumbers;
        this.thermalPrinterMode = thermalPrinterMode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getKraPin() {
        return kraPin;
    }

    public void setKraPin(String kraPin) {
        this.kraPin = kraPin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getReceiptHeaderText() {
        return receiptHeaderText;
    }

    public void setReceiptHeaderText(String receiptHeaderText) {
        this.receiptHeaderText = receiptHeaderText;
    }

    public String getReceiptFooterText() {
        return receiptFooterText;
    }

    public void setReceiptFooterText(String receiptFooterText) {
        this.receiptFooterText = receiptFooterText;
    }

    public boolean isAutoGenerateSerialNumbers() {
        return autoGenerateSerialNumbers;
    }

    public void setAutoGenerateSerialNumbers(boolean autoGenerateSerialNumbers) {
        this.autoGenerateSerialNumbers = autoGenerateSerialNumbers;
    }

    public boolean isThermalPrinterMode() {
        return thermalPrinterMode;
    }

    public void setThermalPrinterMode(boolean thermalPrinterMode) {
        this.thermalPrinterMode = thermalPrinterMode;
    }
}