package com.ohgiraffers.fileio.section02;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter("user_input.txt"));
                Scanner scanner = new Scanner(System.in);
        ) {
            System.out.println("입력 값 : ");
            String message = scanner.nextLine();

            writer.write("사용자 입력 : " + message);
            writer.newLine();
            writer.write("작성 시간 : " + java.time.LocalDate.now());
            writer.flush();
            System.out.println("파일에 데이터 저장 완료");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
