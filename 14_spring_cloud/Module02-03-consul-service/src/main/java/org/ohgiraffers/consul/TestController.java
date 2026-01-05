package org.ohgiraffers.consul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @RefreshScope란?
 * @RefreshScope는 Spring Cloud에서 제공되는 어노테이션으로
 * 해당 빈이 동적으로 갱신될 수 있도록 설정하는 데 사용된다.
 * 이 어노테이션을 사용하면, 외부 구성 소스(예: Spring Cloud config server, consul 등)에서 변경된 설정 값을
 * 애플리케이션에서 반영할 수 있어, 재시작 없이도 설정 변경이 가능하다.
 */
@RestController
@RefreshScope
public class TestController {

    @Value("${message:Default}")
    private String message;

    @GetMapping("/")
    public String getMessage() {
        return this.message;
    }

}
