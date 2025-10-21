package com.ohgiraffers.chap01.section03;

import jakarta.persistence.*;

/**
 * Embedded 타입
 * > 값 객체 (Value Object)의 대표 사례
 */

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Embedded // VO -> JPA에서 변환을 해줘야 한다는 의미
    private Money price;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="name", column = @Column(name = "manufacturer_name")),
            @AttributeOverride(name = "country", column = @Column(name = "Manufacturer_country"))
    })
    private Manufacturer manufacturer;
}
