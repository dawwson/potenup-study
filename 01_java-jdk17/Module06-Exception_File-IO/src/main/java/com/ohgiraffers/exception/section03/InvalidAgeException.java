package com.ohgiraffers.exception.section03;

/**
 * 사용자 정의 예외란
 * - 자바에서 제공하는 기본 예외 클래스 (Exception, runtimeException 등)을 상속받아,
 *   직접 예외를 정의할 수 있음
 *
 * 왜 사용자 정의 예외를 만들까?
 * - 특정 비즈니스 로직에 맞는 예외 처리를 할 수 있음
 * - 예외를 더 의미 있게 만들고, 유지보수를 쉽게 하기 위함
 */
public class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}
