package com.ohgiraffers.injection.chap01.section02.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PaymentsService {
    private final PaymentsInterface paymentsGateway;

    public PaymentsService(PaymentsInterface paymentsGateway) {
        this.paymentsGateway = paymentsGateway;
    }

    public boolean processPayment(String orderId, double amount) {
        System.out.println("결제 시작");
        boolean result = paymentsGateway.processPayment(orderId, amount);

        if (result) {
            System.out.println("결제 성공");
        } else {
            System.out.println("결제 실패");
        }
        return result;
    }
}
