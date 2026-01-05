package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.dto;

public record MemberStatusDTO(
        String email,
        String status
) {
}
