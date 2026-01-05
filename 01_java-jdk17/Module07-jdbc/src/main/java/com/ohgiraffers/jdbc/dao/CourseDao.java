package com.ohgiraffers.jdbc.dao;

import com.ohgiraffers.jdbc.model.Course;
import com.ohgiraffers.jdbc.util.QueryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO(Data Access Object)
 * 데이터베이스의 courses 테이블에 직접 접근하여 sql을 실행하는 책임을 맡는 클래스이다.
 * 오직 데이터 CRUD 작업에만 집중하며, 비즈니스 로직이나 트랜잭션은 service 계층에 위임한다.
 */
public class CourseDao {
    private final Connection connection;

    public CourseDao(Connection connection) {
        this.connection = connection;
    }

    public List<Course> findAll() throws SQLException {
        List<Course> courses = new ArrayList<>();
        // ? 사용해서 동적으로 query 작성 가능
        String sql = "SELECT course_id, author_id, title, description, status FROM courses";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet re = pstmt.executeQuery();

            while (re.next()) {
                Course course = new Course(
                        // 어떤 컬럼을 꺼낼 건지
                        re.getLong("course_id"),
                        re.getLong("author_id"),
                        re.getString("title"),
                        re.getString("description"),
                        re.getString("status")
                );

                courses.add(course);
            }
        }
        return courses;
    }

    public long save(Course course) throws SQLException {
        String sql = QueryUtil.getQuery("course.save");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, course.getCourseId());
            pstmt.setString(2, course.getTitle());
            pstmt.setString(3, course.getDescription());
            pstmt.setString(4, course.getStatus());

            int result = pstmt.executeUpdate();

            if (result > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1);
                    }
                }
            }
        }
        throw new SQLException("강좌 생성에 실패하였습니다.");
    }

    public Course findById(long id) throws SQLException {
        String query = QueryUtil.getQuery("course.findById");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Course(
                        rs.getLong(1),
                        rs.getLong(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                );
            }
        }
        throw new SQLException("강좌 조회에 실패하였습니다.");
    }

    public int update(Course course) throws SQLException {
        String sql = QueryUtil.getQuery("course.update");

        int rowsAffected;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, course.getTitle());
            pstmt.setString(2, course.getDescription());
            pstmt.setLong(3, course.getCourseId());

            rowsAffected = pstmt.executeUpdate();
        }
        return rowsAffected;
    }

    /**
     * course의 id를 전달받고 삭제함
     * @param id 삭제할 값
     * @return 0: 실패, 1: 성공
     * @throws SQLException
     */
    public int delete(long id) throws SQLException {
        // 1. 삭제하라고 질의함
        String sql = QueryUtil.getQuery("course.delete");

        // 2. 결과를 저장하기 위함
        int rowsAffected;

        // 3. DB 요청
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            rowsAffected = pstmt.executeUpdate();
        }
        return rowsAffected;
    }
}
