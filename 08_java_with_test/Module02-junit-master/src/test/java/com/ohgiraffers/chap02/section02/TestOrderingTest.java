package com.ohgiraffers.chap02.section02;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

/*
 * TDD와 유닛 테스트의 대원칙은 테스트 독립성이다.
 *
 * 1. 테스트 독립성이란?
 * - 각 @Test 메서드는 어떤 순서로 실행되든, 서로에게 영향을 주지 않고, 항상 동일한 결과를 보장해야 한다.
 *
 * 2. 왜 독립성이 중요한가?
 * - 순서 의존성이 생기는 순간, 테스트는 깨지기 쉬운 상태가 된다.
 * - 예시 : create 테스트가 실패하면, 그 상태에 의존하는 read, update 테스트가 자신들의 로직이 정상임에도
 *         연쇄적으로 실패하게 된다.
 * - 이는 flickering Test (빌드 서버에서는 실패 로컬에서는 성공 등)의 주된 원인이 된다.
 *
 * 3. 결론 : order는 안티 패턴이다.
 * - 따라서 유닛 테스트 레벨에서 Order를 사용하여 순서를 강제하는 것은
 *   테스트 독립성 원칙을 정면으로 위해하는 안티패턴임을 참고하자.
 *
 * 4. 유일한 예외 : 통합 테스트
 * - 다만, 유닛 테스트가 아닌 통합 테스트 (E2E, End-to-End) 레벨에서는 현실적인 이유로 순서 제어가 필요할 수 있다.
 */

/*
 * @TestMethodOrder : 순서 지정 전략
 * - 역할 : 클래스 레벨에서, 이 클래스의 @Test 메서드들을 어떤 전략으로 정렬할지 선언한다.
 * - 위치 : "class" 선언부
 * - 전략
 * 1. OrderAnnotation.class : 메서드에 붙ㅇㄴ @Order 어노테이션의 숫자를 따름
 * 2. MethodName.class : 메서드 이름의 알파벳 순서로 실행
 * 3. DisplayName.class : "DisplayName"의 가나다/알파벳 순서로 실행
 * 4. Random.class : 무작위 순서로 실행
 */
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("테스트 순서 제어")
public class TestOrderingTest {
    /*
     * 공유 자원
     * - static으로 선언되어야 한다.
     * - 이유 : Junit이 @Test 새로운 객체가 만들어지고 사라진다.
     */
    private static final Map<String, String> sharedDatabase = new HashMap<>();

    @Test
    @Order(3)
    @DisplayName("C : 삭제(delete) 테스트")
    void shouldDeleteResource() {
        System.out.println("Step3: C - Delete Resource(실행 순서 3)");

        String removedUser = sharedDatabase.remove("user1");

        Assertions.assertEquals("홍길동", removedUser, "user1(홍길동)이 정상적으로 삭제되어야 합니다.");
        Assertions.assertNull(sharedDatabase.get("user1"), "삭제된 후에는 null이어야 합니다.");
        Assertions.assertEquals(0, sharedDatabase.size());
    }

    @Test
    @Order(1)
    @DisplayName("A : 생성(create) 테스트")
    void shouldCreateResource() {
        System.out.println("Step1: A - Create Resource");

        sharedDatabase.put("user1", "홍길동");

        Assertions.assertEquals("홍길동", sharedDatabase.get("user1"));
        Assertions.assertEquals(1, sharedDatabase.size());
    }

    @Test
    @Order(2)
    @DisplayName("B : 조회(read) 테스트")
    void shouldReadResource() {
        System.out.println("Step2: B - Read Resource");

        String userName = sharedDatabase.get("user1");

        Assertions.assertNotNull(userName, "@Order(1)에 생성된 user1이 조회되어야 합니다.");
        Assertions.assertEquals("홍길동", userName);
    }
}
