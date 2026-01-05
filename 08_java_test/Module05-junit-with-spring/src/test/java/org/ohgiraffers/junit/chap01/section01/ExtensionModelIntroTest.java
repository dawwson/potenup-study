package org.ohgiraffers.junit.chap01.section01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ohgiraffers.junit.chap01.common.LoggingExtension;

/*
 * - 횡단 관심사 분리 (AOP for tests)
 * - 테스트 코드와 공통 부가 기능(로그, mock 초기화, 환경 설정)을 분리하기 위해 사용된다.
 *
 * 2. Junit 4의 대체
 * - Junit 4의 @RunWith, @Rule, @ClassRule 등 복잡했던 확장 방식을
 *   Extension이라는 하나의 개념으로 통합하고 단순화
 *
 * 3. @ExtendWith(확장 등록)
 * - @ExtendWith(myExtension.class)
 * - 테스트 클래스나 메서드에 이 확장 기능을 사용하겠다고 등록하는 어노테이션
 * - 클래스 레벨 : 클래스 내 모든 @Test에 적용
 * - 메서드 레벨 : 특정 메서드(Test)에만 적용
 *
 * 4. 핵심 콜백(Callback) 인터페이스
 * - 개발자는 Junit이 제공한느 콜백 인터페이스를 구현하여 확장 기능을 만든다.
 */
@ExtendWith(LoggingExtension.class)
@DisplayName("Junit 5 확장 모델 테스트")
public class ExtensionModelIntroTest {

    @Test
    @DisplayName("성공하는 테스트 케이스")
    void shouldPass_WhenRunningTest() {
        System.out.println("[@Test] shouldPass_WhenRunningTest 본문 실행");
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("실패하는 테스트 케이스")
    void shouldFail_WhenRunningTest() {
        System.out.println("[@Test] shouldFail_WhenRunningTest 본문 실행");
        Assertions.assertTrue(true);
    }
}
