package com.ohgiraffers.valueobject.chap02.section01;

import jakarta.persistence.*;

@Entity(name = "section01_available")
@Table(name = "product_available_sizes_section01")
public class AvailableSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "label")
    private String label;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    protected AvailableSize() {}

    public AvailableSize(String label, int stockQuantity) {
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("사이즈의 라벨은 필수입니다.");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("재고 수량은 0 이상이어야 합니다.");
        }

        this.label = label;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getLabel() {
        return label;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void decreaseStock(int stockQuantity) {
        if (this.stockQuantity - stockQuantity < 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stockQuantity -= stockQuantity;
    }

    @Override
    public String toString() {
        return label + "(재고 : " + stockQuantity + ")";
    }
}