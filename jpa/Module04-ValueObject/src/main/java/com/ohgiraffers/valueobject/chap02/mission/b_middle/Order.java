package com.ohgiraffers.valueobject.chap02.mission.b_middle;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "mission_orders")
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "name")
    private String orderName;

    @ElementCollection
    @Column(name = "order_options")
    private List<OrderOption> options = new ArrayList<>();

    protected Order() {}

    public Order(String orderName) {
        this.orderName = orderName;
    }

    public void setOptions(List<OrderOption> options) {
        this.options = options;
    }

    public void addOption(OrderOption option) {
        this.options.add(option);
    }

    public int getTotalOptionPrice() {
        return this.options.stream().map(OrderOption::getPrice).reduce(0, Integer::sum);
    }
}
