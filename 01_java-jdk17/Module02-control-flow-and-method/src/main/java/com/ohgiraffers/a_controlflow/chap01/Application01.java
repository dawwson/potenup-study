package com.ohgiraffers.a_controlflow.chap01;


/*
 * if 문
 * if 문은 조건식의 결과에 따라 프로그램의 실행 흐름을 분기시키는 제어문이다
 * 조건식이 true일 경우 실행 블록이 수행되며, false일 경우 건너뛴다.
 * 형식 : if(조건식) { 실행 코드} else{대체 코드}
 * 조건식은 boolean 타입으로 평가되며, 단일 조건 또는 복합 조건(논리 연산자 사용 가능)을 포함할 수 있다.
 *
 * 실생활 예시 : 만약 비가오면 우산을 들고 가고, 그렇지 않으면 그냥 간다.
 * */

/*
 * else-if
 * if 문을 사용해 복잡한 조건을 처리하거나 실생활 문제를 해결할 수 있다.
 * 형식 : if(조건식) { 실행 코드} else if(조건식) {} else{대체 코드}
 * */
public class Application01 {
    public static void main(String[] args){
        int score = 80;

        if(score >= 90){
            System.out.println("A 등급");
        }else if(score >= 80){
            System.out.println("B 등급");
        }else if(score >= 70){
            System.out.println("C 등급");
        }else{
            System.out.println("D 등급 이하");
        }
    }
}
