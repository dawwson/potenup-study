package com.ohgiraffers.inheritance.chap01.section01.model;

import java.time.LocalDate;

public class FoodProduct {
    // 공통된 필드
    private String name;
    private double price;
    private String brand;
    private int stockQuantity;

    private LocalDate expirationDate;
    private boolean isOrganic;

    public FoodProduct(String name, double price, String brand, int stockQuantity, LocalDate expirationDate, boolean isOrganic) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
        this.expirationDate = expirationDate;
        this.isOrganic = isOrganic;
    }
}
