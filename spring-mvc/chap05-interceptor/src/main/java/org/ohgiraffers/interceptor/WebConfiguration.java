package org.ohgiraffers.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * 1. WebMvcConfigurer 인터페이스
 *   - Spring mvc의 기본 설정은 유지하면서 추가적인 커스터마이징이 필요할 때 구현하는 인터페이스이다.
 *   - 인터셉터 추가, CORS 설정, 정적 리소스 핸들러 추가 등
 *
 * 2. addInterceptor(InterceptorRegistry registry)
 *   - WebMvcConfigurer가 제공하는 메서드로, 인터셉터를 등록할 때 오버라이드 한다.
 *
 * 3. Interceptor
 *   - 컨트롤러의 메서드가 실행되기 전, 후 그리고 완료된 후에 공통 로직을 끼워 넣을 수 있도록 하는 기술이다.
 *   - 예: 로그인 인증 체크, 성능 측정, 공통 로그 등
 *
 * 4. DI(의존성 주입) 방식 사용
 *   - register.addInterceptor (stopWatchInterceptor)
 *   - @Autowired로 주입받은 Spring bean을 등록해야 인터셉터 내부에서도 다른 bean(service)를 @Autowired로 정상적으로 주입받아 사용할 수 있다.
 *
 * 5. addPathPatterns("/*")
 *   - 해당 인터셉터가 어떤 URL 패턴에 적용될지를 지정한다.
 *   - "/*" : "/"(루트) 경로 바로 하위의 모든 경로를 의미한다.
 *   - [참고] /** : 모든 하위 경로(예: /admin/users)까지 포함하는 더 포괄적인 패턴이다.
 * 6. excludePathPatters("..")
 *   - 특정 경로에 대해서는 인터셉터를 적용하지 않도록 제외할 수 있다.
 *   - 정적 리소스와 에러 페이지는 불필요한 부하를 막기 위해 반드시 제외한다.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private final StopWatchInterceptor stopWatchInterceptor;

    @Autowired
    public WebConfiguration(StopWatchInterceptor stopWatchInterceptor) {
         this.stopWatchInterceptor = stopWatchInterceptor;
     }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(stopWatchInterceptor)
                 .addPathPatterns("/*")
                 .order(0)
                 .excludePathPatterns("/css/**")
                 .excludePathPatterns("/images/**")
                 .excludePathPatterns("/js/**")
                 .excludePathPatterns("/error/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }
}
