package com.ohgiraffers.chap02.section03;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

@DisplayName("사용자(user) 서비스 명세")
public class NestedTest {
    private static class User {
        String username; String name;
        User(String u, String n) { username = u; name = n; }
    }

    // (SUT) [개편] 'Map'을 사용하도록 SUT 로직 수정
    private static class UserService {
        // 단일 사용자가 아닌, DB(Map)에 의존
        private final Map<String, User> userDatabase;

        // 의존성 주입(DI) 생성자
        UserService(Map<String, User> userDatabase) {
            this.userDatabase = userDatabase;
        }

        // '가입' 로직 (예외 계약 추가)
        void join(User u) {
            if (userDatabase.containsKey(u.username)) {
                throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
            }
            userDatabase.put(u.username, u);
        }

        // 'ID 중복 확인' 로직
        boolean isUsernameTaken(String username) {
            return userDatabase.containsKey(username);
        }
    }

    private UserService userService;
    private Map<String, User> userDatabase;
    private User testUser;

    @BeforeEach
    void setUpOrder() {
        System.out.println("@BeforeEach : 공유 의존성 db 초기화");

        this.userDatabase = new HashMap<>();
        this.userService = new UserService(this.userDatabase);
        this.testUser = new User("test1234", "홍길동");
    }

    @Nested
    @DisplayName("시나리오 : 사용자가 이미 존재하는 경우 (given)")
    class WhenUserAlreadyExists {
        @BeforeEach
        void setupInner_Exists() {
            System.out.println("nested 시나리오 : 사용자 존재하는 경우, testUser 미리 등록됨");
            userService.join(testUser);
        }

        @Test
        @DisplayName("isUsernameTaken() 호출 시 (when) -> true를 반환한다.")
        void isUsernameTaken_ShouldReturnTrue() {
            System.out.println("isUsernameTakeShouldReturnTrue 실행");
            Assertions.assertTrue(userService.isUsernameTaken(testUser.username));
        }

        @Test
        @DisplayName("join() 호출 시(when) -> 예외를 던진다(then)")
        void join_ShouldThrowException() {
            System.out.println("test join_shouldThrowException");

            User duplicateUser = new User("test1234", "이순신");

            Assertions.assertThrows(IllegalArgumentException.class, () -> userService.join(duplicateUser));
        }
    }
}
