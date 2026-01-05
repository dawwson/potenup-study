package org.ohgiraffers.security.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ohgiraffers.security.exception.ExpiredJwtCustomException;
import org.ohgiraffers.security.exception.InvalidJwtCustomException;
import org.ohgiraffers.security.exception.InvalidRefreshTokenException;
import org.ohgiraffers.security.token.service.RefreshService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshService refreshService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, RefreshService refreshService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshService = refreshService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Authorization: Bearer <token>에서 <token> 부분 추출
        String accessToken = jwtTokenProvider.resolveToken(request);

        try {
            if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtCustomException e) {
            // access token이 만료된 경우 refresh token으로 access token 재밝
            try {
                String refreshTokenValue = jwtTokenProvider.resolveRefreshToken(request);

                if (refreshTokenValue != null) {
                    String newTokens = refreshService.refreshAccessToken(refreshTokenValue);

                    Authentication authentication = jwtTokenProvider.getAuthentication(newTokens);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    response.setHeader("New-Access-Token", newTokens);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } catch (InvalidRefreshTokenException e) {
                e.printStackTrace();
                SecurityContextHolder.clearContext();
            } catch (Exception e) {
                e.printStackTrace();
                SecurityContextHolder.clearContext();
            }
        } catch (InvalidJwtCustomException e) {
            e.printStackTrace();
            SecurityContextHolder.clearContext();
        }

        // 다음 필터로 넘김
        filterChain.doFilter(request, response);
    }
}
