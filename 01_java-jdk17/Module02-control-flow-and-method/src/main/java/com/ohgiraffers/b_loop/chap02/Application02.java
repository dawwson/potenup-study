package com.ohgiraffers.b_loop.chap02;

import java.time.Period;

/*
 * while 문 응용
 * while 문을 사용해 사용자가 입력 기반 반복이나 조건 기반 종료를 구현할 수 있다.
 * 예 : 0이 될 때까지 숫자 감소.
 *
 * while 문 제어 키워드 :
 *   - break : 반복문을 즉시 종료하고 빠져나감.
 *   - continue : 현재 반복문을 건너뛰고 다음 반복으로 진행.
 *   - label break : 지정한 부분을 종료함 <-
 * */
public class Application02 {
    public static void main(String[] args) {

        int num = 5;

        System.out.println("용사의 모험 시작 : 몬스터를 물리치고 보물을 찾아라!");
        System.out.println("용사의 초기 체력 : " + num +"hp");
        while(num >0){
            if(num % 2 == 0){
                System.out.println("독의 늪지에 빠졌다. 체력 : " + num + "hp는 짝수라 회복 ");
                num--;
                System.out.println("회복 중에는 전투 불가능");
                continue;
            }

            if(num == 3){
                System.out.println("보물을 발견하였다. " + num + "hp");
                break;
            }

            System.out.println("몬스터와 전투 !  체력 감소 : " + num + "hp");
            num--;
        }
    }
}
