package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.presentation.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(
        @NotBlank(message = "memberEmail은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String memberEmail,

        @NotNull(message = "courseId는 필수입니다.")
        Long courseId
) {
}
