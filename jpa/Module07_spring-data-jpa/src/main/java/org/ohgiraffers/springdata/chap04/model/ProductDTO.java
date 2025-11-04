package org.ohgiraffers.springdata.chap04.model;

public class ProductDTO {
    private Integer productId;
    private String productName;
    private Integer price;

    public ProductDTO(String productName, Integer price) {
        this.productName = productName;
        this.price = price;
    }
}
