package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.event;

import org.ohgiraffers.module01hexagonal_architecture.common.event.BaseDomainEvent;

public class EnrollmentCanceledByMemberWithdrawEvent extends BaseDomainEvent {

    private final Long enrollmentId;
    private final String memberEmail;
    private final Long courseId;

    public EnrollmentCanceledByMemberWithdrawEvent(Long enrollmentId, String memberEmail, Long courseId) {
        this.enrollmentId = enrollmentId;
        this.memberEmail = memberEmail;
        this.courseId = courseId;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public Long getCourseId() {
        return courseId;
    }
}
