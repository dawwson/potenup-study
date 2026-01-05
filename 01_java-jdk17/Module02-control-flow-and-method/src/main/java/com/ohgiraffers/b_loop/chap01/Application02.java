package com.ohgiraffers.b_loop.chap01;

/*
 * for 문 응용
 * for 문을 활용해 배열 처리나 합계 계산 같은 작업을 수행할 수 있다.
 * */
public class Application02 {
    public static void main(String[] args) {

        int goal = 100;
        System.out.println("wod 칼로리 목표 " + goal +"입니다.");
        int sum = 0;

        for (int i = 10; sum <= goal;){
            System.out.println("sum : " + sum);
            sum += i;

            if(sum == 50){
                System.out.println("살려줘~~~");
            }else if (sum == 80){
                System.out.println("할 수 있다.");
            }
        }
    }
}
