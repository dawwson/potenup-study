package org.ohgiraffers.logging.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationHeaderFilter.class);

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    public static class Config {
        private String requiredRole;

        public String getRequiredRole() {
            return requiredRole;
        }

        public void setRequiredRole(String requiredRole) {
            this.requiredRole = requiredRole;
        }
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();

            log.info("required role from config : {}", config.getRequiredRole());

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            if (!isValidTokenAndRole(authorizationHeader, config.getRequiredRole())) {
                return onError(exchange, "Access Denied: Insufficient role on Invalid Token", HttpStatus.FORBIDDEN);
            }

            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);

        return response.setComplete();
    }

    private boolean isValidTokenAndRole(String authorizationHeader, String requiredRole) {
        // 실제로는 JWT 토큰 파싱 및 검증 로직이 필요하지만, 여기서는 단순히 예시로 처리한다.

        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            return false;
        }

        String token = authorizationHeader;

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!isValid(token)) {
            return false;
        }

        if (requiredRole == null || requiredRole.isEmpty()) {
            return true;
        }

        return token.contains(requiredRole);
    }

    private boolean isValid(String token) {
        return true;
    }
}
