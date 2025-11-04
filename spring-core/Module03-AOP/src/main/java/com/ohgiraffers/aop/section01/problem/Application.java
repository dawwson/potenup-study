package com.ohgiraffers.aop.section01.problem;

public class Application {
    public static void main(String[] args) {
        MemberService memberService = new MemberService();

        try {
            System.out.println("회원 가입");
            Member member = new Member("user@example.com", "pass123", "홍길동", "010-1234-1234", "test");
            memberService.registerMember(member);
        } catch (Exception e) {
            System.out.println("예외 발생 : " + e.getMessage());
        }
    }
    /*
     * AOP를 사용하지 않았을 때 문제점
     * 1. 코드 중복 : 로깅, 성능 측정, 트랜잭션 관리 코드가 각 메서드에 반복됨
     * 2. 낮은 응집도 : 각 메서드가 비즈니스 로직 외에도 여러 관심사를 처리함
     * 3. 유지보수 어려움 : 횡단 관심사 변경 시 여러 메서드를 수정해야 함
     * => AOP(관점 지향 프로그래밍)으로 이것을 해결할 수 있다.
     */
}
