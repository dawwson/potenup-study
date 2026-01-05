package com.ogriffers.chap02.section01;

import com.ogriffers.chap02.section02.GenericBox;

public class Application01 {
    public static void main(String[] args) {
        ObjectBox stringBox = new ObjectBox();
        stringBox.setItem("hello");

        String box = (String) stringBox.getItem();

        GenericBox<Integer> genericBox = new GenericBox<>();
        integerBox.setItem(1234);
        int result2 = integerBox.getItem();
    }
}
