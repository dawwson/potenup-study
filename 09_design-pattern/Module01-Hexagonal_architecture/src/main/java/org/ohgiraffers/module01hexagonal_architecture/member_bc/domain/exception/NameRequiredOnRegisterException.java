package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception;

public class NameRequiredOnRegisterException extends MemberException {

    public NameRequiredOnRegisterException() {
        super("MEMBER.NAME_REQUIRED_ON_REGISTER", "신규 등록 시 이름은 필수입니다.");
    }
}
