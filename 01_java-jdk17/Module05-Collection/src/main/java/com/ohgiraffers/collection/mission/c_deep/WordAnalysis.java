package com.ohgiraffers.collection.mission.c_deep;

import java.util.HashMap;
import java.util.Map;

public class WordAnalysis {
    public static void main(String[] args) {
        // 피
        String str = "The quick, brown fox jumps over the lazy dog. The dog was not amused, and the fox ran away quickly!";
        Map<String, Integer> wordMap = new HashMap<>();

        // 소문자 변환 -> 문장 부호 삭제 -> 공백 기준 단어 분리
        String[] words = str.toLowerCase().replaceAll("[^a-zA-Z\\s]", "").split(" ");

        // 단어별 빈도 계산
        for (String word : words) {
            try {
                // 빈도 +1
                wordMap.compute(word, (k, count) -> count + 1);
            } catch (NullPointerException e) {
                wordMap.put(word, 1);
            }
        }

        // 출력
        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

}
