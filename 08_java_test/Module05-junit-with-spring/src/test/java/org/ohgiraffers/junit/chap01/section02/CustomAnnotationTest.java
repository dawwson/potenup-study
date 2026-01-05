package org.ohgiraffers.junit.chap01.section02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("커스텀 어노테이션 활용")
public class CustomAnnotationTest {

    @FastUnitTest
    @DisplayName("빠른 단위 테스트")
    void shouldRunFase() {
        System.out.println("[Test] 'fast' 태그가 적용된 빠른 테스트 실행됨");
        Assertions.assertTrue(true);
    }

    @SlowIntegrationTest
    @DisplayName("느린 통합 테스트")
    void shouldRunSlowWithLogging() {
        System.out.println("[@Test] slow 태그 및 LoggingExtension이 적용된 느린 테스트 실행됨");
        Assertions.assertTrue(true);
    }
}
