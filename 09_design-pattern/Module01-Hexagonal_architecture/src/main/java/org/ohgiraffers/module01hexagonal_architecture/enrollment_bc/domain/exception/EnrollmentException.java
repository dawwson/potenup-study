package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.exception;

import org.ohgiraffers.module01hexagonal_architecture.global.error.DomainException;

public class EnrollmentException extends DomainException {

    public EnrollmentException(String message) {
        super(message);
    }

    public EnrollmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
