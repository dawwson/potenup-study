package com.ohgiraffers.chap01.section04.config;

import com.ohgiraffers.chap01.section04.service.KakaoPayGateway;
import com.ohgiraffers.chap01.section04.service.NaverPayGateway;
import com.ohgiraffers.chap01.section04.service.PaymentsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    /*
     * Spring Bean 생명주기(Bean LifeCycle)
     * - Spring IoC 컨테이너가 bean을 생성하고 관리하는 일련의 과정을 의미한다.
     * - 주요 단계
     *   1. 인스턴스 생성 : 컨테이너가 paymentService 객체를 생성(new 호출)
     *   2. 의존성 주입 : 생성자를 통해 paymentInterface가 주입됨
     *   3. 초기화 : @Bean의 initMethod로 지정된 메서드가 호출되어 초기 설정 수행
     *     - 리소스 초기화 (db 연결, 캐싱 등)
     *   4. 사용 : 어플리케이션에서 bean을 사용
     *   5. 소멸 : @Bean의 destroy=""로 지정된 메서드가 호출되어 리소스 정리
     *
     * - @Bean(initMethod, destroyMethod)
     *   - Spring이 기본적으로 제공하는 @PostConstruct, @PreDestroy 대신 사용자 정의 초과 소멸 메서드를 지정
     *   - initMethod : Bean이 생성되고 의존성 주입 후 호출될 메서드를 정리
     *   - destroyMethod : 컨테이너 종료 시 bean 소멸 직전에 호출될 메서드 정의
     *
     * - 이점 :
     *   - 생명주기 단계를 명시적으로 제어하며, 리소스 관리와 어플리케이션 동작을 최적화
     *   - 예 : 결제 서비스에서 초기화 시 결제 게이트웨이 인증, 소멸 시 세션 종료
     *
     * - 주의 :
     *   - initMethod와 destroyMethod는 public이고 매개변수가 없어야 하며, 예외 처리를 잘 설계해야 한다.
     */
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public PaymentsService paymentsService(){
        return new PaymentsService(kakaoPayGateway());
    }

    @Bean()
    public KakaoPayGateway kakaoPayGateway(){
        return new KakaoPayGateway();
    }

    @Bean
    public NaverPayGateway naverPayGateway(){
        return new NaverPayGateway();
    }
}
