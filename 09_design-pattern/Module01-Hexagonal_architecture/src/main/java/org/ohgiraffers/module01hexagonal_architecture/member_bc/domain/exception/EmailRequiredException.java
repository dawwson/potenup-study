package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception;

/*
 * 이메일 누락 처리
 */
public class EmailRequiredException extends MemberException {

    public EmailRequiredException(String message) {
        super("MEMBER.EMAIL_REQUIRED", "이메일은 필수입니다.");
    }
}
