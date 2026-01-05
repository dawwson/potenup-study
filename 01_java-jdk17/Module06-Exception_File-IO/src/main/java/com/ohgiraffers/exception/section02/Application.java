package com.ohgiraffers.exception.section02;

/**
 * 예외 처리
 * - 예외(Exception)은 프로그램 실행 중 예상치 못한 문제로 인해 정상적인 흐름이 중단되는 상황을 의미한다.
 * - 예를 들어, 사용자가 0으로 나누기를 시도하거나, 파일을 열 때 파일이 존재하지 않는 경우 등이 있다.
 * - java에서는 try-catch 블록을 통해 이러한 예외를 처리하며, finally 블록으로 필수 정리 작업을 보장한다.
 * - 사용자 정의 예외는 특정 상황에서 개발자가 직접 예외를 만들어 사용하는 것으로 코드의 의도를 명확하게 전달한다.
 *
 * 예외 처리가 필요한 이유
 * - 예외가 발생하면 프로그램이 비정상적으로 종료된다.
 * - 안정적인 프로그램 실행을 위해 반드시 처리해야 한다.
 *
 * try-catch 문법
 * - try : 예외가 발생할 가능성이 있는 코드 블록
 * - catch : 특정 예외를 처리하는 코드 블록
 * - finally : 예외 발생 여부와 상관없이 항상 실행되는 블록
 */
public class Application {
    public static void main(String[] args) {
        try {
            // 여기에서 발생된 에러가
            //int result = 10/0;
            //System.out.println("결과 : " + result);
        } catch (ArithmeticException e) {
            // catch 조건문 매개변수로 들어온다.
            System.out.println("0으로 나눌 수 없습니다.");
        } finally {
            System.out.println("예외와 상관없이 실행됩니다.");
        }

        try {
            checkAge(-1);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println("예외 처리하기");
        }
    }

    /**
     * 나이를 입력받고 0보다 큰지 확인한다.
     * @param age 나이를 입력 받는다.
     * @throws IllegalArgumentException 나이가 0보다 적은 경우
     */
    public static void checkAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("나이는 0보다 작을 수 없습니다.");
        }
        System.out.println("정상입니다.");
    }
}
