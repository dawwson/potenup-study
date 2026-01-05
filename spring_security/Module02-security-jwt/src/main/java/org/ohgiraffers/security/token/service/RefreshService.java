package org.ohgiraffers.security.token.service;

import org.ohgiraffers.security.auth.jwt.JwtTokenProvider;
import org.ohgiraffers.security.exception.ExpiredJwtCustomException;
import org.ohgiraffers.security.exception.InvalidJwtCustomException;
import org.ohgiraffers.security.exception.InvalidRefreshTokenException;
import org.ohgiraffers.security.token.model.RefreshToken;
import org.ohgiraffers.security.token.repository.RefreshTokenRepository;
import org.ohgiraffers.security.user.model.User;
import org.ohgiraffers.security.user.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefreshService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshService(JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String refreshAccessToken(String oldRefreshTokenValue) {

        try {
            jwtTokenProvider.validateToken(oldRefreshTokenValue);
        } catch (ExpiredJwtCustomException e) {
            // refresh token이 유효하지 않을 때
            refreshTokenRepository.findByToken(oldRefreshTokenValue).ifPresent(refreshTokenRepository::delete);
            throw new InvalidRefreshTokenException("Refresh token has expired.", e);
        } catch (InvalidJwtCustomException e) {
            // refresh token이 변조되었을 때
            refreshTokenRepository.findByToken(oldRefreshTokenValue).ifPresent(refreshTokenRepository::delete);
            throw new InvalidRefreshTokenException("Invalid refresh token.", e);
        }

        RefreshToken oldRefreshToken = refreshTokenRepository.findByToken(oldRefreshTokenValue)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token not found."));

        if (oldRefreshToken.isExpired()) {
            refreshTokenRepository.delete(oldRefreshToken);
            throw new InvalidRefreshTokenException("Refresh token has expired.");
        }

        String username = oldRefreshToken.getUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidRefreshTokenException("Username not found for the given refresh token."));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(user.getUsername(), "", user.getAuthorities()),
                null,
                user.getAuthorities()
        );
        return jwtTokenProvider.createAccessToken(authentication);
    }
}
