package com.ogriffers.chap02.section03.model;

public class FoodBox<T>{
    private T item;

    public void put(T item) {
        this.item = item;
    }

    public T get() {
        return item;
    }
}