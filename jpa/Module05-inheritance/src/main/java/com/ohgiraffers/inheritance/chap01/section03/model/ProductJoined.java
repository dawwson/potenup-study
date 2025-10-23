package com.ohgiraffers.inheritance.chap01.section03.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "product_type")
@Table(name = "products_joined")
public class ProductJoined {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;
    private double price;
    private String brand;
    private int stockQuantity;

    protected ProductJoined() {}

    public ProductJoined(String name, double price, String brand, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
    }
}
