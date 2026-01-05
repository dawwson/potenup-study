package org.ohgiraffers.security.auth.service;

import org.ohgiraffers.security.auth.jwt.JwtTokenProvider;
import org.ohgiraffers.security.auth.model.LoginRequestDTO;
import org.ohgiraffers.security.auth.model.SignupRequestDTO;
import org.ohgiraffers.security.auth.model.TokenResponseDTO;
import org.ohgiraffers.security.auth.model.UserResponseDTO;
import org.ohgiraffers.security.token.model.RefreshToken;
import org.ohgiraffers.security.token.repository.RefreshTokenRepository;
import org.ohgiraffers.security.user.model.User;
import org.ohgiraffers.security.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword())
        );

        String accessToken = jwtTokenProvider.createAccessToken(authentication);
        String newRefreshToken = jwtTokenProvider.createRefreshToken();

        refreshTokenRepository.findByUsername(authentication.getName()).ifPresent(refreshTokenRepository::delete);
        refreshTokenRepository.findAll().forEach(System.out::println);

        RefreshToken refreshToken = new RefreshToken(
                authentication.getName(),
                newRefreshToken,
                Instant.now().plusMillis(jwtTokenProvider.getRefreshTokenValidityInMilliseconds())
        );

        refreshTokenRepository.save(refreshToken);

        return new TokenResponseDTO(accessToken, newRefreshToken);
    }

    public UserResponseDTO register(SignupRequestDTO requestDTO) {
        if (userRepository.existsByUsername(requestDTO.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = User.builder()
                .username(requestDTO.getUsername())
                .password(requestDTO.getPassword())
                .email(requestDTO.getEmail())
                .roles(requestDTO.getRoles())
                .build();

        User savedUser = userRepository.save(user);
        return new UserResponseDTO(savedUser);
    }
}
