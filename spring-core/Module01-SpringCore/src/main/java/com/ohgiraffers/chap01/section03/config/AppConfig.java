package com.ohgiraffers.chap01.section03.config;

import com.ohgiraffers.chap01.section03.service.KakaoPayGateway;
import com.ohgiraffers.chap01.section03.service.NaverPayGateway;
import com.ohgiraffers.chap01.section03.service.PaymentsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
    /*
     * 싱글톤 스코프 (singleton scope)
     * - spring의 기본 스코프이며, 컨테이너 내에서 단일 인스턴스만 생성된다.
     * - 모든 요청에서 동일한 객체를 반환하므로 메모리 효율적이고, 상태를 공유해야 하는 경우 유용하다.
     * - 주의 : 상태를 가지면 모든 요청이 이를 공유하기 때문에 상태를 갖지 않는 객체에 적합하다.
     */
    @Bean("singlePay")
    @Scope("singleton")
    public PaymentsService paymentsService(){
        return new PaymentsService(kakaoPayGateway());
    }

    /*
     * 프로토타입 스코프 (Prototype Scope)
     * - 요청할 때마다 새로운 인스턴스를 생성하며, 컨테이너는 생성 후 해당 객체를 관리하지 않는다.
     * - 각 요청이 독립적인 상태를 유지해야 할 때 유용하며, 상태를 가지는 객체에 적합하다.
     * - 주의 : 빈이 생성된 후 spring이 소멸 시점을 관리하지 않으므로, 메모리 해제를 별도로 고려해야 할 수 있다.
     * - 사용 사례 : 요청별로 독립적인 데이터가 필요한 경우
     */
    @Bean("protoPay")
    @Scope("prototype")
    public PaymentsService paymentsServiceProtoType(){
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
