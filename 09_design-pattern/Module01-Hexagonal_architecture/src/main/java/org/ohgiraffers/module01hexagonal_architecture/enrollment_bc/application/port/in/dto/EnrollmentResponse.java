package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.dto;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.model.entity.Enrollment;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.model.entity.EnrollmentStatus;

import java.time.OffsetDateTime;

public record EnrollmentResponse(
        Long id,
        String memberEmail,
        Long courseId,
        EnrollmentStatus status,
        String rejectReason,
        OffsetDateTime requestedAt,
        OffsetDateTime decidedAt,
        OffsetDateTime canceledAt
) {
    public static EnrollmentResponse from(Enrollment e) {

        return new EnrollmentResponse(
                e.getId(),
                e.getMemberEmail(),
                e.getCourseId(),
                e.getStatus(),
                e.getRejectReason(),
                e.getRequestedAt(),
                e.getDecidedAt(),
                e.getCanceledAt()
        );
    }
}
