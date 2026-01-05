package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.service;

import org.ohgiraffers.module01hexagonal_architecture.common.event.DomainEventPublisher;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.*;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.command.DecideEnrollmentCommand;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.command.RequestEnrollmentCommand;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.out.CoursePolicyReader;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.out.MemberStatusReader;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.out.PaymentVerifier;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.event.EnrollmentRequestedEvent;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.exception.EnrollmentException;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.model.entity.Enrollment;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.model.entity.EnrollmentStatus;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class EnrollmentCommandService implements RequestEnrollmentUseCase, ApproveEnrollmentUseCase, RejectEnrollmentUseCase, CancelEnrollmentUseCase {

    private final EnrollmentRepository enrollmentRepository;

    private final MemberStatusReader memberStatusReader;
    private final CoursePolicyReader coursePolicyReader;
    private final PaymentVerifier paymentVerifier;
    private final DomainEventPublisher domainEventPublisher;

    public EnrollmentCommandService(EnrollmentRepository enrollmentRepository, MemberStatusReader memberStatusReader, CoursePolicyReader coursePolicyReader, PaymentVerifier paymentVerifier, DomainEventPublisher domainEventPublisher) {
        this.enrollmentRepository = enrollmentRepository;
        this.memberStatusReader = memberStatusReader;
        this.coursePolicyReader = coursePolicyReader;
        this.paymentVerifier = paymentVerifier;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public Long request(RequestEnrollmentCommand command) {

        Objects.requireNonNull(command, "command는 null일 수 없습니다.");

        if (command.memberEmail() == null || command.memberEmail().isBlank()) {
            throw new EnrollmentException("memberEmail은 필수입니다.");
        }

        if (command.courseId() == null) {
            throw new EnrollmentException("courseId는 필수입니다.");
        }

        if (!memberStatusReader.isActive(command.memberEmail())) {
            throw new EnrollmentException("비활성화/탈퇴 회원은 수강신청 불가");
        }

        if (!coursePolicyReader.isCoursePublished(command.courseId())) {
            throw new EnrollmentException("비공개 강좌는 수강신청 할 수 없습니다.");
        }

        if (!coursePolicyReader.hasCapacity(command.courseId())) {
            throw new EnrollmentException("정원이 초과되어 수강신청 할 수 없습니다.");
        }

        enrollmentRepository.findByMemberEmailAndCourseId(command.memberEmail(), command.courseId())
                .ifPresent(enrollment -> {
                    if (enrollment.getStatus() == EnrollmentStatus.REQUESTED || enrollment.getStatus() == EnrollmentStatus.APPROVED) {
                        throw new EnrollmentException("이미 신청(또는 승인)된 수강입니다.");
                    }
                });

        Enrollment enrollment = Enrollment.request(command.memberEmail(), command.courseId());
        Enrollment saved = enrollmentRepository.save(enrollment);

        domainEventPublisher.publish(new EnrollmentRequestedEvent(
                saved.getId(), saved.getMemberEmail(), saved.getCourseId()
        ));

        return saved.getId();
    }

    @Override
    public void approve(DecideEnrollmentCommand command) {

        Objects.requireNonNull(command, "command는 null일 수 없습니다.");

        Enrollment enrollment = enrollmentRepository.findById(command.enrollmentId())
                .orElseThrow(() -> new EnrollmentException("해당 수강 신청이 존재하지 않습니다."));

        if (!memberStatusReader.isActive(enrollment.getMemberEmail())) {
            throw new EnrollmentException("비활성화/탈퇴 회원은 수강신청 불가");
        }

        if (!coursePolicyReader.isCoursePublished(enrollment.getCourseId())) {
            throw new EnrollmentException("비공개 강좌는 수강신청 할 수 없습니다.");
        }

        if (!coursePolicyReader.hasCapacity(enrollment.getCourseId())) {
            throw new EnrollmentException("정원이 초과되어 수강신청 할 수 없습니다.");
        }

        if (coursePolicyReader.isPaidCourse(enrollment.getCourseId()) &&
                !paymentVerifier.isPaymentCompleted(enrollment.getMemberEmail(), enrollment.getCourseId())) {
            throw new EnrollmentException("결제가 완료되지 않아 승인할 수 없습니다.");
        }

        enrollment.approve();

        enrollmentRepository.save(enrollment); // 생략 가능 (흐름 이해를 위해 적음)

        domainEventPublisher.publishAll(enrollment.pullDomainEvents());
    }

    @Override
    public void reject(DecideEnrollmentCommand command) {

        Objects.requireNonNull(command, "command는 null일 수 없습니다.");

        if (command.reason() == null || command.reason().isBlank()) {
            throw new EnrollmentException("거절 사유는 필수입니다.");
        }

        Enrollment enrollment = enrollmentRepository.findById(command.enrollmentId())
                .orElseThrow(() -> new EnrollmentException("해당 수강신청이 존재하지 않습니다."));

        enrollment.reject(command.reason());

        enrollmentRepository.save(enrollment); // 생략 가능 (흐름 이해를 위해 적음)

        domainEventPublisher.publishAll(enrollment.pullDomainEvents());
    }

    @Override
    public void cancel(Long enrollmentId) {

        Objects.requireNonNull(enrollmentId, "enrollment는 null일 수 없습니다.");

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new EnrollmentException("해당 수강 신청이 존재하지 않습니다."));

        enrollment.cancel();

        enrollmentRepository.save(enrollment); // 생략 가능 (흐름 이해를 위해 적음)

        domainEventPublisher.publishAll(enrollment.pullDomainEvents());
    }
}
