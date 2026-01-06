package org.ohgiraffers.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
 * 이 애플리케이션이 서비스 디스커버리(Consul, Eureka 등)에 자신을 등록하는 클라이언트임을 선언
 * 요즘(Spring Cloud 2021+) 기준 starter 의존성만 있어도 자동 활성화됨
 * 하지만 학습 단계에서는 명시적으로 붙이는 게 좋음
 */
@EnableDiscoveryClient
@SpringBootApplication
public class Module0101MsaProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Module0101MsaProductServiceApplication.class, args);
    }

}
