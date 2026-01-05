package org.ohgiraffers.junit.chap03.filter;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
 * order란?
 * - 스프링 필터의 실행 순서를 지정하는 어노테이션입니다.
 * - 값이 낮을수록 우선순위가 높아 먼저 실행됩니다.
 * - 예를 들어, @Order(1)은 @Order(2)보다 먼저 실행됩니다.
 * - 이를 통해 여러 필터 간의 실행 순서를 제어할 수 있습니다.
 * */
@Component
@Order(1)
public class AuthTestFilter extends OncePerRequestFilter {

    /*
     * shouldNotFilter란?
     * - 특정 요청에 대해 필터를 적용하지 않을지 결정하는 메서드입니다.
     * - true를 반환하면 해당 요청에 대해 필터가 적용되지 않습니다.
     * - false를 반환하면 필터가 적용됩니다.
     *
     * 동작 방식
     * - 이 메서드는 HttpServletRequest 객체를 매개변수로 받아 요청의 URI를 확인합니다.
     * - 요청 URI가 "/admin/"로 시작하지 않는 경우(true 반환) 필터가 적용되지 않습니다.
     * - 요청 URI가 "/admin/"로 시작하는 경우(false 반환) 필터가 적용됩니다.
     * - 이를 통해 "/admin/**" 경로에만 인증 필터를 적용할 수 있습니다.
     * */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // /admin/** 만 필터 적용
        return !path.startsWith("/admin/");
    }


    /*
     * doFilterInternal란?
     * - 실제 필터링 로직을 구현하는 메서드입니다.
     * - HttpServletRequest와 HttpServletResponse 객체를 매개변수로 받아 요청과 응답을 처리합니다.
     * - FilterChain 객체를 사용하여 다음 필터 또는 최종 목적지로 요청을 전달할 수 있습니다.
     * - 이 메서드에서는 Authorization 헤더를 확인하여 Bearer 토큰이 있는지 검사합니다.
     * - 토큰이 없거나 유효하지 않은 경우 각각 401(Unauthorized) 또는 403(Forbidden) 상태 코드를 반환합니다.
     * - 토큰이 유효한 경우 요청을 다음 필터로 전달합니다.
     * */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String auth = request.getHeader("Authorization");

        if (auth == null || !auth.startsWith("Bearer ")) {
            // 인증 정보 없음 → 401
            writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "Missing bearer token");
            return;
        }

        String token = auth.substring(7);

        // 예시 규칙: "valid-token"만 통과, 그 외는 403
        if (!"valid-token".equals(token)) {
            writeError(response, HttpServletResponse.SC_FORBIDDEN, "Invalid token");
            return;
        }

        // 통과
        filterChain.doFilter(request, response);
    }

    private void writeError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
        response.getWriter().flush();
    }
}