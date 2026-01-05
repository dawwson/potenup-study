package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.out;

public interface PaymentVerifier {

    boolean isPaymentCompleted(String memberEmail, Long courseId);
}
