package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.repository;

import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.entity.Member;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.vo.MemberId;

import java.util.Optional;

// 이번 모듈에서는 브릿지 패턴을 사용

/*
 * 도메인 레포지토리 포트
 * - member 애그리게이트의 영속성을 처리하기 위한 추상 인터페이스이다.
 * - 헥사고날 아키텍처에서는 아웃바운드 포트이다.
 *
 * 도메인 순수성
 * - 이 인터페이스는 도메인 계층에 위치한다.
 * - member 애그리게이트가 어떻게 저장되는지 (예: JPA, Mybatis) 전혀 알지 못하며,
 *   무엇을 저장하고 조회하는지만 정의한다.
 * - 반환 타입과 파라미터는 순수 도메인 객체를 사용해야 한다.
 *   (JPA의 pageable과 같은 기술이 여기에 존재해서는 안 된다.)
 */
public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(MemberId id);

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    void delete(Member member);
}
