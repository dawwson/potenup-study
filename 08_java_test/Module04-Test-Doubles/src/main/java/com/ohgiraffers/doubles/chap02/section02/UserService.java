package com.ohgiraffers.doubles.chap02.section02;

/*
 * [SUT: @Spy를 '주입'받는 클래스]
 * - TDD의 '정석'은 '인터페이스(IProductRepository)'에 의존하는 것이지만,
 * - '레거시 코드'나 '특수 상황'에서는 '구현 클래스(RealEmailService)'에 '직접' 의존할 수도 있다.
 * - `@Spy`는 이 '구현 클래스 의존성'을 테스트할 수 있게 해준다.
 */
public class UserService {
    private final RealEmailService emailService;

    // '구현 클래스'를 '생성자 주입' 받는다.
    public UserService(RealEmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * [SUT의 고유 로직]
     * - 'emailService'의 '실제' 'sendEmail' 메서드를 호출하고,
     * - 그 '결과'를 '대문자'로 바꾸는 것이 'SUT의 고유 로직'이다.
     */
    public String registerUser(String username) {
        String emailAddress = username + "@example.com";
        String result = emailService.sendEmail(emailAddress, "회원가입 환영");
        return result.toUpperCase();
    }
}