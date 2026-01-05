package com.ohgiraffers.b_loop.chap02;


/*
 * [while 문 동작의 메모리 구조]
 * 1) while 문이 시작될 때, 조건식에 사용되는 변수(예: count, limit)는 스택에 생성된다.
 * 2) 조건식이 참이면 본문을 실행하고, 조건을 다시 검사하며 반복한다.
 * 3) 조건이 거짓이 되면 while 문의 스택 프레임이 제거되고 종료된다.
 *
 * [성능 최적화 관점]
 * 1) 조건식 계산을 최소화하면 성능이 향상된다.
 *    예: while (count <= limit) { ... }로 상수값 활용.
 * 2) 비최적화된 경우, 반복마다 calculateLimit()을 호출하여 성능 저하 발생.
 * 3) 무한 루프(while (true))는 스택 오버플로우를 유발할 수 있으니 break 사용 권장.
 * 4) JIT 컴파일러가 루프 언롤링(unrolling)으로 최적화 가능.
 */
public class WhileDeep {
    public static void main(String[] args) {
        // 1. 비최적화 while 문: 조건식에서 매번 계산
        System.out.println("1. 비최적화 while 문");
        long startTime = System.nanoTime();
        int count = 0;
        while (count <= calculateLimit()) { // 매 반복마다 calculateLimit() 호출
            System.out.println("비최적화 카운트: " + count);
            count++;
        }
        long endTime = System.nanoTime();
        System.out.println("실행 시간: " + (endTime - startTime) + "ns\n");
        // while 문은 매 반복마다 calculateLimit() 메서드를 호출하여 조건식을 평가




        // 2. 최적화 while 문: 상수값 활용
        System.out.println("2. 최적화 while 문");
        startTime = System.nanoTime();
        count = 0;
        int limit = calculateLimit(); // 한 번만 계산
        while (count <= limit) { // 단순 비교로 최적화
            System.out.println("최적화 카운트: " + count);
            count++;
        }
        endTime = System.nanoTime();
        System.out.println("실행 시간: " + (endTime - startTime) + "ns");
        // calculateLimit()을 매번 호출하지 않고 Stack 프레임에 저장하여 호출
    }

    private static int calculateLimit() {
        return "안녕하세요 ~~~".length(); // 문자열 길이 반환 (13)
    }
}