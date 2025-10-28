package com.ohgiraffers.injection.chap01.section03.strategy.field;

import com.ohgiraffers.injection.chap01.section03.service.PaymentsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * 필드 주입(field injection)
 * - @Autowired를 필드에 직접 적용하여 의존성 주입
 * - 장점 :
 *   1. 코드 간결 : 생성자나 setter 메서드 없이 주입 가능
 *   2. 빠른 구현 : 프로토타입 개발 시 유용
 * - 단점 :
 *   1. 불변성 보장 불가 : final 사용 불가, 런타임에 변경 가능
 *   2. 테스트 어려움 : 의존성을 외부에서 주입하기 어려움 (리플렉션 필요)
 *   3. 주입 시점 불명확 : 객체 생성 후 언제 주입되는지 알기 어려움
 *   4. 의존성 숨김 : 필수 의존성이 코드 상으로 드러나지 않음
 */
@Service
public class PaymentsServiceField {
    @Autowired
    private PaymentsInterface paymentsGateway;  // final 못 붙임

    // 생성자 삭제
    //public PaymentsServiceField(PaymentsInterface paymentsGateway) {
    //    this.paymentsGateway = paymentsGateway;
    //}

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
