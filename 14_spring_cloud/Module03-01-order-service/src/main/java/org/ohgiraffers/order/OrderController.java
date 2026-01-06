package org.ohgiraffers.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/orders")
    public ResponseEntity<String> getOrders() {
        return ResponseEntity.ok("Order Service is working!");
    }
}
