package org.ohgiraffers.order.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable String id) {
        System.out.println("Order Service Called with ID: " + id);
        String response = restTemplate.getForObject("http://PAYMENT-SERVICE:8081/payments/", String.class);

        return "Order ID: " + id + ", Payment Service Response: " + response;
    }
}
