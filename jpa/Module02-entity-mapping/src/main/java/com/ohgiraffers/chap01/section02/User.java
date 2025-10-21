package com.ohgiraffers.chap01.section02;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * [문제 상황]
 * 만약 회원의 역할(Role)을 @Column private String role 처럼 단순한 문자열로 관리한다면 어떤 문제가 생길까?
 * - 오타 발생 : "ADMIN", "admin", "ADMINISTRATOR" 등 다양한 값이 저장되어 데이터 일관성이 깨진다.
 * - 확장성 부족 : 역할에 따른 권한 레벨 같은 추가 정보를 담기 어려웠다.
 *
 * [해결방법]
 * 개발자의 실수를 방지하고 역할이라는 개념을 타입 수준에서 안정적으로 다루기 위해 Enum을 사용한다.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String password;

    /**
     * 날짜 및 시간 타입 매핑
     * > LocalDate : 날짜만 저장(yyyy-mm-dd)
     * > LocalDateTime : 날짜 + 시간 저장 (yyyy-mm-dd HH:mm:ss)
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * @Enumerated - enum 타입 필드 매핑
     * EnumType.String을 써야만 하는 이유
     * - ORDINAL (기본값) : enum의 순서(0, 1, 2, 3, 4)를 DB에 저장한다.
     * - 치명적인 단점 : 나중에 Enum의 순서가 변경되면 기존의 모든 데이터가 전부 깨진다.
     *
     * - String : Enum의 이름을 DB에 저장한다.
     * - 강력한 장점 : 순서가 바뀌거나 중간에 새로운 역할이 추가되어도 기존 데이터에 전혀 영향을 주지 않는다.
     *   데이터베이스만 봐도 무슨 역할인지 명확하게 알 수 있다.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role_id", nullable = false)
    private Role role;
}
