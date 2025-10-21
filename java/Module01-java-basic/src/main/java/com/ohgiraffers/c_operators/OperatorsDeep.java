package com.ohgiraffers.c_operators;

public class OperatorsDeep {
    public static void main(String[] args) {

        /*
         * 1. 오버플로우(Overflow)와 언더플로우(Underflow)
         * - 데이터 타입이 표현할 수 있는 값의 범위를 초과하거나 미달할 때 발생하는 현상이다.
         *
         * - 오버 플로우 : 최대값을 초과하면 해당 데이터 타입의 최솟값으로 순환한다.
         * - 예 byte 타입(최대 127)에서 127+1 = -128
         *
         * - 언더플로우 : 최솟값보다 작아지면 해당 데이터 타입의 최갯값으로 순환한다.
         * - 예 byte 타입(최소 -128)에서 -128 - 1 = 127
         *
         * 이러한 현상은 예기치 않은 버그의 원인이 될 수 있기 때문에 값의 범위를 고려해야 한다.
         * */

        System.out.println(" 오버플로우와 언더플로우");
        int maxInt = Integer.MAX_VALUE;
        int minInt = Integer.MIN_VALUE;
        System.out.println("int의 최대값 : " + maxInt);
        System.out.println("오버플로우 : " + (maxInt+1));

        System.out.println("int의 최솟값 : " + minInt);
        System.out.println("언더플로우 : " + (minInt - 1));
        System.out.println();


        /*
         * 2. 증감 연산자(++, --)와 실행 순서.
         * - 전위(++x) :연산 전에 값을 1 증가시킨 후, 다른 연산에 사용된다.
         * - 후위(x++) : 다른 연산에 현재 값을 먼저 사용한 후, 값을 1 증가시킨다.
         * - 복잡한 식에서는 실행 순서가 결과를 좌우 하므로 명확하게 이해하는 것이 중요하다.
         * */
        System.out.println("증감 연산 실습");
        int a = 5;
        int b = 5;

        // 후위 연산자 : a의 현재 값(5)이 result1 에 할당된 후 a가 6으로 증가
        int result = a++ + 2;
        System.out.println("a++ + 2 : " + result);

        // 전위 연산자 : b가 먼저 6으로 증가된 후 result2에 할당
        int reulst2 = ++b + 2;
        System.out.println("++b + 2 : " + reulst2);

    }
}
