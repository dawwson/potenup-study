package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.event;

import org.ohgiraffers.module01hexagonal_architecture.common.event.BaseDomainEvent;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.vo.MemberId;

public class MemberWithdrawnEvent extends BaseDomainEvent {

    private final MemberId memberId;

    private final String email;

    public MemberWithdrawnEvent(MemberId memberId, String email) {
        this.memberId = memberId;
        this.email = email;
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }
}
