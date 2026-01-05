package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception;

/*
 * 이메일 형식을 위반
 */
public class InvalidEmailFormatException extends MemberException {

    public InvalidEmailFormatException(String message) {
        super("MEMBER.INVALID_EMAIL_FORMAT", "이메일 형식이 올바르지 않습니다.");
    }
}
