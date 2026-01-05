package com.ohgiraffers.junit.chap03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 * 🧩 [TDD 실습 가이드: 'Calculator.divide()' 구현]
 *
 * ✅ 1. 목적
 * - TDD의 핵심 주기(Red-Green-Refactor)를 '실제 코드'로 경험한다.
 * - '실패하는 테스트'가 '개발을 주도(Drive)'하는 과정을 체감한다.
 *
 * ✅ 2. TDD 사이클 원리
 * 1) RED 🚦: '실패하는' 테스트 케이스를 '먼저' 작성한다.
 * - (원리) 이 테스트는 '새로운 기능의 명세(Specification)'이다.
 * - (단계 1: 컴파일 에러) 아직 존재하지 않는 SUT의 메서드(divide)를 호출하여 컴파일부터 실패시킨다.
 * - (단계 2: 실행 실패) SUT에 '최소한의 구현'(e.g., return 0;)을 추가하여,
 * '컴파일은 성공'하지만 '기대값이 달라' 테스트가 '실패(Red)'하도록 만든다.
 *
 * 2) GREEN ✅: '오직' RED 단계의 테스트를 '통과'할 '최소한의 코드'만 SUT에 작성한다.
 * - (원리) '과잉 구현'을 방지한다. 코드가 비효율적이거나 지저분해도 '일단' 통과가 목표이다.
 *
 * 3) REFACTOR 🛠️: '모든' 테스트가 'GREEN'인 상태를 '유지'하면서 코드의 품질을 개선한다.
 * - (원리) TDD는 '안전망'(Green 테스트)이 확보된 상태에서만 '리팩토링'을 허용한다.
 * - 중복 제거, 가독성 향상, 설계 개선(e.g., 매직 넘버 제거) 등을 수행한다.
 *
 * 4) 반복: 새로운 '요구사항'이나 '엣지 케이스'(e.g., 0으로 나누기)가 필요하면,
 * 다시 'RED' 단계로 돌아가 '새로운 실패 테스트'를 추가하며 사이클을 반복한다.
 */
public class TddPracticeTest {

    private Calculator.calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }

    @Test
    @DisplayName("RED 1 : 10 나누기 2는 5를 반환한다. (happy path)")
    void shouldReturnQuotient_WhenUsingTwoNumbers() {
        int a = 10;
        int b = 2;
        int expected = 5;

        int acu = calc.divided(a, b);

        Assertions.assertEquals(expected, actual, () -> "연산이 정상적으로 수행되지 않음.");
    }

    @Test
    @DisplayName("RED 2 : 0으로 나눌 때 'IllegalArgumentException'을 발생시켜야 한댜ㅏ.=")
    void shouldThrowIllegalArgumentException_WhenUsingTwoNumbers() {
        int a = 10;
        int b = 0;
        int expected = 0;

        Assertions.assertEquals(expected, actual, () -> "연산이 정상적으로 종료되지 않았습니다.");
    }
}
