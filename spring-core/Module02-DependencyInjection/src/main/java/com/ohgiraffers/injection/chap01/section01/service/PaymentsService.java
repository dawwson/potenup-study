package com.ohgiraffers.injection.chap01.section01.service;

import org.springframework.stereotype.Service;

/*
 * [@Service]
 * - @Component의 특수화된 형태로, 서비스 계층에 속하는 bean을 나타낸다.
 *   - 역할 : 비즈니스 로직을 처리하는 클래스에 사용되며, spring이 이름을 Bean으로 등록
 *   - Bean 이름 : paymentsService
 *   - 예시 : 비즈니스의 핵심 로직을 담당
 * - 컴포넌트 스캔으로 자동으로 등록되며, 생명주기(생성 -> 사용 -> 소멸) 관리됨
 */
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
