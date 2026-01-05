package org.ohgiraffers.module01hexagonal_architecture.member_bc.infrastructure.persistence;

import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.entity.Member;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.vo.MemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaMemberRepository extends JpaRepository<Member, MemberId> {

    Optional<Member> findByProfileEmail(String email) {

    }
}
