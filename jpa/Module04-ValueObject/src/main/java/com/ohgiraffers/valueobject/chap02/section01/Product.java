package com.ohgiraffers.valueobject.chap02.section01;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "section01_product")
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableSize> availableSizes = new ArrayList<>();

    protected Product() {}

    public Product(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<AvailableSize> getAvailableSizes() {
        return availableSizes;
    }

    /**
     * 상품에 새로운 사이즈를 추가한다.
     */
    public void addAvailableSize(AvailableSize availableSize) {
        this.availableSizes.add(availableSize);
        availableSize.setProduct(this);
    }

    /**
     * 상품에서 특정 사이즈를 제거한다.
     */
    public void removeAvailableSize(AvailableSize availableSize) {
        this.availableSizes.remove(availableSize);
        availableSize.setProduct(null);
    }

    /**
     * 입력된 사이즈와 주문수 만큼 감소시킨다.
     * @param sizeLabel
     * @param quantity
     */
    public void decreaseStock(String sizeLabel, int quantity) {
        for (AvailableSize availableSize : availableSizes) {
            if (availableSize.getLabel().matches(sizeLabel)) {
                availableSize.decreaseStock(quantity);
                return;
            }
        }

        throw new IllegalArgumentException("해당 사이즈의 상품은 존재하지 않습니다.");
    }
}
