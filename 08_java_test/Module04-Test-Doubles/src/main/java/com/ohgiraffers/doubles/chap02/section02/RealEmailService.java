package com.ohgiraffers.doubles.chap02.section02;

/*
 * [Spy 대상 클래스]
 * - `@Spy`는 이 '실제' 클래스의 '인스턴스'를 생성하고 '감싼다(Wrapping)'.
 * - `@Spy`가 인스턴스를 '자동으로' 생성하도록 하려면, '기본 생성자'가 필요하다.
 */
public class RealEmailService {

    /**
     * [Spy의 '실제 메서드' 1]
     * - (가정) 이 메서드는 '실제' 외부 SMTP 서버와 '통신'하는 '무거운' 로직이다.
     * - TDD(유닛 테스트) 시 '절대' 호출되어서는 안 되는 '경계'이다.
     */
    public String sendEmail(String to, String subject) {
        System.out.println("!!! [실제 메서드 호출] !!!: " + to + "에게 " + subject + " 전송 시도");
        return "전송 성공"; // 📌 '실제' 반환값
    }

    /**
     * [Spy의 '실제 메서드' 2]
     * - (가정) 이 메서드는 '단순한' 내부 로직이다.
     * - TDD 시 '실제로' 호출되어 '검증'하고 싶은 로직이다.
     */
    public String formatEmail(String to, String subject) {
        System.out.println("... (실제 메서드 호출) 포맷팅 중 ...");
        return subject + " to " + to;
    }
}