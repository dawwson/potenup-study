package com.ohgiraffers.chap02.section01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 * TDD의 딜레마
 *
 * 1. TDD의 정석 원칙
 *   - TDD의 핵심 사이클은 Red(실패) -> Green(성공) -> Refactor(개선)이다.
 *     테스트가 "Red" 상태라면, 개발자는 즉시 코드를 수정하여 Green으로 만들어야 할 의무가 있다.
 *
 * 2. @Disabled의 역할
 *   - @Disabled는 이 TDD 사이클을 의도적으로 중단시킨다.
 *   - 테스트 Skipped(건너뜀) 상태로 만들어 Red 상태를 숨기는 행위이다.
 *   - 따라서 정석적인 관점에서는 @Disabled는 실패를 방치하는 행위이므로 안티 패턴으로 간주될 수 있다.
 *
 * 3. 현실적인 사용 시점
 *   - 하지만 현실 개발에서는 TDD 원칙을 잠시 보류해야 할 때가 있다.
 *   - 대규모 리팩토링 : 핵심 로직 변경으로 수십 개의 테스트가 일시적으로 깨졌으나, 더 큰 단위 기능을 먼저 완성해야 할 때
 *   - 미완성 기능 : 기획이 변경되면서 아직 명세가 확정되지 않은 기능의 테스트를 임시로 보류할 때
 *   - 환경 의존성 : 로컬 DB에서만 통과하는 테스트를 CI(빌드 서버)에서는 제외하고 싶을 때
 *
 * 4. 결론
 *   - @Disabled는 나중에 갚아야 할 기술 부채이다.
 *   - 정석 개발자는 이 부채를 반드시 사유와 함께 명시하며 팀이 이 부채를 인지하고 추적할 수 있게 관리해야 한다.
 */
//@Disabled("Jira-456 : 이 모듈 전체가 현재 리팩토링 중 (2025-10-31까지)")
@DisplayName("테스트 비활성화 기능 테스트")
public class DisabledTest {

    @Test
    @DisplayName("실행되는 테스트 (Green)")
    void shouldRunThisTest() {
        System.out.println("run this test 실행됨");
    }

    @Test
    @Disabled
    @DisplayName("비활성화 되는 테스트 (사유 없음)")
    void shouldBeSkipped() {
        System.out.println("실행되지 않음");
        /*
         * 다음과 같이 테스트가 비활성화 되어 있는 사유를 명시하지 않으면
         * 해당 텟트를 왜 건너뛰어야 하는지 아무도 알 수 없다.
         * 1. 버그인가?
         * 2. 기능 미완성인가?
         * 3. 리팩터링 중인가?
         * 사유가 없는 기술 부채는 방치되며, 1년 뒤 왜 @Disabled죠? 라는 질문과 함께 코드를 부패시키게 된다.
         */
    }

    @Test
    @Disabled("JIRA-123 버그 수정 완료 시까지 임시 비활성화(담당자: 홍길동)")
    @DisplayName("비활성화되는 테스트 (사유 명시)")
    void shouldBeSkippedWithReason() {
        System.out.println("다음도 실행되지 않는다.");
        Assertions.assertTrue(true);
        /*
         * 정석 관점
         * 이것이 기술 부채를 관리하는 방법이다.
         * 1. (보고) Junit 리포트와 IDE는 "Skipped" 사유로 "JIRA-123" 메시지를 명시적으로 출력한다.
         * 2. (추적) 팀 동료는 이 사유를 보고 상황을 인지할 수 있으며, JIRA-123 이슈가 해결되면 이 @Disabled를 제거해야 함을 추적할 수 있다.
         * 3. (관례) "이슈 트래커 ID", "담당자", "기한" 등을 명시하는 것이 중요하다.
         */
    }
}
