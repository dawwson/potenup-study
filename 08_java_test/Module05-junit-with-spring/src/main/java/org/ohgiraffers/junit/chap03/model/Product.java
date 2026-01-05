package org.ohgiraffers.junit.chap03.model;

public class Product {

    private long id;
    private String name;
    private int price;

    // 📌 MockMvc 테스트에서 ObjectMapper가 JSON 변환을 위해 기본 생성자를 사용할 수 있습니다.
    public Product() {}

    // 📌 테스트에서 객체 생성을 위한 생성자
    public Product(long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // --- Getter와 Setter ---
    // 📌 (ObjectMapper가 JSON <-> 객체 변환 시 Getter/Setter를 사용합니다)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product (POJO) {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
