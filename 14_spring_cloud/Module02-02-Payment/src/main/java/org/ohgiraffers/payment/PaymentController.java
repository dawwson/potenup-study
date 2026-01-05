package org.ohgiraffers.payment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    @GetMapping("/pay")
    public String processPayment() {
        System.out.println("호출됨");
        return "결제 처리 완료!";
    }
}
