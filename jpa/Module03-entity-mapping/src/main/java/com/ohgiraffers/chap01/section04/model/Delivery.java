package com.ohgiraffers.chap01.section04.model;

import jakarta.persistence.*;

@Entity(name = "section04-delivery")
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "status", nullable = false)
    private String status;

    public Delivery() {}

    public Delivery(String address, String status) {
        this.address = address;
        this.status = status;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", order=" + order +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
