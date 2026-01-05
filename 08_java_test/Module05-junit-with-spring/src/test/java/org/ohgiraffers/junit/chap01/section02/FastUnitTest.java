package org.ohgiraffers.junit.chap01.section02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  // 어노테이션이 메서드 레벨에서만 쓰일 수 있음
@Retention(RetentionPolicy.RUNTIME)
@Test
@Tag("fast")
@DisplayName("[fast unit Test]")
@interface FastUnitTest {
}
