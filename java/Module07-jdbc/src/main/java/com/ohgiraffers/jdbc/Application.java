package com.ohgiraffers.jdbc;

import com.ohgiraffers.jdbc.config.JDBCConnection;
import com.ohgiraffers.jdbc.model.Course;
import com.ohgiraffers.jdbc.service.CourseService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        try {
            Connection conn = JDBCConnection.getConnection();
            System.out.println("데이터베이스 연결 성공!");

            Scanner sc = new Scanner(System.in);

            while(true){
                System.out.println("=== 1. 기초 실습 ===");
                System.out.println("=== 2. 중급 실습 ===");

                int num = sc.nextInt();

                if (num == 1) {
                    runBeginnerExercise(conn);
                } else if (num == 2) {
                    runIntermediateExercise(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void runBeginnerExercise(Connection conn) throws SQLException {
        CourseService courseService = new CourseService(conn);
        List<Course> courses = courseService.courseFindAll();

        for (Course course : courses) {
            System.out.println(course);
        }

    }

    public static void runIntermediateExercise(Connection conn) throws SQLException {
        System.out.println("== 중급 실습 콘텐츠 모듈 crud ==");
        CourseService courseService = new CourseService(conn);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("=== 작업을 선택해주세요 ===");
            System.out.println("1. 과정 등록");
            System.out.println("2. 단일 과정 조회");
            System.out.println("3. 과정 수정");
            System.out.println("4. 과정 삭제");
            System.out.println("5. 이전으로 돌아가기");

            int num = sc.nextInt();
            sc.nextLine();

            if (num == 1) {
                System.out.println("과정명을 입력해주세요 : ");
                String title = sc.nextLine();

                System.out.println("과정 설명을 입력해주세요 : ");
                String description = sc.nextLine();

                Course course = new Course(1L, title, description, "draft");
                System.out.println("[등록] course = " + course);
                long result = courseService.courseSave(course);

                if (result > 0) {
                    System.out.println("=== 모듈 등록 성공 ===");
                } else {
                    System.out.println("=== 모듈 등록 실패 ===");
                }
            } else if (num == 2) {
                System.out.println("조회할 과정 번호를 입력해주세요.");
                int id = sc.nextInt();
                sc.nextLine();

                Course foundModule = courseService.findCourse(id);
                System.out.println("모듈 조회 결과 : " + foundModule);
            } else if (num == 3) {
                System.out.println("수정할 과정 번호를 입력해주세요.");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.println("새로운 과정명을 입력해주세요.");
                String newTitle = sc.nextLine();


                System.out.println("새로운 과정 설명을 입력해주세요.");
                String newDescription = sc.nextLine();

                int foundModule = courseService.updateCourse(id, newTitle, newDescription);
                System.out.println("모듈 조회 결과 : " + foundModule);
            } else if (num == 4) {
                // 삭제할 번호를 입력받는다.
                System.out.println("삭제할 번호를 입력해주세요.");
                int id = sc.nextInt();
                sc.nextLine();

                // 서비스에 넘겨준다.
                // 전 : 입력 받기, 후 : 반환값 (int)
                if (id > 0) {
                    int result = courseService.deleteCourse(id);

                    if (result > 0) {
                        // 성공 시
                        System.out.println("삭제 성공 : " + id);
                    } else {
                        // 실패 시
                        System.out.println("삭제 실패 : " + id);
                    }
                } else {
                    // 값이 없을 경우
                    System.out.println("error : 다시 입력해주세요.");
                }
            }
        }
    }
}
