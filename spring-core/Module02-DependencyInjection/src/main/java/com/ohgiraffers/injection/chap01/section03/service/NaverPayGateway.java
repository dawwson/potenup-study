package com.ohgiraffers.injection.chap01.section03.service;

import org.springframework.stereotype.Component;

@Component("naverPay03")
public class NaverPayGateway implements PaymentsInterface {
    @Override
    public boolean processPayment(String orderId, double amount) {
        System.out.println("네이버 페이로 결제를 진행합니다... 주문 ID" + orderId + ", 금액 : " + amount);
        return true;
    }
}
