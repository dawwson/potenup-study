package com.ohgiraffers.injection.chap01.section02.config;

import com.ohgiraffers.injection.chap01.section02.service.PaymentsInterface;
import com.ohgiraffers.injection.chap01.section02.service.PaymentsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ohgiraffers.injection.chap01.section02")
public class AppConfig {

    @Bean("kakaoPayService")
    public PaymentsService kakaoPayService(PaymentsInterface paymentGateway) {
        return new PaymentsService(paymentGateway);
    }

    @Bean("naverPayService")
    public PaymentsService naverPayService(
            @Qualifier("naverPayGateway") PaymentsInterface paymentGateway
    ) {
        return new PaymentsService(paymentGateway);
    }
}
