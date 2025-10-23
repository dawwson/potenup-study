package com.ohgiraffers.chap01.section03.model;

import jakarta.persistence.*;

@Entity(name = "section03-Delivery")
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToOne
    @JoinColumn(name = "order_id") // 연관관계 주인은 Delivery (FK를 가지고 있는 쪽)
    private Order order;

    @Column(name = "status", nullable = false)
    private String status;

    public Delivery() {
    }

    public Delivery(String address, String status) {
        this.address = address;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", order=" + order.getId() +  // NOTE: 필요한 것만 호출해야 된다. 안 그러면 스택오버플로우 발생
                ", status='" + status + '\'' +
                '}';
    }
}
