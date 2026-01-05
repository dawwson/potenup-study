package org.ohgiraffers.module01hexagonal_architecture.member_bc.infrastructure.persistence;

import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.entity.Member;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.vo.MemberId;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.repository.MemberRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MemberPersistenceAdapter implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    public MemberPersistenceAdapter(JpaMemberRepository jpaMemberRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
    }

    @Override
    public Member save(Member member) {
        return jpaMemberRepository.save(member);
    }

    @Override
    public Optional<Member> findById(MemberId id) {
        return jpaMemberRepository.findById(id);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return jpaMemberRepository.findByProfileEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaMemberRepository.ex;
    }

    @Override
    public void delete(Member member) {

    }
}
