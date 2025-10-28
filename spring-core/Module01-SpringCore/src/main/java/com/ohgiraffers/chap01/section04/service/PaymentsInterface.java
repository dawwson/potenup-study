package com.ohgiraffers.chap01.section04.service;

public interface PaymentsInterface {
    boolean processPayment(String orderId, double amount);
}
