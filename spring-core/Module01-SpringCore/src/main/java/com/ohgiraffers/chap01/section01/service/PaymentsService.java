package com.ohgiraffers.chap01.section01.service;

public class PaymentsService {
    private NaverPayGateway paymentsGateway; // <- 변경지점

    public PaymentsService(NaverPayGateway paymentsGateway) { // <- 변경지점
        this.paymentsGateway = paymentsGateway;
    }

    public boolean processPayment(String orderId, double amount) {
        System.out.println("결제 처리를 시작합니다. 주문 ID : " + orderId + ", 금액 : " + amount);
        boolean paymentResult = paymentsGateway.processPayment(orderId, amount);  // <- 변경지점

        if (paymentResult) {
            System.out.println("결제가 성공적으로 처리되었습니다.");
        } else {
            System.out.println("결제 처리 중 오류가 발생하였습니다.");
        }
        return paymentResult;
    }
}
