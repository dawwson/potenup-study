package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.handler;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.model.entity.Enrollment;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.repository.EnrollmentRepository;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.event.MemberWithdrawnEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
public class MemberWithdrawnEventHandler {

    private final EnrollmentRepository enrollmentRepository;

    public MemberWithdrawnEventHandler(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    /*
     * Transactional
     * 어떻게 트랜잭션을 실행할지 관리하는 트랜잭션 생성기이다.
     * 클래스가 하나의 트랜잭션 경계 안에서 실행되도록 보장한다.
     *
     * 주요 속성
     * 1. REQUIRES_NEW : 무조건, 항상, 새로운 트랜잭션을 시작한다.
     * 2. REQUIRED : 기존 트랜잭션이 있으면 거기에 참여하고, 없으면 새로 시작한다.
     * 3. supports : 기존 트랜잭션이 있으면 참여하고, 없으면 그냥 트랜잭션 없이 실행한다.
     * 4. NOT_SUPPORTED : 기존 트랜잭션이 있으면 잠시 멈추고, 트랜잭션 없이 실행한다.
     */
    @Transactional
    /*
     * TransactionalEventListener
     * - 이벤트 리스너가 트랜잭션의 어느 시점에 동작할지 결정하는 역할이다.
     * 1. AFTER_COMMIT (기본값) : 이벤트를 발행한 부모의 트랜잭션이 성공적으로 커밋되면 그 후에 실행
     * 2. IN_TRANSACTION : 부모의 트랜잭션이 아직 진행 중일 때 실행된다. (위험할 수 있음)
     * 3. AFTER_ROLLBACK : 부모 트랜잭션이 롤백되면 실행된다.
     * 4. AFTER_COMPLETION : 커밋이 되든 롤백되든 완료되면 실행한다.
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onMemberWithdrawn(MemberWithdrawnEvent event) {

        String email = event.getEmail();

        List<Enrollment> enrollments = enrollmentRepository.findAllByMemberEmail(email);

        if (enrollments.isEmpty()) {
            return;
        }

        enrollments.forEach(Enrollment::cancelByMemberWithdraw);
    }

}
