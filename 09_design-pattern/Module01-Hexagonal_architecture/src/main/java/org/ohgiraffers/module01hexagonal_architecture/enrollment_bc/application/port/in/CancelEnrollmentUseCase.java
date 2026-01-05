package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in;

public interface CancelEnrollmentUseCase {

    void cancel(Long enrollmentId);
}
