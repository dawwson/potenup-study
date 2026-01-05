package com.ohgiraffers.doubles.chap02.section01.model;

public class Product {
    Long id;
    String name;

    public Product(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getName() { return name; }
    public Long getId() { return id; }
}