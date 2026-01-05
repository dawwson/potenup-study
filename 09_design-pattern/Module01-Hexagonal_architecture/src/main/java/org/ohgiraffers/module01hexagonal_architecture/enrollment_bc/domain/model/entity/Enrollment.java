package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.model.entity;

import jakarta.persistence.*;
import org.ohgiraffers.module01hexagonal_architecture.common.model.AggregateRoot;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.event.EnrollmentApprovedEvent;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.event.EnrollmentCanceledByMemberWithdrawEvent;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.event.EnrollmentRejectedEvent;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.event.EnrollmentRequestedEvent;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.exception.EnrollmentException;

import java.time.OffsetDateTime;
import java.util.Objects;

/*
 * Enrollment의 책임
 * 1. member와 마찬가지로, enrollment는 자신의 생명 주기와 규칙을 스스로 책임지는 도메인 객체이다.
 *   1) 승인은 Requested 상태에서만 가능하다.
 *   2) 거절은 requested 상태에서만 가능하다.
 *   3) 취소는 requested 또는 approved 상태에서만 가능하다.
 *   4) 거절 시에는 사유가 필수이다.
 *   5) 중복 한 명의 회원은 동일한 강좌에 중복으로 수강신청 할 수 없다.
 *
 * 2. Application service와 Domain Aggregate (책임 비교)
 * - EnrollmentCommandService(애플리케이션)은 외부 정책을 검증한다.
 *
 * - enrollment는 내부 상태를 검증한다.
 */
@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = {
                @UniqueConstraint(
                        // 5번 책임 충복 (DB 레벨 방어선이기 때문에 비즈니스 로직 레벨에서도 막아줘야 함)
                        name = "uk_member_email_and_course_id",
                        columnNames = { "member_email", "course_id" }
                )
        }
)
public class Enrollment extends AggregateRoot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "bigint comment '수강신청 pk'")
    private Long id;

    @Column(name = "member_email", nullable = false, length = 255, columnDefinition = "varchar(255) comment '수강자 이메일'")
    private String memberEmail;

    @Column(name = "course_id", nullable = false, columnDefinition = "bigint comment '강좌 ID(약결합 키)'")
    private Long courseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20, columnDefinition = "varchar(20) comment '수강 상태'")
    private EnrollmentStatus status;

    @Column(name = "requested_at", nullable = false, columnDefinition = "timestamp comment '신청 시각'")
    private OffsetDateTime requestedAt;

    @Column(name = "decided_at", columnDefinition = "timestamp comment '승인/거절 결정 시각'")
    private OffsetDateTime decidedAt;

    @Column(name = "canceled_at", columnDefinition = "timestamp comment '취소 시각'")
    private OffsetDateTime canceledAt;

    @Column(name = "reject_reason", length = 500, columnDefinition = "varchar(500) comment '거절 사유'")
    private String rejectReason;

    // NOTE: 기본 생성자 JPA 때문에 필요함
    protected Enrollment() {}

    private Enrollment(String memberEmail, Long courseId) {
        this.memberEmail = requireNonBlank(memberEmail, "memberEmail은 비어 있을 수 없습니다.");
        this.courseId = Objects.requireNonNull(courseId, "courseId는 null일 수 없습니다.");

        this.status = EnrollmentStatus.REQUESTED;
        this.requestedAt = OffsetDateTime.now();

        registerEvent(new EnrollmentRequestedEvent(this.id, this.memberEmail, this.courseId));
    }

    public static Enrollment request(String memberEmail, Long courseId) {
        return new Enrollment(memberEmail, courseId);
    }

    /*
     * 도메인 행위 (승인/거절/취소)
     */

    /*
     * 도메인 행위 승인
     * - Application service에서 외부 정책을 이미 검증한 뒤 호출된다.
     * - 이 메서드는 내부 상태만 검증한다.
     */
    public void approve() {

        ensureStatus(EnrollmentStatus.REQUESTED, "REQUESTED 상태에서만 승인할 수 있습니다.");

        this.status = EnrollmentStatus.APPROVED;
        this.decidedAt = OffsetDateTime.now();
        this.rejectReason = null;

        registerEvent(new EnrollmentApprovedEvent(this.id, this.memberEmail, this.courseId));
    }

    public void reject(String reason) {

        ensureStatus(EnrollmentStatus.REQUESTED, "REQUESTED 상태에서만 거절할 수 있습니다.");

        if (reason == null || rejectReason.isBlank()) {
            throw new EnrollmentException("거절 사유는 필수입니다.");
        }

        this.status = EnrollmentStatus.REJECTED;
        this.decidedAt = OffsetDateTime.now();
        this.rejectReason = reason;

        registerEvent(new EnrollmentRejectedEvent(this.id, this.memberEmail, this.courseId, reason));
    }

    public void cancel() {

        if (!(status == EnrollmentStatus.REQUESTED || status == EnrollmentStatus.APPROVED)) {
            throw new EnrollmentException("Requested/Approved 상태에서만 취소가 가능합니다.");
        }

        this.status = EnrollmentStatus.CANCELLED;
        this.canceledAt = OffsetDateTime.now();

        registerEvent(new EnrollmentCanceledByMemberWithdrawEvent(this.id, this.memberEmail, this.courseId));
    }

    public void cancelByMemberWithdraw() {

        if (!(status == EnrollmentStatus.REQUESTED || status == EnrollmentStatus.APPROVED)) {
            return;
        }

        this.status = EnrollmentStatus.CANCELLED;
        this.canceledAt = OffsetDateTime.now();

        registerEvent(new EnrollmentCanceledByMemberWithdrawEvent(this.id, this.memberEmail, this.courseId));
    }

    private void ensureStatus(EnrollmentStatus expected, String reason) {

        if (this.status != expected) {
            throw new EnrollmentException(reason);
        }
    }

    private static String requireNonBlank(String value, String msg) {

        if (value == null || value.isBlank()) {
            throw new EnrollmentException(msg);
        }

        return value;
    }

    public Long getId() {
        return id;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public Long getCourseId() {
        return courseId;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public OffsetDateTime getRequestedAt() {
        return requestedAt;
    }

    public OffsetDateTime getDecidedAt() {
        return decidedAt;
    }

    public OffsetDateTime getCanceledAt() {
        return canceledAt;
    }

    public String getRejectReason() {
        return rejectReason;
    }
}
