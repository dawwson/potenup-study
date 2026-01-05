package org.ohgiraffers.junit.chap01.section02;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ohgiraffers.junit.chap01.common.LoggingExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Test
@Tag("Slow")
@DisplayName("[slow unit Test]")
@ExtendWith(LoggingExtension.class)
@interface SlowIntegrationTest {
}
