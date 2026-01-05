package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.command.RequestEnrollmentCommand;

public interface RequestEnrollmentUseCase {

    Long request(RequestEnrollmentCommand command);
}
