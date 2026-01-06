package org.ohgiraffers.payment;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @GetMapping("/{id}")
    public String getPaymentInfo(@PathVariable String id) {

        System.out.println("Payment Service Called with ID: " + id);
        return "Payment Info for ID: " + id + "successfully.";
    }
}
