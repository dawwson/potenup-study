package com.ohgiraffers.injection.chap01.section01;

import com.ohgiraffers.injection.chap01.section01.config.AppConfig;
import com.ohgiraffers.injection.chap01.section01.service.PaymentsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        PaymentsService paymentsService = context.getBean(PaymentsService.class);
        paymentsService.processPayment("ORDER-123", 5000);

        ((AnnotationConfigApplicationContext)context).close();
    }
}
