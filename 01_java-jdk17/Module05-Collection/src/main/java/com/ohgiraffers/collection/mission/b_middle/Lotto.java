package com.ohgiraffers.collection.mission.b_middle;

import java.util.*;

public class Lotto {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();

        while (set.size() != 6) {
             int number = (int) (Math.random() * 45) + 1;
             set.add(number);
        }

        // Set -> List
        List<Integer> list = new ArrayList<>(set);

        // 정렬
        Collections.sort(list);

        // 출력
        list.forEach(System.out::println);
    }
}
