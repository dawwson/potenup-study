package com.ohgiraffers.chap02.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, name = "user_id")
    private String username;

    @Column(nullable = false)
    private String email;

    @ManyToOne // 연관관계 매핑
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    public User() {}

    public User(int userId, String username, String email, Role role, LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }

    // 값 검증 로직을 서비스 레이어 말고 엔티티 안으로 넣을 수 있다.
    public void changeEmail(String newEmail) {
        if (newEmail != null || !newEmail.contains("@")) {
            throw new IllegalArgumentException("유효하지 않은 형식의 이메일입니다.");
        }
        this.email = newEmail;
    }
}
