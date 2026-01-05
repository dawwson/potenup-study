package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.event;

import org.ohgiraffers.module01hexagonal_architecture.common.event.BaseDomainEvent;

public class EnrollmentRejectedEvent extends BaseDomainEvent {

    private final Long enrollmentId;
    private final String memberEmail;
    private final Long courseId;
    private final String reason;

    public EnrollmentRejectedEvent(Long enrollmentId, String memberEmail, Long courseId, String reason) {
        this.enrollmentId = enrollmentId;
        this.memberEmail = memberEmail;
        this.courseId = courseId;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }
}
