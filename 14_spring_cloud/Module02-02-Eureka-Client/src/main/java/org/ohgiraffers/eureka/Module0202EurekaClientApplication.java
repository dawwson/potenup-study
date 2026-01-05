package org.ohgiraffers.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
 * EnableDiscoveryClient란?
 * @EnableDiscoveryClient 어노테이션은 Spring Cloud에서 제공하는 어노테이션으로
 * 해당 애플리케이션이 서비스 디스커버리 클라이언트로 동작하도록 설정하는 데 사용된다.
 * 이를 통해 애플리케이션은 서비스 레지스트리(예: Eureka, Consul 등)에 자신을 등록하고, 다른 서비스들을 검색할 수 있게 된다.
 * 이 어노테이션을 사용하면 마이크로서비스 아키텍처에서 서비스 간의 동적 연결과 확장성을 향상시킬 수 있다.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class Module0202EurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(Module0202EurekaClientApplication.class, args);
    }

}
