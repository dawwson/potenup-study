package org.ohgiraffers.security.config;

import org.ohgiraffers.security.auth.handler.CustomAccessDeniedHandler;
import org.ohgiraffers.security.auth.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity     // url 경로 기반 필터 보안
@EnableMethodSecurity( // 메서드 보안 활성화
        // @PreAuthorize, @PostAuthorize spel을 사용하면 역할 기반 검사뿐만 아닌, 현재 인증된 사용자 정보까지 정의 가능
        prePostEnabled = true,
        // @Secured(ROLE_ADMIN, ROLE_EDITOR) 지정된 역할 중 하나라도 현재 사용자가 가지고 있으면 메서드 실행을 허용
        securedEnabled = true,
        // @RoleAllowed("ROLE_USER") @Secured와 같이 둘 중 하나라도 가지고 있으면 허용하지만, 이름에 ROLE_ 접두사를 요구하지 않음
        jsr250Enabled = true
)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomAccessDeniedHandler customAccessDeniedHandler, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://your-prudction-frontend-domain.com"
        ));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers",
                "X-Refresh-Token" // 리프레시 토큰을 위한 커스텀 헤더 (예시)
        ));

        // 클라이언트 브라우저에 노출할 수 있는 헤더 설정
        configuration.setExposedHeaders(Arrays.asList(
                "Authorization",
                "New-Access-Token"
        ));

        configuration.setAllowCredentials(true); // 쿠키, 인증 헤더 등 자격 증명 허용
        configuration.setMaxAge(3600L); // preflight 요청 캐시 시간 설정 1시간
        // 모든 경로 (/**)에 대해서 위에서 설정한 cors 설정을 등록하겠다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/**").hasAnyAuthority("ROLE_USER")
                        .requestMatchers("/api/admin/**").hasAnyAuthority("ROLE_ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                // 기존 시큐리티 필터에 jwtAuthenticationFilter를 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception ->
                    exception.authenticationEntryPoint(customAuthenticationEntryPoint)
                             .accessDeniedHandler(customAccessDeniedHandler)
                )
                .build();
    }
}
