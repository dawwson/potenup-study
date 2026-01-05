package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.entity;

/*
 * 1. 애그리게이트 루트
 * - DDD(도메인 주도 설계)의 핵심 패턴으로, 관련된 도메인 객체들(entity, vo)를
 *   하나의 경계로 묶어 관리하는 대표 엔티티이다.
 * - 이 경계 내의 데이터 일관성(불변식)을 책임지는 유일한 객체이다.
 *
 * 2. member 애그리게이트의 책임
 * - 역할 : 시스템 내 회원을 대표하며, 회원의 생명주기(가입, 수정, 탈퇴)를 관리한다.
 * - 구성 : memberId, profile, memberStatus 객체를 포함하여 하나의 회원이라는 개념을 완성한다.
 *
 * 3. 헥사고날 아키텍처에서 위치
 * - domain.model.entity 패키지에 위치하며, 아키텍처의 가장 안쪽에 해당한다.
 * - 순수성 : 클래스는 JPA나 Spring 같은 외부 기술을 전혀 알지 못해야 한다.
 *   (단, JPA @Entity와 같은 어노테이션은 편의를 위한 타협점으로 사용하기도 한다.)
 *
 * 4. AggregateRoot 추상 클래스 상속
 * - common.model.AggregateRoot를 상속하여 도메인 이벤트를 발행하고 수집하는 기능을 공통으로 사용한다.
 *
 * 불변식
 * - memberId, profile, status는 null일 수 없다.
 * - 프로필 수정 : "Active" 상태의 회원만 가능하다.
 * - 상태 전이 : memberStatus Enum에 정의된 규칙만 따라야 한다.
 * - 탈퇴 : delete 상태가 아닌 회원만 가능하다.
 */

import jakarta.persistence.*;
import org.ohgiraffers.module01hexagonal_architecture.common.model.AggregateRoot;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.event.MemberWithdrawnEvent;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception.MemberNotActiveException;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception.MemberNotFoundException;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception.MemberStatusTransitionNotAllowedException;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.vo.MemberId;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.vo.MemberStatus;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.vo.Profile;

import java.util.Objects;

@Entity
@Table(
        name = "members",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_MEMBER_EMAIL",
                        columnNames = { "member_email" }
                )
        }
)
public class Member extends AggregateRoot {

    @EmbeddedId
    @AttributeOverride(
            name = "value",
            column = @Column(
                        name = "member_id",
                        nullable = false,
                        updatable = false,
                        columnDefinition = "BINARY(16) COMMENT '회원 식별자(UUID)"
                    )
    )
    private MemberId id;

    @Embedded
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            columnDefinition = "VARCHAR(20) COMMENT '회원 상태(ACTIVE/INACTIVE/DELETED'"
    )
    private MemberStatus status;

    // JPA에서 사용하기 때문에 필요
    protected Member() {}

    private Member(MemberId id, Profile profile, MemberStatus status) {
        this.id = id;
        this.profile = profile;
        this.status = status;

        validateInvariant();
    }

    /*
     * 정적 팩토리 메서드
     * - new Member 대신 메서드를 사용하는 이유
     * 1. 도메인 언어 반영 : new Member 보다 Member.register가
     *    비즈니스 의미를 훨씬 명확하게 전달하기 때문
     *
     * 2. 생성 시 규칙 강제 : 신규 회원은 무조건 Active 상태여야 한다는
     *    도메인 규칙을 이 메서드 안에서 강제할 수 있다.
     */
    public static Member register(Profile profile) {
        return new Member(MemberId.newId(), profile, MemberStatus.ACTIVE);
    }

    public void updateProfile(Profile profile) {
        ensureActive();
        this.profile = Objects.requireNonNull(profile, "프로필은 null이 될 수 없습니다.");
    }

    public void changeStatus(MemberStatus targetStatus) {
        Objects.requireNonNull(targetStatus, "targetStatus는 null이 될 수 없습니다.");

        if (!this.status.canTransitionTo(targetStatus)) {
            throw new MemberStatusTransitionNotAllowedException(this.status.name(), targetStatus.name());
        }

        this.status = targetStatus;
    }

    /*
     * 탈퇴는 단순히 상태 변경이 아닌 다른 BC에 영향을 주는 중요한 이벤트이다.
     */
    public void withdraw() {
        if (this.status == MemberStatus.DELETED) {
            //throw new MemberNotFoundException();
        }

        this.status = MemberStatus.DELETED;

        registerEvent(new MemberWithdrawnEvent(this.id, this.profile.getEmail()));
    }

    // -- 가드 메서드 정의 --
    private void ensureActive() {
        if (this.status != MemberStatus.ACTIVE) {
            //throw new MemberNotActiveException(this.status.name(), MemberStatus.ACTIVE);
        }
    }

    private void validateInvariant() {
        if (id == null) {
            throw new IllegalArgumentException("MemberId는 null일 수 없습니다.");
        }

        if (profile == null) {
            throw new IllegalArgumentException("profile은 null일 수 없습니다.");
        }

        if (status == null) {
            throw new IllegalArgumentException("memberStatus는 null일 수 없습니다.");
        }
    }

    public MemberId getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public MemberStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : Objects.hash(super.hashCode());
    }
}
