package com.ohgiraffers.chap01.section01;

import com.ohgiraffers.chap01.section01.service.KakaoPayGateway;
import com.ohgiraffers.chap01.section01.service.NaverPayGateway;
import com.ohgiraffers.chap01.section01.service.PaymentsService;

public class Application {
    public static void main(String[] args) {
        System.out.println("=== 스플이 없이 객체 생성 및 사용 ===");

        PaymentsService paymentsService = new PaymentsService(new NaverPayGateway());

        String orderId = "Order-1234";
        double amount = 100.00;

        boolean result = paymentsService.processPayment(orderId, amount);
        System.out.println("결제 결과 : " + (result ? "성공" : "실패"));
        System.out.println("==============================");
        /*
         * [개방-폐쇄 원칙]
         * 기존 코드를 건드리지 않고도 기능을 확장할 수 있는 구조를 만들어야 한다.
         *
         * KakaoPay -> NaverPay로 변경 시 발생한 문제점
         * 1. Service의 생성자 변경
         * 2. Service의 필드 변경
         * 3. processPayment() 안의 메서드 변경
         */
    }
}
