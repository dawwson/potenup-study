package com.ohgiraffers.chap01.section04;

import com.ohgiraffers.chap01.section04.service.PaymentsService;
import com.ohgiraffers.chap01.section04.config.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("=== 빈의 생명주기 ===");
        PaymentsService paymentsService = context.getBean(PaymentsService.class);
        paymentsService.processPayment("ORDER-123", 5000);

        ((AnnotationConfigApplicationContext) context).close();
    }
}
