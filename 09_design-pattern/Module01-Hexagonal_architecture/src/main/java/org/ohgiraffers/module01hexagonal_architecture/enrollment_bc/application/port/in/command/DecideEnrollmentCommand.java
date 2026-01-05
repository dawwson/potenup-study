package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.command;

public record DecideEnrollmentCommand(
        Long enrollmentId,
        String reason
) {
}
