package org.ohgiraffers.security.token.model;

import jakarta.persistence.*;

import java.time.Instant;

/*
 * 필요성 :
 * - AccessToken은 일반적으로 짧은 수명을 가진다. -> 유출 시 피해를 최소화 하기 위함
 * - 사용자가 계속 로그인 상태를 유지하려면, 만료된 AccessToken을 갱신할 수 있는 방법이 필요하다.
 * - RefreshToken은 상대적으로 긴 수명을 가지며, AccessToken이 만료되었을 때
 */
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 1024)
    private String token;

    @Column(nullable = false)
    private Instant expiresAt;

    public RefreshToken() {}

    public RefreshToken(String username, String token, Instant expiresAt) {
        this.username = username;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiresAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", expiresAt=" + expiresAt +
                '}';
    }
}
