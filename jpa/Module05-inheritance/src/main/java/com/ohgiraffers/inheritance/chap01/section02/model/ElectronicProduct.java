package com.ohgiraffers.inheritance.chap01.section02.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ELECTRONIC")
public class ElectronicProduct extends Product {
    @Column(name = "warranty_period")
    private int warrantyPeriod;
    private String powerConsumption;

    protected  ElectronicProduct() {}

    public ElectronicProduct(String name, double price, String brand, int stockQuantity, int warrantyPeriod, String powerConsumption) {
        super(name, price, brand, stockQuantity);
        this.warrantyPeriod = warrantyPeriod;
        this.powerConsumption = powerConsumption;
    }

    @Override
    public String toString() {
        return "ElectronicProduct{" +
                "warrantyPeriod=" + warrantyPeriod +
                ", powerConsumption='" + powerConsumption + '\'' +
                '}';
    }
}
