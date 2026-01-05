package org.ohgiraffers.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/*
 * EnableEurekaServer란?
 * @EnableEurekaServer 어노테이션은 Spring Cloud Netflix에서 제공하는 어노테이션ㅇ로,
 * 해당 애플리케이션을 Eureka 서버로 설정하는 데 사용된다.
 * Eureka 서버는 마이크로서비스 아키텍처에서 서비스 디스커버리(서비스 등록 및 검색)을 담당하는 핵심 컴포넌트이다.
 * 이 어노테이션을 사용하면 애플리케이션이 Eureka 서버로 동작하게 되며,
 * 다른 마이크로서비스들이 자신을 등록하고, 필요성 서비스 인스턴스를 검색할 수 있게 된다.
 */
@EnableEurekaServer
@SpringBootApplication
public class Module0202EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Module0202EurekaServerApplication.class, args);
    }

}
