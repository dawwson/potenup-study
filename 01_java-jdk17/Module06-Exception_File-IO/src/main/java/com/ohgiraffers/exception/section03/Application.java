package com.ohgiraffers.exception.section03;

public class Application {
    public static void main(String[] args) {
        try {
            checkAge(10);
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void checkAge(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("나이가 18세 미만이면 가입할 수 없습니다.");
        }
        System.out.println("가입 가능!");
    }
}
