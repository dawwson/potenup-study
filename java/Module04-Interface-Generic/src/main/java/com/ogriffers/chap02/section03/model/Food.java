package com.ogriffers.chap02.section03.model;

public class Food {
    public String name;

    public Food(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}