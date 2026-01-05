package com.ohgiraffers.junit.chap01.section01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

/*
 * 파라미터화된 테스트가 왜 필요한가? (TDD와 Dry 원칙)
 * - Dry 원칙 (Don't Repeat Yourself) : 중복은 악이다.
 * - TDD 사이클에서 중복된 테스트 코드는 유지보수성을 심각하게 저해한다.
 *
 * TDD는 다양한 경계값과 엣지 케이스를 검증해야 한다.
 * (예: 홀수 검증 시 - 1, 3, -1, 99)
 *
 * 나쁜 예
 * - void testOdd1 // 1의 값에 대한 테스트
 * - void testOdd3 // 3의 값에 대한 테스트
 *
 * 문제점
 * 1. 검증 로직이 완전히 중복된다. (Dry 원칙 위배)
 * 2. 데이터(1, 3, -1)와 로직이 분리되지 않아 테스트 의도가 한 눈에 보이지 않는다.
 *
 * 해결책 : @ParameterizedTest
 * - 동일한 테스트 로직(스펙)을 여러 다른 데이터로 반복 실행하게 만든다.
 * - 테스트 로직과 데이터를 명확하게 분리하여 가독성과 유지보수성을 극대화한다.
 */
public class ParameterizedValueSourceTest {
    private static class Validator {
        /**
         * 숫자가 홀수인지 검증한다.
         */
        boolean isOdd(int number) {
            return number % 2 != 0;
        }

        /**
         * [정석 원리] TDD의 핵심은 'null'과 '빈 값'을 방어하는 '방어 코드(Defensive Code)'를
         * 테스트로 '먼저' 명세하는 것이다.
         *
         * Java 11의 `String.isBlank()`는 `""`, `" "`는 true로 처리하지만,
         * 'null'을 받으면 'NullPointerException(NPE)'을 던진다.
         *
         * 따라서 '정석적인' SUT 로직은 `input == null` 체크를 '먼저' 수행하여
         * NPE를 방어(short-circuit)해야 한다. 이 테스트는 이 '계약'을 검증한다.
         */
        boolean isNullOrBlank(String input) {
            return (input == null || input.isBlank());
        }
    }

    /*
     * @ParameterizedTest + @ValueSource
     * - @Test 대신 @ParameterizedTest를 선언한다.
     * - 데이터 제공자(data source) 어노테이션을 반드시 함께 사용한다.
     */
    @ParameterizedTest
    @ValueSource(ints = { 1, 3, 5, -7, -13 })
    @DisplayName("홀수 숫자에 대해 True를 반환해야 함.")
    void shouldReturnTrue_WhenNumberIsOdd(int number) {
        Validator validator = new Validator();
        boolean isOdd = validator.isOdd(number);

        Assertions.assertTrue(isOdd, number + "는 홀수여야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = { "java", "junit", "TDD", "null", "", " " })
    @DisplayName("일반 문자열은 isNullOrBlank가 false여야 한다.")
    void shouldReturnFalse_When_StringIsNormal(String text) {
        Validator validator = new Validator();

        boolean result = validator.isNullOrBlank(text);

        Assertions.assertFalse(result, text + "는 blank가 아닙니다.");
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = { " ", "  ", "\t", "\n" })
    @DisplayName("null이거나, 비어있거나, 공백 문자열일 때 true를 변환해야 한다.")
    void shouldReturnTrue_WhenStringIsBlankOrNull(String text) {
        Validator validator = new Validator();

        boolean isBlankOrNull = validator.isNullOrBlank(text);

        Assertions.assertTrue(isBlankOrNull, "입력값 [" + text + "]은 null이거나 blank여야 합니다.");
    }
}
