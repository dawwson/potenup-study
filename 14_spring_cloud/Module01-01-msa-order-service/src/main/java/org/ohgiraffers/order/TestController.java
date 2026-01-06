package org.ohgiraffers.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 설정 값이 변경되었을 때
// (Consul KV, Config Server 등)
// 애플리케이션 재시작 없이 다시 로드될 수 있도록 하는 스코프
@RefreshScope
@RestController
public class TestController {

    // 환경 설정에서 "message"라는 키의 값을 주입
    // - application.yml
    // - Consul KV
    // - 환경 변수
    // 중 어디에 있든 상관없음
    // 만약 message 값이 없으면 기본값 "Default" 사용
    @Value("${message:Default}")
    private String message;

    @GetMapping("/")
    public String getMessage() {
        // 현재 주입된 message 값을 그대로 응답으로 반환
        // Consul KV의 message 값을 변경 후 refresh 하면
        // 이 응답이 바뀌는 것을 확인할 수 있음
        return this.message;
    }
}
