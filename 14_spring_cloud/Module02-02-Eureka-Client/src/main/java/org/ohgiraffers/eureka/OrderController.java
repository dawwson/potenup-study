package org.ohgiraffers.eureka;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/create-order")
    public String createOrder() {
        String paymentServiceUrl = "http://PAYMENT-SERVICE/api/v1/pay";

        try {
            String response = restTemplate.getForObject(paymentServiceUrl, String.class);

            return "주문 처리 성공, 결제 서비스 응답 : " + response;
        } catch (Exception e) {
            return "주문 처리 실패: "+ e.getMessage();
        }
    }
}
