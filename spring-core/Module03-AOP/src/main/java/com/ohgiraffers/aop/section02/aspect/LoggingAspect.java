package com.ohgiraffers.aop.section02.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/*
 * @Aspect : 이 클래스가 AOP의 Aspect(관점)임을 선언. point cut(어디에 적용할지)과 Advice(무엇을 할지)를 하나의 단위로 묶음
 *           AspectJ 프레임워크에서 제공하며, 스프링 AOP와 통합되어 사용됨
 * @Component : Spring Container가 이 클래스를 Bean으로 등록하도록 지시
 *              AOP가 동작하려면 Spring이 관리하는 Bean이어야 프록시가 생성됨
 */
@Aspect
@Component
public class LoggingAspect {
    /*
     * @Before : 대상 메서드가 실행되기 전에 실행되는 Advice를 정의한다.
     *           로깅, 인증, 초기화 작업 등에 주로 사용된다.
     * Pointcut 표현식 : "execution(* com.ohgiraffers.aop.section02.MemberService.*(..))"
     * - execution : 메서드 실행 시점을 타겟팅하는 pointcut 디자인 패턴
     * - * : 모든 반환 타입 (void, int, String 등을 의미)
     *
     * - com.ohgiraffers.aop.section02.MemberService : 패키지와 클래스명 지정
     * - .* : memberService 클래스 내 모든 메서드를 의미
     * - (..) : 매개변수가 0개 이상인 모든 경우를 의미
     */
    @Before("execution(* com.ohgiraffers.aop.section02.MemberService.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        /*
         * [join point]
         * Advice가 적용되는 지점에 대한 정보를 제공하는 객체
         * 메서드 이름, 매개변수, 타겟 객체 등의 정보를 얻을 수 있음
         */

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        StringBuilder argStr = new StringBuilder();

        for (Object arg : args) {
            argStr.append(arg).append(",");
        }

        if (argStr.length() > 0) {
            argStr.delete(argStr.length() - 2, argStr.length());
        }

        System.out.println("[로그] " + methodName + " 메서드 실행 시작, 매개 변수 " + argStr);
    }

    /*
     * [@AfterReturning]
     * - 대상 메서드가 정상적으로 실행을 완료하고 값을 반환한 후 실행되는 Advice
     * - 반환값을 확인하거나 후처리 작업에 유용함
     * pointcut : 적용 대상을 지정. 여기서는 MemberService의 모든 메서드에 적용
     * returning = "result" : 메서드의 반환값을 "result"라는 이름의 매개변수로 받음
     *                        반환값의 타입은 Object로 어떤 타입이든 수용이 가능
     */
    @AfterReturning(
            pointcut = "execution(* com.ohgiraffers.aop.section02.MemberService.*(..))",
            returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[로그] " + methodName + " 메소드 정상 종료, 반환값 : " + result);
    }

    /*
     * [@AfterThrowing]
     * 대상 메서드 실행 중 예외가 발생했을 때 실행되는 Advice, 예외 로깅, 예외 복구 로직 등에 사용
     * pointcut : 적용 대상을 지정
     * throwing : "exception" 발생할 예외를 "exception"이라는 이름으로 받음
     *            Exception 타입으로 선언되며 모든 예외를 포함
     */
    @AfterThrowing(
            pointcut = "execution(* com.ohgiraffers.aop.section02.MemberService.*(..))",
            throwing = "exception"
    )
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[로그] " + methodName + " 메소드 실행 중 예외 발생 " + exception.getMessage());
    }

    /*
     * [@After]
     * 대상 메서드 실행이 완료된 후 실행되는 Advice
     * 정상 종료는 예외 발생이든 상관없이 항상 실행됨
     * 종료 로깅 혹은 자원 정리와 같은 곳에서 유용하게 상용됨
     */
    @After("execution(* com.ohgiraffers.aop.section02.MemberService.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[로그] " + methodName + " 메소드 실행 완료 ");
    }
}
