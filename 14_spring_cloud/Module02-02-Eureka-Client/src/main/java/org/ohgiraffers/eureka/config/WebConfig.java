package org.ohgiraffers.eureka.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

    /*
     * @LoadBalanced란?
     * @LoadBalanced는 Spring Cloud에서 제공하는 어노테이션으로
     * RestTemplate 빈에 적용되어 클라이언트 측 로드 밸런싱 기능을 활성화하는 데 사용된다.
     * 이를 통해 여러 인스턴스가 존재하는 서비스에 대한 요청을 분산시켜 시스템의 가용성과 성능을 향상시킬 수 있다.
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
