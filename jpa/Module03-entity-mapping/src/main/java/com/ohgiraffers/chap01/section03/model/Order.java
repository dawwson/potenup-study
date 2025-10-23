package com.ohgiraffers.chap01.section03.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "section03-order")
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "customer_id")
    private int customer;
    // 필요에 따라 연관관계를 맺지 않을 수도 있음 (DDD 바운디드 컨텍스트에 따라 다름)
    // N+1 문제가 없어질지라도 주문자가 없는 주문이 생길 수도 있는 문제가 생김
    // 그래서 DDL none으로 설정?

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Delivery delivery;

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    public Order() {
    }

    public Order(int customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this); // 연관관계 주인인 delivery는 order를 모르기 때문에 설정해준다.
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", delivery=" + delivery +
                ", orderDate=" + orderDate +
                '}';
    }
}
