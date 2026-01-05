package org.ohgiraffers.junit.chap01.common;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/*
 * Junit 5의 Extension 모델은 테스트의 생명주기에 원하는 공통 로직을 끼워넣을 수 있게 하는 강력한 AOP 기능이다.
 *
 * 1. Callback 인터페이스 구현
 * - 특정 시점에 동작할 로직을 정의하기 위해 Junit이 제공하는 콜백 인터페이스를 구현한다.
 * - "BeforeTestExecutionCallback" : Test 메서드가 실행되기 직전에 호출된다.
 * - "AfterTestExecutionCallback" : Test 메서드가 실행된 직후에 호출된다.
 *
 * 2. ExecutionContext : 테스트 컨텍스트
 * - 테스트 실행에 대한 모든 정보를 담고 있는 객체이다.
 * - 이 객체를 통해 현재 실행 중인 테스트 클래스나 메서드명을 가져올 수 있다.
 *
 * 3. beforeTestExecution (전처리)
 * - context.getRequiredTestMethod().getName();
 *   컨텍스트에서 현재 실행할 메서드 정보를 꺼내 그 이름을 가져온다.
 * - 테스트 시작을 알리는 로그를 출력한다.
 *
 * 4. AfterTestExecution (후처리)
 * - @Test 메서드가 실행된 직후에 호출된다. (성공/실패) 무관
 * - context.getExecutionException() :
 *   컨텍스트에서 테스트 실행 중 발생한 예외 정보를 가져온다.
 * - orElse(null) : 예외가 Optional로 감싸져 있으므로, 예외가 없으면(성공 시) null을 반환
 * - if (exception != null) 예외 객체의 존재 여부로 테스트의 성공/실패를 판단하여 조건부 로그를 출력한다.
 */
public class LoggingExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        String testMethodName = context.getRequiredTestMethod().getName();
        System.out.println("===[LoggingExtension] " + testMethodName + " 테스트 실행 시작 ===");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        String testMethodName = context.getRequiredTestMethod().getName();
        Throwable exception = context.getExecutionException().orElse(null);

        if (exception != null) {
            System.out.println("=== ❌ [LoggingExtension] " + testMethodName + "테스트 실패===");
        } else {
            System.out.println("=== ✅ [LoggingExtension] " + testMethodName + " 테스트 실행 성공 ===");
        }
    }
}
