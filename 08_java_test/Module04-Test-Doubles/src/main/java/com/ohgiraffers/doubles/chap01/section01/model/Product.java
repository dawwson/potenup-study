package com.ohgiraffers.doubles.chap01.section01.model;


// DTO 또는 VO (데이터 객체)
public class Product {
    private String name;
    public Product(String name) { this.name = name; }
    public String getName() { return name; }
    public String getNameWithPrefix() { return "[Product] " + this.name; }
}