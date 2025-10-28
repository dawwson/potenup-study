package com.ohgiraffers.injection.chap01.section01.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*
 * Spring 컴포넌트 스캔 설정
 * - @ComponentScan
 *   - 지정된 패키지에서 @Component, @Service 등의 어노테이션이 붙은 클래스를 탐지하여 Bean으로 등록한다.
 *   - basePackages: "경로"를 설정하여 현재 패키지와 하위 패키지 모두 스캔
 * - 동작 과정
 *   1. 컨테이너 시작 시 지정된 패키지를 탐색
 *   2. @Component, @Service 등이 붙은 클래스를 인식
 *   3. 해당 클래스들을 bean으로 등록하고 의존성 주입 수행
 * - 이점
 *   1. 코드 간소화 : @Bean으로 일일이 정의할 필요 없음
 *   2. 유연성 : 새로운 컴포넌트 추가 시 설정 변경 없이 자동으로 인식
 */
@Configuration
@ComponentScan(basePackages = "com.ohgiraffers.injection.chap01.section01")
public class AppConfig {
    /*
     * 컴포넌트 스캔을 활용하면 발생하는 이점
     * 1. 코드 간소화 및 생산성 향상
     *   - @Bean으로 개별 클래스를 수동 정의할 필요 없이, @Component, @Service 등의 어노테이션으로 자동 등록
     * 2. 유연한 확장성
     *   - 새로운 컴포넌트를 추가할 때 AppConfig를 수정하지 않고, 해당 패키지에 클래스만 추가하면 자동 인식
     * 3. 의존성 주입 자동화
     *   - @Autowired와 함께 사용하면 컴포넌트 간 의존성을 spring이 자동으로 연결
     * 4. 일관된 bean 관리
     *   - 스캔된 bean은 spring IoC 컨테이너에 의해 일관되게 관리
     * 5. 개발 표준화
     *   - @Component, @Service 등으로 계층별 역할 명시 가능
     *
     * 주의사항
     * - 스캔 범위가 너무 넓으면 불필요한 클래스까지 Bean으로 등록될 수 있음 -> 성능 저하
     *   - 동일 타입의 bean이 여러 개일 경우 충돌 발생 -> @Qualifier 또는 @Primary로 해결
     */
}
