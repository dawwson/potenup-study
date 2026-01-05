package com.ohgiraffers.junit.chap02.section01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

/*
 * 결정성 vs 비결정성 : TDD 정서고가 예외
 * 1. 정석 TDD 유닛 테스트는 결정론적이어야 한다.
 * - 동일한 입력에는 항상 동일한 결과가 나와야 한다.
 *
 * 2. @Parameterized vs @RepeatedTest
 * - @ParameterizedTest는 결정론적 원칙을 지킨다.
 * - 명시적으로는 다른 데이터를 주입하여 동일한 로직을 검증한다.
 *
 * - @RepeatedTest : 비결정론적 상황을 위한 특수 도구
 * - 동일한 테스트 로직을 N번 반복하며 안정성을 검증한다.
 * - 파라미터가 주입되지 않는다.
 *
 * @RepeatedTest의 정석적인 사용 시점
 * - TDD의 결정성 원칙을 깨뜨리므로 반드시 목적이 명확해야 한다.
 * - 용도 1 : flaky Test (간헐적 실패) 검증
 * - 10번 중 1번 실패하는 테스트 (예: Race Condition, 비동기, 네트워크) 깨진(Red) 테스트
 * - RepeatedTest(100)는 간헐적 실패를 확정적으로 제안하여 비결정적인 버그를 수정하는 근거로 사용된다.
 *
 * - 용도 2 : 무작위 입력값의 검증
 * - sut(테스트 대상)에 무작위 입력을 반복적으로 주입하여
 * - 개발자가 예상하지 못한 엣지 케이스를 찾을 때 사용한다.
 */
public class RepeatedStudyTest {
    @RepeatedTest(5)
    @DisplayName("기본 반복 테스트")
    void shouldRepeatTest_WhenUsingDefaultName() {
        System.out.println("실행됨");
        Assertions.assertTrue(true);
    }

    @RepeatedTest(value = 3, name = "{displayName} - {currentRepeatition} / {totalRepeatition}회차")
    @DisplayName("커스텀 이름 반복 테스트")
    void shouldRepeatTest_WhenUsingCustomName() {
        System.out.println("실행됨");
        Assertions.assertTrue(true);
    }
}
