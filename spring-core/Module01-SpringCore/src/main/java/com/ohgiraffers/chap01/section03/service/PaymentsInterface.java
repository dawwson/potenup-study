package com.ohgiraffers.chap01.section03.service;

public interface PaymentsInterface {
    boolean processPayment(String orderId, double amount);
}
