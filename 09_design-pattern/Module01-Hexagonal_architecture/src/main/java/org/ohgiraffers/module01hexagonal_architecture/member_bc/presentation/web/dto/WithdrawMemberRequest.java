package org.ohgiraffers.module01hexagonal_architecture.member_bc.presentation.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record WithdrawMemberRequest(
        @Email
        @NotBlank
        String email
) {

}
