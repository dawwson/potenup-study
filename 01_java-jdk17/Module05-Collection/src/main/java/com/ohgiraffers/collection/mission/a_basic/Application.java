package com.ohgiraffers.collection.mission.a_basic;

import java.util.Scanner;

public class Application {
    private static boolean running = true;
    private final TaskManager tm = new TaskManager();  // 불변 객체
    private Scanner sc = null;

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }

    public void run(){
        System.out.println("=== ⭐️ My ToDo App ⭐️ ===");
        sc = new Scanner(System.in);

        while (running) {
            printMenu();
            String input = sc.nextLine();

            switch (input) {
                case "0":
                    exit();
                    break;
                case "1":
                    add();
                    break;
                case "2":
                    delete();
                    break;
                case "3":
                    search();
                    break;
                case "4":
                    getAll();
                    break;
            }
        }

        sc.close();
        System.out.println("프로그램 종료.");
    }

    public void printMenu() {
        System.out.println("\n0. 종료하기");
        System.out.println("1. TODO 추가");
        System.out.println("2. TODO 삭제");
        System.out.println("3. TODO 검색");
        System.out.println("4. TODO 보기");
        System.out.print("🙂 번호를 입력하세요 👉 ");
    }

    public void exit() {
        System.out.println("🥹 모든 데이터가 사라집니다! 종료 중...");
        running = false;
    }

    public void add() {
        System.out.print("🙂 내용을 입력하세요 👉 ");
        String input = sc.nextLine();
        this.tm.addTask(input);

        System.out.println("✅ 저장되었습니다.");
    }

    public void delete() {
        System.out.print("🙂 삭제할 TODO의 번호를 입력하세요 👉 ");
        String input = sc.nextLine();
        this.tm.removeTask(Integer.parseInt(input) - 1); // index는 0부터 시작하므로 1을 빼서 전달

        System.out.println("✅ 삭제되었습니다.");
    }

    public void search() {
        System.out.print("🙂 검색할 TODO의 번호를 입력하세요 👉 ");
        String input = sc.nextLine();
        this.tm.findTask(Integer.parseInt(input) - 1); // index는 0부터 시작하므로 1을 빼서 전달
    }

    public void getAll() {
        System.out.println("\n▶︎ TODO LIST");
        this.tm.printAllTasks();
    }
}
