package org.ohgiraffers.module01hexagonal_architecture.member_bc.presentation.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/*
 * Presentation DTO의 역할
 * - inbound Adapter에서 사용하는 데이터 전송 객체이다.
 * - 이 객체는 외부 API 명세(spec) 또는 화면과 1:1로 대응이 된다.
 */
public record MemberUpsertRequest (
        String name,

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")

        String email,

        String phone
) {
}
