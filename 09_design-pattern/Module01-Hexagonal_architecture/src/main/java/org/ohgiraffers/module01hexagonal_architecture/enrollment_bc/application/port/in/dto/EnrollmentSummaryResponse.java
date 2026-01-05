package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.dto;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.model.entity.Enrollment;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.model.entity.EnrollmentStatus;

public record EnrollmentSummaryResponse(
        Long id,
        Long courseId,
        EnrollmentStatus status
) {
    public static EnrollmentSummaryResponse from(Enrollment e) {

        return new EnrollmentSummaryResponse(
                e.getId(),
                e.getCourseId(),
                e.getStatus()
        );
    }
}
