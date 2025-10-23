package com.ohgiraffers.chap01.section04.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "section04-order")
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    // 1명의 회원이 n의 주문을 할 수 있다.
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Delivery delivery;

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    public Order() {}

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public Customer getCustomer() {
        return customer;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                "주문 번호: " + this.id +
                "주문 시간: " + this.orderDate +
                '}';
    }
}
