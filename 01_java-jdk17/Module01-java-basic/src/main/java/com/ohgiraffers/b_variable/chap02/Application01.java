package com.ohgiraffers.b_variable.chap02;

public class Application01 {
    public static void main(String[] args) {
        /*
         * 형변환
         * 형변환(type Conversion)은 프로그래밍에서 데이터 타입을 다른 데이터 타입으로 변환하는 과정을 의미한다.
         * 형변환은 두 가지 주요 방식으로 나눌 수 있는데 암시적 형변환과 명시적 형변환이다.
         * */

        /*
         * 암시적 형변환(Implicait Type conversion)
         * 작은 범위의 데이터 타입에서 큰 범위의 데이터 타입으로 자동으로 변환되는 과정을 의미한다.
         * (int -> long) 이 경우, 데이터 손실이 발생하지 않는다.
         * */
        int intVal = 100; // 4byte 400ml
        long longVal = intVal; // 8byte 800ml

        double doubleVal = intVal;
        System.out.println("intVal = " + intVal);
        System.out.println("longVal = " + longVal);
        System.out.println("doubleVal = " + doubleVal);

        /*
         *  명시적 형변환 (Explicit Type Conversion)
         *  정의: 큰 범위의 데이터 타입에서 작은 범위의 데이터 타입으로 변환할 때, 프로그래머가 직접 변환을 지정해야 하는 과정이다.
         *  (double -> int) 이 경우, 데이터 손실이 발생할 수 있다.
         * */
        double dVal = 99.99;
        int iVal = (int)dVal;
        System.out.println("iVal = " + iVal);

        // 출력
        System.out.println("암시적 형변환 결과(doubleVal): " + doubleVal);
        System.out.println("명시적 형변환 결과(iVal): " + iVal);

        /*
         * 주의: 명시적 형변환은 데이터 손실 가능성이 있다.
         * 데이터 타입 선택 시, 값의 범위와 필요한 정확도를 고려해야 한다.
         *
         * 참고 : 과거에는 컴퓨터의 용량이 작기 때문에 바이트 수가 중요하게 생각되었다.
         * 그러나 요즘 컴퓨터의 용량은 매우 많기 때문에 바이트 수를 과거보다는 덜 중요하게 생각한다.
         *
         * 그렇다면 무조건 큰 데이터 타입으로 작성하면 되는 것 아닐까?
         * 프로그램에서는 데이터를 처리하는 기본 단위가 있으며, 이 기본 단위를 기반으로 연산 구조가 설계되어 있다.
         * 무조건 큰 데이터 타입을 사용하는 것이 항상 좋은 선택은 아닙니다. 큰 데이터 타입은 더 많은 메모리를 차지하고,
         * CPU의 캐시를 비효율적으로 사용할 수 있어 성능 저하를 초래할 수 있다
         *
         * 자료형의 기본 단위
         * 정수형 -> int
         * 실수형 -> double
         */

    }
}
