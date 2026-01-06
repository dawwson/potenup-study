package org.ohgiraffers.logging.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*
 * GlobalFilter란?
 * GlobalFilter는 Spring Cloud Gateway에서 제공하는 필터 인터페이스로,
 * 모든 요청에 대해 공통적으로 적용되는 필터를 구현할 때 사용된다.
 * 이를 통해 요청과 응답을 가로채어 로깅, 인증, 헤더 조작 등 다양한 기능을 수행할 수 있다.
 * GlobalFilter는 애플리케이션 전체에 걸쳐 적용되기 때문에
 * 특정 경로에 국한되지 않고 모든 라우트에 대해 필터링 작업을 수행할 수 있다.
 */

/*
 * Ordered란?
 * Ordered는 Spring Framework에서 제공하는 인터페이스로,
 * 빈(Bean)이나 컴포넌트의 우선순위를 지정하는 데 사용된다.
 * 이를 통해 여러 개의 빈이 동일한 타입을 가질 때, 어떤 빈이 먼저 처리되어야 하는지 결정할 수 있다.
 * Ordered 인터페이스를 구현하는 클래스는 getOrder() 메서드를 오버라이드하여 우선순위 값을 반환해야 한다.
 * 우선순위 값이 낮을수록 높은 우선순위를 가지며, 동일한 우선순위 값을 가진 경우에는 순서에 따라 처리된다.
 */
@Component
public class GlobalLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(GlobalLoggingFilter.class);

    /*
     * ServerWebExchange란?
     * ServerWebExchange는 Spring WebFlux에서 제공하는 인터페이스로,
     * Http 요청과 응답을 캡슐화하는 객체다.
     * 이를 통해 요청 헤더, 요청 본문, 응답 헤더, 응답 본문 등 다양한 Http 관련 정보를 접근하고 조작할 수 있다.
     * serverWebExchange는 비동기적이고 논블로킹 방식으로 동작하는 WebFlux의 핵심 컴포넌트 중 하나로,
     * 필터나 핸들러에서 Http 요청과 응답을 처리하는 데 사용된다.
     *
     * GatewayFilterChain이란?
     * GatewayFilterChain은 Spring Cloud Gateway에서 제공하는 인터페이스로,
     * 필터 체인을 나타낸다.
     * 이를 통해 여러 개의 ??? 답을 처리할 수 있다.
     * GatewayFilterChain은 필터가 적용된 후 다음 필터로 요청을 전달하는 역할을 한다.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Global pre filter : request id -> {}", exchange.getRequest().getId());

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    log.info("Global post filter : response code -> {}", exchange.getResponse());
                }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
