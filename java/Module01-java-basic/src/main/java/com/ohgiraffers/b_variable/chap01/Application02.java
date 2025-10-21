package com.ohgiraffers.b_variable.chap01;

import java.util.Scanner;

public class Application02 {
    public static void main(String[] args){

        // Scanner 객체 : 힙 메모리에 저장, 참조 변수는 스택에 저장됨
        Scanner sc = new Scanner(System.in);

        System.out.println("이름을 입력해주세요 : ");
        String name = sc.nextLine();

        System.out.println("나이를 입력해주세요 : ");
        int age = sc.nextInt();

        System.out.println("안녕하세요, " + age + "세의 " + name + "님!");
        sc.close();
    }

}

