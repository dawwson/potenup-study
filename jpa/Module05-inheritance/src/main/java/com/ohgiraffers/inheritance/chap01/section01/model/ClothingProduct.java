package com.ohgiraffers.inheritance.chap01.section01.model;

public class ClothingProduct {
    // 공통된 필드
    private String name;
    private double price;
    private String brand;
    private int stockQuantity;

    private String size;
    private String material;

    public ClothingProduct(String name, double price, String brand, int stockQuantity, String size, String material) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
        this.size = size;
        this.material = material;
    }
}
