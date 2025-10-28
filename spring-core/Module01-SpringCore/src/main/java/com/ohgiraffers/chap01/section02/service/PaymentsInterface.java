package com.ohgiraffers.chap01.section02.service;

public interface PaymentsInterface {
    boolean processPayment(String orderId, double amount);
}
