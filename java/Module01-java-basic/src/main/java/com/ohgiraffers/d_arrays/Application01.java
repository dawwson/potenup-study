package com.ohgiraffers.d_arrays;

public class Application01 {
    public static void main(String[] args) {

        /*
         * 배열
         * 배열은 동일한 데이터 타입의 요소를 연속적으로 저장하는 자료구조이;다.
         * 선언 : 타입[] 변수명 = new 타입[n] (n은 크기를 의미하며 숫자가 들어감)
         * 인덱스(0부터 시작)을 사용해 요소에 접근한다.
         * 배열은 고정된 크기를 가지며, 생성 후 크기 변경이 불가능하다.
         * 배열의 길이는 "langth" 속성을 이용해 크기를 확인할 수 있다.
         *
         * 예시 :
         * int[] numbers = {1,2,3,4,5}
         * */

        // 배열 선언
        int[] numbers;
        // 초기화
        numbers = new int[5];

        numbers[0] = 1;
        numbers[1] = 2;
        numbers[2] = 3;
        numbers[3] = 4;
        numbers[4] = 5;
        System.out.println("배열의 첫 번째 값 :  " + numbers[0]);
        System.out.println("배열의 길이 : " + numbers.length);


        String[] names = {"Alice", "bob", "charlie"};
        System.out.println("0번 앞으로 나와 " + names[0]);

    }
}
