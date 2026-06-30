package com.electropro.electropos.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Column(unique = true)
    private String sku;

    private BigDecimal price;

    private BigDecimal costPrice;

    private Integer quantity;

    private Integer lowStockThreshold;

    @Column(unique = true)
    private String barcode;

    private String imageUrl;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {

    }

    public Product(String name, String sku, BigDecimal price, BigDecimal costPrice,
                   Integer quantity, Integer lowStockThreshold, String barcode, String imageUrl) {
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.costPrice = costPrice;
        this.quantity = quantity;
        this.lowStockThreshold = lowStockThreshold;
        this.barcode = barcode;
        this.imageUrl = imageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(Integer lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}