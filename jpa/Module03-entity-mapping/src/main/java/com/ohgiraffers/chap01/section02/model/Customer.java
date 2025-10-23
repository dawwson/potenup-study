package com.ohgiraffers.chap01.section02.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "section02-customer")
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(name = "name")
    private String name;

    /**
     * [양방향 연관관계 매핑]
     * 고객은 여러 주문을 가질 수 있다.(1:N)
     * - mappedBy : 연관관계의 주인이 되는 엔티티에서 정의한 필드의 이름을 명시한다.
     *              즉, order의 customer(FK)를 지정함
     * 연관관계의 주인은 외래키(FK)를 가지고 있으며, 해당 관계의 상태를 직접적으로 관리하는 것을 의미한다.
     *
     * [Cascade 옵션]
     * - cascade = CascadeType.ALL :
     *   고객 저장 시 관련 모든 주문도 함께 저장된다.
     *   예를 들어, 고객을 저장하면 그 고객의 모든 주문도 자동으로 저장된다.
     *   다른 옵션 :
     *   - PERSIST : 고객 저장 시 주문도 저장
     *   - REMOVE : 고객 삭제 시 주문도 삭제
     *   - MERGE : 고객 병합 시 주문도 병합
     *   - REFRESH : 고객 새로고침 시 주문도 새로고침
     *   - NONE : 아무런 전파가 없는 것을 의미함
     *
     * OrphanRemoval = true;
     * 고객에서 특정 주문을 제거하는 경우 해당 주문이 DB에서도 삭제된다.
     * 즉, 고객과 연관이 끊어진 주문은 데이터베이스에서 자동으로 삭제된다.
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    protected Customer() {}

    public Customer(String name) {
        this.name = name;
    }

    public void addOrder(Order order) {
        orders.add(order);
        // 지금 양방향 참조 상태이지만 order는 customer를 모르기 때문에 this를 설정해준다.
        order.setCustomer(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
