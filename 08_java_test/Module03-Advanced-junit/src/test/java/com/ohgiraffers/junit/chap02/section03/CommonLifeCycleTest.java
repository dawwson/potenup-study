package com.ohgiraffers.junit.chap02.section03;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
interface CommonLifeCycleTest {

    @BeforeAll
    default void setupAllTests() {
        System.out.println("공통 @BeforeAll : 모든 테스트 시작(인터페이스 정의)");
    }

    @BeforeEach
    default void setupEachTest(TestInfo testInfo) {
        System.out.println("공통 @BeforeEach : " + testInfo.getDisplayName() + "시작 인터페이스 정의");
    }

    @AfterEach
    default void tearDownEachTest(TestInfo testInfo) {
        System.out.println("공통 @AfterEach : " + testInfo.getDisplayName() + "종료 인터페이스 정의");
    }

    @AfterAll
    default void tearDownAllTests() {
        System.out.println("공통 @AfterAll : 모든 테스트 종료 인터페이스 정의");
    }
}

@DisplayName("구현 클래스 A")
class ConcreteTestClassA implements CommonLifeCycleTest {

    @Test
    @DisplayName("A 클래스 고유 테스트 1")
    void testA1() {
        System.out.println("testA 실행");
    }
}

@DisplayName("구현 클래스 B")
class ConcreteTestClassB implements CommonLifeCycleTest {

    @Test
    @DisplayName("B 클래스 고유 테스트 1")
    void testB1() {
        System.out.println("testB 실행");
    }
}