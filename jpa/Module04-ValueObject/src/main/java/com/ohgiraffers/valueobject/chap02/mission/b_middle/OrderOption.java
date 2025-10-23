package com.ohgiraffers.valueobject.chap02.mission.b_middle;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderOption {
    private String optionName;
    private int price;

    public String getOptionName() {
        return optionName;
    }

    public int getPrice() {
        return price;
    }

    protected OrderOption() {}

    public OrderOption(String optionName, int price) {
        this.optionName = optionName;
        this.price = price;
    }
}