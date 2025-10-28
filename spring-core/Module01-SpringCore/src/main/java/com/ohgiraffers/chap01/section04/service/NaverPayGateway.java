package com.ohgiraffers.chap01.section04.service;

public class NaverPayGateway implements PaymentsInterface {
    @Override
    public boolean processPayment(String orderId, double amount) {
        System.out.println("네이버 페이 결제 시작! 주문번호 : " + orderId + ", 주문 금액 : " + amount);
        return true; // 무조건 성공한다고 가정
    }
}
