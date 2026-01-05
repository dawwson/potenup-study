package com.ohgiraffers.b_loop.chap03;

/*
 * do-while문
 * do-while문은 실행 코드를 최소 1회 실행한 후 조건식을 확인하는 반복문이다.
 * 형식 : do{실행코드} while(조건식);
 * 조건이 처음부터 false여도 무조건 1회 실행 보장
 * */
public class Application {
    public static void main(String[] args) {

        int num = 0;
        do {
            System.out.println("실행 : " + num);
            num++;
        }while(num < 0);

    }
}
