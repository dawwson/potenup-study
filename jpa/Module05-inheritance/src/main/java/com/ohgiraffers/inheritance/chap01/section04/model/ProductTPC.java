package com.ohgiraffers.inheritance.chap01.section04.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ProductTPC {
    @Id
    // 1차 캐싱에서 id가 중복되는 문제때문에 auto increment를 쓰지 않음
    // mysql은 sequence가 없는데, sequence 생성기를 만들어서 쓴다.
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_generator")
    @SequenceGenerator(name = "product_id_generator", sequenceName = "product_id_seq", allocationSize = 1)
    @Column(name = "product_id")
    private Long id;

    private String name;
    private double price;
    private String brand;
    private int stockQuantity;

    protected ProductTPC() {}

    public ProductTPC(String name, double price, String brand, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
    }
}
