package org.ohgiraffers.security.auth.controller;

import jakarta.validation.Valid;
import org.ohgiraffers.security.auth.model.LoginRequestDTO;
import org.ohgiraffers.security.auth.model.SignupRequestDTO;
import org.ohgiraffers.security.auth.model.TokenResponseDTO;
import org.ohgiraffers.security.auth.model.UserResponseDTO;
import org.ohgiraffers.security.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        TokenResponseDTO tokenResponseDTO = authService.login(loginRequestDTO);
        return ResponseEntity.ok(tokenResponseDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@RequestBody @Valid SignupRequestDTO signupRequestDTO) {
        UserResponseDTO userResponseDTO = authService.register(signupRequestDTO);

        if (userResponseDTO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(userResponseDTO);
    }
}
