package com.ohgiraffers.c_operators;

public class Application {
    public static void main(String[] args) {
        /*
         * 연산자(Operators)
         *
         * 산술 연산자
         * 수학적 계산을 수행하는 연산자이다.
         * +(덧셈), -(뺄셈), *(곱셈), /(나눗셈), %(나머지)
         *
         * 비교 연산자
         * 두 값을 비교하여 참(true), 또는 거짓(false)를 반환하는 연산자이다.
         * == (같음), != (다름), <(작음), >(큼), <= (작거나 같음), >=(크거나 같음)
         *
         * 논리 연산자
         * 하나 이상의 "조건을 결합"하여 최종적인 참 또는 거짓을 평가하는 연산자이다.
         * 예시 && (논리 : AND) || (논리 OR), !(논리 Not)
         *
         * 대입 연산자
         * 변수에 값을 할당하는 연산자.
         * = (대입), +=(덧셈 후 대입), -=(뺄셈 후 대입), *=(곱셈 후 대입), /=(나눗셈 후 대입)
         *
         * 증감 연산자
         * 변수의 값을 1씩 증가시키거나 감소시키는 연산자이다.
         * ++(증가)
         * -- (감소)
         * */

        int a = 10;
        int b = 3;

        System.out.println("덧셈 : " + (a + b));
        System.out.println("나눗셈 : " + (a / b));
        System.out.println("나머지 : " + (a % b));

        boolean isGreater = a > b;
        System.out.println("a가 b보다 큰가? " + isGreater);

        /*
         * 논리 연산자
         * */

        boolean isTure = true;
        boolean isFalse = false;

        System.out.println("둘 다 참인가? : " + (isTure && isFalse)); // 좌항 && 우항
        System.out.println("둘 중 하나는 거짓인가? : " + (isTure || isFalse));
        System.out.println("Not은 너가 거짓이라는 것을 부정하겠어. " + !isFalse);


        // 증감 연산자
        int g = 1;
        System.out.println(++g);
        System.out.println(--g);
    }
}
