package com.ohgiraffers.injection.chap01.section03;

import com.ohgiraffers.injection.chap01.section03.service.NaverPayGateway;
import com.ohgiraffers.injection.chap01.section03.strategy.constructor.PaymentsServiceConstructor;
import com.ohgiraffers.injection.chap01.section03.strategy.field.PaymentsServiceField;

public class Application {
    public static void main(String[] args) {
        PaymentsServiceConstructor paymentsServiceConstructor = new PaymentsServiceConstructor(new NaverPayGateway());
        paymentsServiceConstructor.processPayment("ORDER-123", 1000);

        PaymentsServiceField paymentsServiceField = new PaymentsServiceField(new NaverPayGateway());
        paymentsServiceField.processPayment("ORDER-123", 1000);
    }
}
