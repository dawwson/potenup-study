package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.infrastructure.external.member;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.dto.MemberStatusDTO;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.out.MemberStatusReader;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.service.MemberQueryService;
import org.springframework.stereotype.Component;

@Component
public class MemberStatusReaderAdapter implements MemberStatusReader {

    private final MemberQueryService memberQueryService;

    public MemberStatusReaderAdapter(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    @Override
    public boolean isActive(String memberEmail) {
        return memberQueryService.getMemberStatus(memberEmail)
                .map(this::translate)
                .orElse(false);
    }

    private boolean translate(MemberStatusDTO memberStatusDTO) {
        return "ACTIVE".equals(memberStatusDTO.status());
    }
}
