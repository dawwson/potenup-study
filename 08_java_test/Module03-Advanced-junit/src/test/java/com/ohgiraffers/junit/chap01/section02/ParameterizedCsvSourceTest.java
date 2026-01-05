package com.ohgiraffers.junit.chap01.section02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("파라미터화된 테스트 : @CsvSource(데이터 주입)")
public class ParameterizedCsvSourceTest {
    private static class Calculator {
        int add(int a, int b) {
            return a + b;
        }

        /**
         * [TDD 정석] SUT는 '경계값(Boundary)' 계약을 가진다.
         * '0으로 나누기'는 'IllegalArgumentException'을 발생시키는 '계약'을 가진다.
         */
        int divide(int a, int b) {
            if (b == 0) {
                throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
            }
            return a / b;
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "10, 20, 30",
            "-5, 8, 3",
            "0, 0, 0"
    })
    @DisplayName("다양한 덧셈 케이스(입력값 2개, 기대값 1개를 CSV로 검증")
    void shouldReturnCorrectSum_WhenTwoNumbersAreAdded(int a, int b, int expected) {

        Calculator calculator = new Calculator();

        int actual = calculator.add(a, b);

        assertEquals(expected, actual, () -> a + " + " + b + "는 " + expected + "여야 합니다.");
    }

    @ParameterizedTest
    @CsvSource(
        value = {
                "'', true",
                "' ', true",
                "junit, false",
                "NULL, true"
        },
            nullValues = { "NULL" } //
    )
    @DisplayName("null 또는 공백 문자열 엣지 케이스를 CSV로 검증")
    void shouldReturnTrue_WhenStringIsBlankOrNull(String input, boolean expected) {
        boolean actual = (Objects.isNull(input) || input.isBlank());
        System.out.println(actual);
        assertEquals(expected, actual, "입력값 " + input + " 의 검증 결과가 다릅니다.");
    }

    @ParameterizedTest
    @CsvSource(value = {
            "10 : 5 : 2",    // (스펙 1) a=10, b=5 이면, expected=2
            "100 : 10 : 10", // (스펙 2) a=100, b=10 이면, expected=10
            "-8 : 4 : -2"    // (스펙 3) 음수 나눗셈 스펙
    }, delimiter = ':') // 📌 구분자를 쉼표(,) 대신 콜론(:)으로 지정
    @DisplayName("사용자 정의 구분자(delimiter)를 사용하여 '나눗셈' 검증")
    void shouldReturnCorrectDivision_WhenUsingCustomDelimiter(int a, int b, int expectedDivision) {
        // Arrange
        Calculator calc = new Calculator();

        // Act
        int actual = calc.divide(a, b);

        // Assert
        assertEquals(expectedDivision, actual, () -> a + " / " + b + "는 " + expectedDivision + "여야 합니다.");
    }

    /*
     * @CsvSource로 '예외 경계' 검증하기
     * - CsvSource는 '기대값'뿐만 아니라 '입력값'만 제공하는 데도 사용할 수 있다.
     * - SUT의 'divide' 메서드가 '0으로 나누기' 계약을 잘 지키는지 검증한다.
     */
    @ParameterizedTest
    @CsvSource({
            "10, 0",    // (스펙 1) 10 / 0
            "0, 0",     // (스펙 2) 0 / 0
            "-5, 0"     // (스펙 3) -5 / 0
    })
    @DisplayName("'0으로 나누기' 예외 경계를 CSV로 검증 (assertThrows)")
    void shouldThrowException_WhenDividingByZero(int a, int b) { // (입력값만 받음)
        // Arrange
        Calculator calc = new Calculator();

        // Act & Assert
        // 이 '모든' 케이스(3번 실행)에서 'IllegalArgumentException'이 발생해야만 테스트가 성공(Green)한다.
        assertThrows(IllegalArgumentException.class, () -> {
            calc.divide(a, b);
        });
    }
}
