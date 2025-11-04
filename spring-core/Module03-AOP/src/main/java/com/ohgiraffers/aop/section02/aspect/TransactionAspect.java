package com.ohgiraffers.aop.section02.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
 * [트랜잭션 관리]
 * 실제 애플리케이션에서는 @Transactional 어노테이션을 사용하거나
 * PlatformTransactionManager를 이용해 트랜잭션을 관리하지만,
 * 이는 예제를 위한 시뮬레이션임을 참고
 */
@Aspect
@Component
public class TransactionAspect {
    /*
     * () : 매개변수가 없는 메서드
     * (..) : 매개변수가 0개 이상 있는 메서드
     * (*) : 매개변수가 1개인 메서드 (모든 타입)
     * (String) : String 타입 매개변수가 하나인 메서드
     * (String, String, String) : String 타입 매개변수가 3개인 메서드
     * (String, ..) : String 매개변수가 시작되는 모든 메서드
     * (패키지 경로.class) : 특정 타입의 메서드
     */
    @Around(
            "execution(* com.ohgiraffers.aop.section02.MemberService.register*(..)) || " +
            "execution(* com.ohgiraffers.aop.section02.MemberService.update*(..)) || " +
            "execution(* com.ohgiraffers.aop.section02.MemberService.delete*(..)) || " +
            "execution(* com.ohgiraffers.aop.section02.MemberService.get*(..))"
    )
    public Object managerTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        try {
            System.out.println("[트랜잭션] " + methodName + " 트랜잭션 시작");
            Object result = joinPoint.proceed();
            System.out.println("[트랜잭션] " + methodName + "트랜잭션 커밋");
            return result;
        } catch (Exception e) {
            System.out.println("[트랜잭션] " + methodName + "트랜잭션 롤백 : " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
