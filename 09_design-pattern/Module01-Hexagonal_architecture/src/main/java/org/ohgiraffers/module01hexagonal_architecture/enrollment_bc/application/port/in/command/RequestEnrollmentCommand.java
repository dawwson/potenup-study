package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.command;

public record RequestEnrollmentCommand(
        String memberEmail,
        Long courseId
) {
}
