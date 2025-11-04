package com.ohgiraffers.aop.section02.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {
    /*
     * [@Around]
     * 메서드 실행 전후에 실행되는 Advice를 정의하는 어노테이션
     * - 가장 강력한 Advice 타입으로, 대상 메서드의 실행 자체를 제어할 수 있음
     * - 메서드 호출 전후에 추가 로직을 삽입하거나, 아예 메서드 실행을 막을 수 있음
     * - pointcut 표현식과 함께 사용되어 적용 대상을 지정함
     */
    @Around("execution(* com.ohgiraffers.aop.section02.MemberService.*(..))")
    public Object measurePerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("[Around] 시작");
        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed(); // 호출된 메서드 반환값

            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            System.out.println("[성능] " + methodName + " 메소드 실행 시간 " + executionTime + "ms");
            System.out.println("[Around] 종료");
        }
    }
}
