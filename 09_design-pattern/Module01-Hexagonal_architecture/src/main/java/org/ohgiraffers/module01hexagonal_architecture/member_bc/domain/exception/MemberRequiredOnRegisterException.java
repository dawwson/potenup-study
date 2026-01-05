package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception;

/*
 * 회원가입 시 이름 누락
 */
public class MemberRequiredOnRegisterException extends MemberException {

    public MemberRequiredOnRegisterException() {
        super("MEMBER.NAME_REQUIRED_ON_REGISTER", "신규 등록 시 이름은 필수입니다.");
    }
}
