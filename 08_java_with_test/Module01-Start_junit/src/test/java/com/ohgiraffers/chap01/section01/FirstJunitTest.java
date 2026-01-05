package com.ohgiraffers.chap01.section01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
 * Junit 테스트 메서드 네이밍 패턴
 * 1. 목적
 *   - 테스트 이름만 보고도 "무엇을 테스트하는지", "어떤 상황에서", "어떤 결과를 기대하는지" 이해할 수 있게 한다.
 *   - 즉, 테스트는 "문서화된 예시"로 코드의 동작 의도를 설명해야 한다.
 *
 * 2. 일반적인 네이밍 패턴
 *   1) should[ExceptedBehavior] where[Condition]
 *     - ex) shouldReturnSum_WhenTwoNumbersAreAdded()
 *     - 의미 : 두 수를 더할 때 합계를 반환해야 한다.
 *
 * 3. 작성 원칙
 *   - "test" 접두사는 필수가 아니다. 대신 "행동 기반 문장"으로 명확하게 표현한다.
 *   - 한 메서드는 한 가지 시나리오만 검증한다.
 *   - 이름은 길어져도 괜찮다. "가독성 > 길이"
 *   - 가능하면 영어를 사용하여 국제화된 코드 스타일을 유지한다.
 *
 * 4. 예시 비교
 *   - 나쁜 예 : testAddition() - 테스트 대상만 드러남
 *   - 좋은 예 : shouldReturnSum_WhenTwoNumbersAreAdded() - 동작까지 포함
 */
public class FirstJunitTest {

    static class Calculator {

        public int add(int a, int b) {
            return a + b;
        }

        public boolean isPositive(int n) {
            return n > 0;
        }
    }

    /*
     * @Test 어노테이션
     * - 역할 : Junit 프레임워크에 해당 메서드가 테스트 메서드임을 알린다.
     * - 실행 : Junit 실행기는 이 어노테이션이 붙은 메서드를 찾아 자동으로 실행한다.
     * - 요구사항
     *   - 접근 제한자 : "public" 또는 "default"
     *   - 반환 타입 : "void"
     */
    @Test
    void shouldReturnSum_WhenTwoNumbersAreAdded() {

        // Arrange (준비)
        Calculator calculator = new Calculator();
        int num1 = 5;
        int num2 = 3;
        int exceptedResult = 8;

        // Act (실행)
        int actualResult = calculator.add(num1, num2);

        // Assert (검증)
        /*
         * Assertions.AssertEquals()
         * - 기능 : 두 값이 동일한지 검증한다.
         * - 문법 : assertEquals(Object expected, Object actual, [String message])
         * - 실패 : "expected"와 "actual"이 다르면 테스트 실패 처리
         * - message(선택) : 실패 시 출력할 메시지
         */
        Assertions.assertEquals(exceptedResult, actualResult, () -> "값이 일치하지 않습니다.");
    }

    /*
     * Assertions.AssertTrue()/AssertFalse()
     * 기능 : 주어진 조건이 참 또는 거짓이 되는지 검증한다.
     * - 문법 : Assertions.assertTrue(기대값, message);  // 기대값은 boolean 타입
     * - 문법 : Asserstions.assertFalse(기대값, message);
     * - 실패 : 조건이 기대와 다르면 실패 처리된다.
     */

    @Test
    void shouldReturnTrue_WhenNumberIsPositive() {
        Calculator calculator = new Calculator();
        Assertions.assertTrue(calculator.isPositive(5));
    }

    @Test
    void shouldReturnFalse_WhenNumberIsNegative() {
        Calculator calculator = new Calculator();
        Assertions.assertFalse(calculator.isPositive(-5));
    }

    @Test
    void shouldReturnFalse_WhenNumberIsZero() {
        Calculator calculator = new Calculator();
        Assertions.assertFalse(calculator.isPositive(0));
    }
}
