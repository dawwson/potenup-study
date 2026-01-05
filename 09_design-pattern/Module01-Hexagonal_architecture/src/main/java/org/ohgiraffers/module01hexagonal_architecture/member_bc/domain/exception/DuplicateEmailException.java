package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception;

/*
 * 이메일 중복 처리
 */
public class DuplicateEmailException extends MemberException {

    public DuplicateEmailException(String message) {
        super("MEMBER.DUPLICATE_EMAIL", "이미 사용 중인 이메일입니다.");
    }
}
