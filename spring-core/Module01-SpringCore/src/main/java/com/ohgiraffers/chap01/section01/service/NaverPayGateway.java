package com.ohgiraffers.chap01.section01.service;

public class NaverPayGateway {
    public boolean processPayment(String orderId, double amount) {
        System.out.println("네이버페이로 결제를 진행합니다. 주문 ID : " + orderId + ", 금액 : " + amount);
        return true;
    }
}
