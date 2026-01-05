package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception;

/*
 * 존재하지 않는 회원 조회 오류
 */
public class MemberNotFoundException extends MemberException {

    public MemberNotFoundException(String message) {
        super("MEMBER.NOT_FOUND", "회원을 찾을 수 없습니다.");
    }
}
