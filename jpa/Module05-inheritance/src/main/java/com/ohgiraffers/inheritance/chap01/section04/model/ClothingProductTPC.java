package com.ohgiraffers.inheritance.chap01.section04.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clothing_products_tpc")
public class ClothingProductTPC extends ProductTPC {
    private String size;
    private String material;
    private String color;

    protected ClothingProductTPC(){}

    public ClothingProductTPC(String name, double price, String brand, int stockQuantity, String size, String material, String color) {
        super(name, price, brand, stockQuantity);
        this.size = size;
        this.material = material;
        this.color = color;
    }

    @Override
    public String toString() {
        return "ClothingProductTPC{" +
                "size='" + size + '\'' +
                ", material='" + material + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
