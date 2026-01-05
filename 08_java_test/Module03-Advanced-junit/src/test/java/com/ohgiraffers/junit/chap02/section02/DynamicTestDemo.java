package com.ohgiraffers.junit.chap02.section02;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/*
 * @TestFactory (동적 테스트)
 * - 런타임 시점에 동적으로 데이터를 생성하거나 읽어온다.
 * - 개발자가 직접 데이터를 파싱하고 DynamicTest 객체를 생성해야 한다. (복잡함, 명령형)
 * - 정석 용도 : 파일, DB, 외부 API 등 컴파일 시점에 알 수 없는 소스로부터 테스트를 만들어 내는 경우 사용한다.
 *
 * [결론]
 * 1. 데이터가 정적이고 미리 알려진 경우 (90%) : @ParameterizedTest
 * 2. 데이터가 동적이거나 런타임에 생성되는 경우 (10%) : @TestFactory
 */
@DisplayName("동적 테스트 : @TestFactory")
public class DynamicTestDemo {

    private static class InputSet {

        int a, b, expected;

        InputSet(int a, int b, int expected) {
            this.a = a;
            this.b = b;
            this.expected = expected;
        }
    }

    private static class Calculator {
        int add(int a, int b) {
            return a + b;
        }
    }

    @TestFactory
    @DisplayName("덧셈 시나리오에 대한 동적 테스트 생성")
    Stream<DynamicTest> shouldGeneratedAdditionTest_WhenUsingTestFactory() {

        Calculator calculator = new Calculator();

        List<InputSet> inputs = Arrays.asList(
                new InputSet(1, 2, 3),
                new InputSet(10, 20, 30),
                new InputSet(-5, 5, 0)
        );

        return inputs.stream()
                .map(input -> dynamicTest(
                        "덧셈 테스트 " + input.a + " + " + input.b + " = " + input.expected,  // 동적 테스트 이름
                        () -> { // 테스트 내용
                            int actual = calculator.add(input.a, input.b);
                            Assertions.assertEquals(input.expected, actual);
                        }
                ));
    }
}
