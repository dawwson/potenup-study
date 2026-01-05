package com.ohgiraffers.doubles.chap02.section02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/*
 * spy?
 * - Mock과 달리 가짜 객체를 만드는 것이 아니라 실제 객체를 생성하고,
 *   그 실제 객체를 감싸는 프록시를 만든다.
 *
 * 2. 부분 모킹의 의미
 * - spy 객체는 when()으로 행동을 정의(스터빙)하지 않으면, 가짜가 아닌 실제 메서드 로직을 그대로 호출한다.
 * - 개발자가 필요한 일부 메서드만 골라서 when()으로 가짜 동작을 주입하고 나머지 메서드는 실제 로직대로 동작하게 만들 수 있다.
 *
 * 3. Mock
 * - 빈 껍데기 가짜 객체를 만든다.
 * - stubbing 하지 않은 메서드 모두 0, false, null을 반환한다.
 * - 정석 용도 : 인터페이스를 mocking 하여 외부 의존성을 완전히 차단할 때
 *
 * 4. spy
 * - 실제 객체를 생성하고, 그 위에 감시 래퍼를 씌운다.
 * - stubbing 하지 않은 메서드드는 모두 실제 객체의 원본 메서드를 호출한다.
 * - 정석 용도 : 구현 클래스를 테스트할 때 대부분의 실제 로직은 그대로 실행하지만,
 *   많은 연산을 요구하는 일부 메서드(예: DB, 네트워크 호출 등)를 가짜로 대체하는 경우 사용된다.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Mockito spy 기능 테스트 (부분 모킹)")
public class MockitoSpyTest {

    @Spy
    private RealEmailService emailService;

    @Test
    @DisplayName("spy 객체는 stubbing(정의)하지 않으면 실제 원본 메서드를 호출한다.")
    void shouldCallRealMethod_WhenSpyIsNotStubbed() {

        String result = emailService.sendEmail("test@example.com", "Spy 테스트");

        Assertions.assertEquals("전송 성공", result, "실제 메서드의 반환값이 나와야 한다.");

        BDDMockito.then(emailService)
                .should(BDDMockito.times(1))
                .sendEmail(BDDMockito.anyString(), BDDMockito.anyString());
    }

    @Test
    @DisplayName("Spy 객체는 doReturn/when으로 부분 모킹이 가능하다")
    void shouldReturnStubbedValue_WhenUsingSpyWithDoReturn() {

        // stubbing
        BDDMockito.doReturn("가짜 전송")
                .when(emailService)
                .sendEmail(BDDMockito.eq("fake@example.com"), BDDMockito.anyString()); // 모킹 동작 조건

        String result1 = emailService.sendEmail("fake@example.com", "가짜 테스트");
        String result2 = emailService.formatEmail("user@example.com", "실제 테스트");

        Assertions.assertEquals("가짜 전송", result1, "Stubbing 된 가짜 값이 반환되어야 합니다.");
        Assertions.assertEquals("실제 테스트", result2, "Stubbing 안 된 메서드는 실제 로직이 동작");

        BDDMockito.then(emailService).should()
                .sendEmail(BDDMockito.eq("fake@example.com"), BDDMockito.anyString());
        BDDMockito.then(emailService).should()
                .formatEmail(BDDMockito.eq("user@example.com"), BDDMockito.anyString());
    }

    // -----------------------------------
    @Spy
    private RealEamilService realEamilService;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("@InjectMocks는 @Spy 객체를 실제 의존성으로 주입한다.")
    void shouldUseSpy_WhenInjectedIntoSut() {

        BDDMockito.doReturn("Stubbed Mail OK")
                .when(realEmailServiceForInject)
                .sendEmail(BDDMockito.eq("testUser@example.com"), BDDMockito.anyString());

        String result = userService.registerUser("testUser");

        Assertions.assertEquals("STUBBED MAIL OK", result, "SUT의 고유 로직이 동작해야 합니다.");

        BDDMockito.then(realEmailServiceForInject).should()
                .sendEmail(BDDMockito.eq("testUser@example.com"), BDDMockito.anyString());

    }
}
