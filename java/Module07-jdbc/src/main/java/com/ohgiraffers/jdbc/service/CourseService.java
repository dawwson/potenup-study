package com.ohgiraffers.jdbc.service;

import com.ohgiraffers.jdbc.dao.CourseDao;
import com.ohgiraffers.jdbc.model.Course;
import com.ohgiraffers.jdbc.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * service 레이어란?
 *
 * [ Service Layer의 역할 ]
 * 비즈니스 로직을 처리하는 계층으로, 주된 역할은 다음과 같다.
 * - 비즈니스 로직 처리 : 데이터베이스와 직접적으로 상호작용하는 DAO(Data Access Object) 계층을 활용하여,
 *   필요한 데이터를 가공하거나 추가적인 로직을 수행한다.
 * - 트랜잭션 관리 : 여러 개의 데이터 변경 작업이 원자성을 유지하도록 commit()/rollback()을 활용해 트랜잭션을 관리한다.
 * - 예외 처리 : DAO에서 발생한 SQLException을 상위 계층(view)에서 처리할 수 있도록 변환 또는 로깅
 * - 데이터 검증 : 요청된 데이터가 유효한지 검증한 후 처리
 */
public class CourseService {
    private final CourseDao courseDao;
    /**
     * Connection이 필요한 이유
     * 서비스 레이어에서 트랜잭션 수행을 하게 된다.
     * DAO 계층에서 서비스와 다른 별도의 connection을 가지게 된다면
     * 세션이 유지되지 않는 문제가 발생하게 되며 이러한 문제로 인해
     * 트랜잭션 제어에 문제가 생기게 된다.
     * 또한, 트랜잭션을 제어하기 위해서는 Connection을 service에서도 가지고 있어야 한다.
     */
    private final Connection connection;

    public CourseService(Connection connection) {
        this.connection = connection;
        this.courseDao = new CourseDao(connection);
    }

    public List<Course> courseFindAll() {
        List<Course> courses = null;

        try {
            courses = courseDao.findAll();

            if (courses.isEmpty()) {
                System.out.println("log : 강좌 없음.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("log : error");
        }

        return courses;
    }

    // 과정 등록
    public long courseSave(Course course) {
        try {
            long result = courseDao.save(course);

            if (result < 0) {
                System.out.println("log : 과정 등록 실패");
                return 0;
            } else {
                return result;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("log : error");
        }
        return 0;
    }

    // 과정 조회
    public Course findCourse(long id) {
        try {
            Course findCourse = courseDao.findById(id);

            if (findCourse == null) {
                System.out.println("log : 강좌 조회 실패");
                return null;
            } else {
                return findCourse;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int updateCourse(long id, String title, String description) {
        int result = 0;

        try {
            Course findCourse = findCourse(id);

            if (findCourse == null) {
                System.out.println("log : ID에 해당하는 강좌가 존재하지 않습니다.");
                return 0;
            }
            findCourse.setTitle(title);
            findCourse.setDescription(description);

            result = courseDao.update(findCourse);
        } catch (SQLException e) {
            throw new RuntimeException("강좌 수정 중 데이터베이스 오류 발생");
        }

        return result;
    }

    /**
     * 과정 삭제를 위한 메서드
     * @param id 삭제할 과정의 번호
     * @return 0: 삭제 실패, 1: 삭제 성공
     */
    public int deleteCourse(long id) {
        // 1. 삭제할 대상 조회
        Course findCourse = findCourse(id);

        // 2. 삭제 대상의 존재 여부 확인
        if (findCourse == null) {
            System.out.println("log : ID에 해당하는 강좌가 존재하지 않습니다.");
            return 0;
        }

        // 3. 삭제를 요청함
        int result = 0;

        try {
            result = courseDao.delete(id);

            if (result > 0) {
                return result;
            } else {

            }
        } catch (SQLException e) {
            throw new RuntimeException("강좌 삭제 중 데이터베이스 오류 발생", e);
        }

        // 4. 결과 반환
        return result;
    }
}
