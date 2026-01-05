package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.service;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.dto.MemberStatusDTO;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberQueryService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Optional<MemberStatusDTO> getMemberStatus(String email) {
        return memberRepository.findByEmail(email)
                .map(member -> new MemberStatusDTO(
                        member.getProfile().getEmail(),
                        member.getStatus().name()
                ));
    }
}
