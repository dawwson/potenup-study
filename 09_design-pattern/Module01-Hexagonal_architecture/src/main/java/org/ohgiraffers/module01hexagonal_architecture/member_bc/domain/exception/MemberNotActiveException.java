package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception;

/*
 * 비활성화 회원은 프로필 수정이 불가능하다.
 */
public class MemberNotActiveException extends MemberException {

    public MemberNotActiveException(String message) {
        // TODO: 에러 코드는 enum으로 분리하는 것이 좋음
        super("MEMBER.NOT_ACTIVE", "비활성화 상태에서는 프로필 수정을 할 수 없습니다.");
    }
}
