package com.ohgiraffers.chap01.section01.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "order_id")
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    /**
     * ManyToOne : 다대일 단방향 매핑 (주문 -> 고객)
     * 주문은 반드시 고객 한 명에게 속한다.
     * 연관관계의 주인이므로 @joinColumn으로 외래키를 명시함
     *
     * 지연 로딩(Lazy) : 실제 사용할 때 조회하도록 설정해 성능 최적화
     * (초깅는 프록시 객체로 대기하다가, getCustomer() 호출 시 DB에서 조회)
     * 즉시 로딩(EAGER) : 연관된 엔티티를 즉시 조회하도록 설정
     * (주문을 조회할 때 관련된 고객 정보도 함께 조회됨)
     */
    // LAZY : SELECT + JOIN
    // EAGER : SELECT + SELECT
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    public Order() {}

    public Order(Customer customer) {
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                '}';
    }
}
