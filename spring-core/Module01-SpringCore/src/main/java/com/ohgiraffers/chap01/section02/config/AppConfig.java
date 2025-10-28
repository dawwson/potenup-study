package com.ohgiraffers.chap01.section02.config;

import com.ohgiraffers.chap01.section02.service.KakaoPayGateway;
import com.ohgiraffers.chap01.section02.service.NaverPayGateway;
import com.ohgiraffers.chap01.section02.service.PaymentsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * [@Configuration]
 * 스프링 IoC 컨테이너에게 해당 클래스가 Bean 설정 정보를 가지고 있다는 것을 알려주는 어노테이션이다.
 * 이 클래스는 하나 이상의 @Bean 메서드를 제공하고 스프링 컨테이너가 bean 정의를 생성하고 런타임 시 Bean 요청을 처리하는 데 사용된다.
 * 또한, @Configuration 클래스 자체도 스프링 컨테이너에 하나의 빈으로 등록되어 관리된다.
 * 이는 스프링이 설정 클래스를 통해 애플리케이션의 구조와 의존 관계를 파악하고 필요한 객체들을 생성 및 관리할 수 있도록 하는 핵심 어노테이션이다.
 */
@Configuration
public class AppConfig {
    /*
     * [Bean]
     * 스프링 IoC 컨테이너가 관리할 Bean 객체를 생성하는 메서드임을 나타낸다.
     * 메서드 이름은 Bean의 이름이 되며, 반환되는 객체가 스프링 컨테이너에 의해 관리되는 Bean이 된다.
     * Bean은 싱글톤으로 관리되어 애플리케이션 전반에서 동일한 객체가 사용된다.
     */
    @Bean
    public KakaoPayGateway kakaoPayGateway(){
        return new KakaoPayGateway();
    }

    @Bean
    public NaverPayGateway naverPayGateway(){
        return new NaverPayGateway();
    }

    @Bean
    public PaymentsService paymentsService(){
        return new PaymentsService(kakaoPayGateway());
    }
}
