package com.ohgiraffers.injection.chap01.section02.service;

public interface PaymentsInterface {
    boolean processPayment(String orderId, double amount);
}
