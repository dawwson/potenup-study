package com.ohgiraffers.valueobject.chap02.section02;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "section02_product_entity")
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "product_available_sizes", joinColumns = @JoinColumn(name = "product_id"))
    private List<AvailableSize> availableSizes = new ArrayList<AvailableSize>();

    protected Product(String name) {
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

    /*
     * addAvailableSize
     * - 상품에 새로운 가능한 사이즈를 추가한다.
     */
    public void addAvailableSize(AvailableSize availableSize) {
        this.availableSizes.add(availableSize);
    }

    /*
     * removeAvailableSize
     * - 상품에서 특정 사이즈를 제거한다.
     */
    public void removeAvailableSize(AvailableSize availableSize) {
        this.availableSizes.removeIf(size -> size.equals(availableSize)); // 이름을 기준으로 비교
    }

    /**
     * decreaseStock 메서드
     * - 특정 사이즈의 재고 수량을 감소
     * - 컬렉션에서 해당 사이즈를 찾고 재고 감소 로직을 수행
     */
    public void decreaseStock(String sizeLabel, int quantity) {
        for (AvailableSize availableSize : availableSizes) {
            if (availableSize.getLabel().equals(sizeLabel)) {
                availableSize.decreaseStock(quantity);
                return;
            }
        }
        throw new IllegalArgumentException("해당 사이즈의 상품이 없습니다. -> " + sizeLabel);
    }
}
