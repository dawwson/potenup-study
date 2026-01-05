package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception;

/*
 * 회원 상태 전환 불가
 */
public class MemberStatusTransitionNotAllowedException extends MemberException {

    public MemberStatusTransitionNotAllowedException(String from, String to) {
        super(
                "MEMBER.STATUS_TRANSITION_NOT_ALLOWED",
                String.format("현재 상태(%s)에서 %s로 전환할 수 없습니다., from, to")
        );
    }
}
