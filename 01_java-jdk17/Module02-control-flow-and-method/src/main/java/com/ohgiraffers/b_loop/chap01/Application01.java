package com.ohgiraffers.b_loop.chap01;

/*
 * for 문
 * for 문은 초기값, 조건식, 증감식을 사용해 반복 횟수를 제어하는 반복문이다.
 *
 * 형식 : for(초기식; 조건식; 증감식){ 실행 코드}
 * - 초기식 : 반복 변수 선언 및 초기화.
 * - 조건식 : true일 때 반복, false면 종료.
 * - 증감식 : 반복 변수 값 변경.
 * 고정된 횟수의 반복 작업에 적합하다.
 * */
public class Appliaction01 {
    public static void main(String[] args){
        System.out.println("회원님 벤치 5개 더 할게요~~");

        for (int i = 1; i < 6; i++) {
            System.out.println("반복 " + i);
        }


    }
}
