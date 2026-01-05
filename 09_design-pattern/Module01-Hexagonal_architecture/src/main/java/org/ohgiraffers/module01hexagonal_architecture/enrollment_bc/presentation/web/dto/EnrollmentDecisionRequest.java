package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.presentation.web.dto;

import jakarta.validation.constraints.NotBlank;

public record EnrollmentDecisionRequest(
        @NotBlank(message = "거절 사유는 필수입니다.")
        String reason
) {
}
