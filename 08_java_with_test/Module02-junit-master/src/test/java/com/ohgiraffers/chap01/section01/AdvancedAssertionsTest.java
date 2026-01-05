package com.ohgiraffers.chap01.section01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@DisplayName("junit 고급")
public class AdvancedAssertionsTest {

    private static class Calculator {

        // 1. '예외 경계'를 가진 메서드
        int divide(int a, int b) {
            if (b == 0) {
                // SUT는 'b가 0'이라는 '경계'에서 'IllegalArgumentException'을 발생시키는 '계약'을 가진다.
                throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
            }
            return a / b;
        }

        // 2. '성능 계약'을 가진 메서드
        void slowCalculation() {
            // 이 메서드는 '약 10ms'의 시간이 소요되는 '계약'을 시뮬레이션한다.
            try {
                Thread.sleep(10); // 10ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // --- assertAll 테스트를 위해 추가된 메서드 ---

        // 3. '기본 기능 계약' (덧셈)
        int add(int a, int b) {
            return a + b;
        }

        // 4. '기본 기능 계약' (양수 판별)
        boolean isPositive(int a) {
            return a > 0;
        }
    }

    /*
     * assertThrows : 예외 검증
     * TDD는 성공 케이스(happy path)뿐만 아니라,
     * 실패 케이스를 먼저 정의하는 것이 핵심이다.
     * assertThrows는 sut가 정의된 실패 계약을 정확히 지키는지 검증한다.
     *
     * [문법] assertThrows(기대하는 예외 클래스, 실행할 코드 람다)
     *
     * 왜 람다를 써야 하는가?
     * - 목적 : 실행할 코드 자체를 assertThrows 메서드에 인자로 전달하기 위해서이다.
     * - 원리 : () -> calc.divide(10, 0)은 실행이 아니라 실행할 코드 블록 자체를 의미한다.
     *   이 코드 블록 내부에서 try-catch로 감싸 대신 실행해준다.
     * - 만약 calc.divide(10, 0)을 람다 없이 직접 넣으면
     *   assertThrows가 실행되기도 전에 예외가 발생하며, 테스트 자체가 오류로 중단된다.
     */
    @Test
    @DisplayName("0으로 나눌 때, IllegalArgumentException이 발생해야 한다.")
    void shouldThrowIllegalArgumentException_whenDividedByZero() {
        Calculator calculator = new Calculator();

        // 오류
        //Assertions.assertThrows(IllegalArgumentException.class, calculator.divide(0, 0));

        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.divide(0, 0));
    }

    /*
     * AssertTimeout : 비기능적 계약
     * 원리 : TDD는 요구사항 뿐만 아니라,
     * 성능 (예: 응답은 100ms 이내여야 한다.)와 같은 비기능적 요구사항도 검증할 수 있다.
     *
     * - assertTimeout : 람다 코드가 끝날 때까지 기다린 후, 총 시간을 검증한다.
     * - assertTimeoutPreemptively : (즉시 중단) 100ms를 넘는 즉시 람다 실행을 강제 중단시키고 실패 판정
     */
    @Test
    @DisplayName("시간이 소요되는 작업(10ms)이 100ms 이내에 완료되어야 함")
    void shouldCompleteWithinTimeout_WhenSlowCalculationIsFastEnough() {
        Calculator calculator = new Calculator();

        Assertions.assertTimeout(Duration.ofMillis(100), () -> calculator.divide(10, 10));
    }

    /*
     * AssertAll : 검증의 원자성 문제 해결
     * 원리
     * - 문제점 : "assertEquals" 3개를 연달아 사용하면 1번의 실패가 발생하는 순간,
     *   모든 테스트가 즉시 중단되어 2,3 번은 실행조차 되지 않는다.
     *
     * 해결책
     * - assertAll은 그룹 내의 모든 람다를 끝까지 실행한다.
     * - 중간에 실패가 있어도 중단하지 않고, 실패한 모든 목록을 한 번에 리포트 해준다.
     */
    @Test
    @DisplayName("Calculator의 여러 기능(덧셈/양수 판별) 계약을 한 번에 검증한다.")
    void shouldAssertAllPropertiesOfCalculator() {
        Calculator calculator = new Calculator();

        Assertions.assertAll(
                "계산기 기능 그룹 검증",
                () -> Assertions.assertEquals(5, calculator.add(1, 3), "덧셈 (2+3) 검증"),
                () -> Assertions.assertTrue(calculator.isPositive(1), "양수 (1) 검증"),
                () -> Assertions.assertFalse(calculator.isPositive(0), "양수 검증 false"),
                () -> Assertions.assertFalse(calculator.isPositive(-1), "양수 검증 (-1) 검증 false")
        );
    }
}
