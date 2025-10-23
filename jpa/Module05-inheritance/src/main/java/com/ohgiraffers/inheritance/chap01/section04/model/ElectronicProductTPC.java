package com.ohgiraffers.inheritance.chap01.section04.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "electronic_product_tpc")
public class ElectronicProductTPC extends ProductTPC {
    private int warrantyPeriod;
    private String powerConsumption;

    protected ElectronicProductTPC(){}

    public ElectronicProductTPC(String name, double price, String brand, int stockQuantity, int warrantyPeriod, String powerConsumption) {
        super(name, price, brand, stockQuantity);
        this.warrantyPeriod = warrantyPeriod;
        this.powerConsumption = powerConsumption;
    }

    @Override
    public String toString() {
        return "ElectronicProductTPC{" +
                "warrantyPeriod=" + warrantyPeriod +
                ", powerConsumption='" + powerConsumption + '\'' +
                '}';
    }
}
