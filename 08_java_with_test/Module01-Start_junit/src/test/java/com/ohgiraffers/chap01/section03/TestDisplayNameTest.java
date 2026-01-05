package com.ohgiraffers.chap01.section03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("계산기 기능 명세 테스트")
public class TestDisplayNameTest {

    private static class Calculator {
        int add(int a, int b) {
            return a + b;
        }
    }

    @Test
    @DisplayName("두 양수를 더하면 합계를 정확히 반환해야 한다.")
    void add_ShouldReturnCorrectSum_WhenTwoPositiveNumbers() {
        Calculator calculator = new Calculator();

        int expected = 5;
        int zero = 0;

        int actual = calculator.add(2, 3);

        Assertions.assertEquals(expected, actual, () -> "2+3의 결과는 " + expected + "여야 합니다.");

        // 메시지 안에서 발생한 에러가 테스트에 영향을 주지 않도록 람다형식으로 작성
        Assertions.assertEquals(expected, actual, () -> (10 / zero) + "은 불가능해요!");
    }
}
