package org.ohgiraffers.security.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.ohgiraffers.security.exception.ExpiredJwtCustomException;
import org.ohgiraffers.security.exception.InvalidJwtCustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    /*
     * key
     * - JWT의 서명을 생성하고 검증하는 데 사용되는 핵심 암호화 키 객체이다.
     *   사용자가 입력한 평문을 암호화 알고리즘에 맞춰 byte[]로 저장하게 된다.
     * - 이 key의 기밀성을 유지하는 jwt 기반 인증 시스템의 보안에서 가장 중요한데
     *   특히, hmac과 같은 대칭키 알고리즘에서 이 키가 외부에 노출될 경우, 공격자는
     *   유효한 토큰을 임의로 생성하거나 기존 토큰을 위변조할 수 있는 심각한 보안 위험이 발생한다.
     * - 따라서 이 키는 환경 변수, 외부 설정 파일, 또는 보안 관리 시승템 등을 통해
     *   매우 안전하게 관리되어야 하며, 코드에 직접 하드코딩 하는 것은 피해야 한다.
     */
    private SecretKey key;

    @Value("${jwt.access-token-validity-milliseconds}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${refresh-token-validity-milliseconds}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    @Value("${jwt.secret}")
    private String secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /*
     * 요청 헤더에서 JWT 토큰 추출
     */
    public String resolveToken(HttpServletRequest httpServletRequest) {

        String bearerToken = httpServletRequest.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /*
     * JWT 토큰 유효성 검사
     */
    public boolean validateToken(String token) throws ExpiredJwtCustomException {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtCustomException("Expired JWT Token : " + e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtCustomException("Invalid JWT Token : " + e.getMessage());
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = extractClaims(token, false);

        String username = claims.getSubject();
        String roleString = claims.get("role", String.class);

        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

        if (roleString != null && !roleString.trim().isEmpty()) {
            authorities = Arrays.stream(roleString.split(","))
                    .map(String::trim)
                    .filter(role -> !role.trim().isEmpty())
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        UserDetails userDetails = User.builder()
                .username(username)
                .password("")
                .authorities(authorities)
                .build();

        return new UsernamePasswordAuthenticationToken((userDetails, token, authorities.getAuthorisies));

        return new UsernamePasswordAuthenticationToken(username, null, roleString);
    }

    /*
     * Claims 추출
     * - 만료 시 예외를 발생시키지 않고 내부에서 클레임을 사용
     */
    private Claims extractClaims(String token, boolean allowExpired) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            if (allowExpired) {
                e.getClaims();
            }
            throw new InvalidJwtCustomException("Expired JWT Token : " + e.getMessage());
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtCustomException("Invalid JWT Token : " + e.getMessage());
        }
    }

    /*
     * 헤더에서 X-Refresh-Token의 토큰을 꺼내옴
     */
    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("X-Refresh-Token");
    }

    public String createAccessToken(Authentication authentication) {
        String username = authentication.getName();

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .subject(username)
                .claim("roles", authorities)
                .issuedAt(new java.util.Date())
                .expiration(new java.util.Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    /*
     * RefreshToken 발급
     */
    public String createRefreshToken() {
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    /*
     *
     */
    public long getRefreshTokenValidityInMilliseconds() {
        return REFRESH_TOKEN_EXPIRE_TIME;
    }
}
