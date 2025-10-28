package com.ohgiraffers.injection.chap01.section03.strategy.setter;

import com.ohgiraffers.injection.chap01.section03.service.PaymentsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * [Setter 주입]
 * - @Autowired를 Setter 메서드에 적용하여 의존성 주입
 * - 장점 :
 *   - 선택적 의존성 : 주입이 필수가 아님. 기본값 설정 가능
 *   - 런타임 변경 가능 : 의존성을 동적으로 교체
 * - 단점 :
 *   1. 불변성 보장 불가
 *   2. 주입 누락 위험
 *   3. 의존성 명확성 부족
 */
@Service
public class PaymentsServiceSetter {
    private PaymentsInterface paymentsGateway;

    @Autowired
    public void setPaymentsGateway(PaymentsInterface paymentsGateway) {
        this.paymentsGateway = paymentsGateway;
    }

    public PaymentsServiceSetter(PaymentsInterface paymentsGateway) {
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
