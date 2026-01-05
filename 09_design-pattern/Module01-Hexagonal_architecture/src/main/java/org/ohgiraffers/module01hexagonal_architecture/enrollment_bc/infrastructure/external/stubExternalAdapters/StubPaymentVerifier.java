package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.infrastructure.external.stubExternalAdapters;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.out.PaymentVerifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class StubPaymentVerifier implements PaymentVerifier {

    @Override
    public boolean isPaymentCompleted(String memberEmail, Long courseId) {
        return memberEmail.contains("paid");
    }
}
