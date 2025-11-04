package com.ohgiraffers.jpql.chap01.section07;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();

        String sql = "SELECT u.username AS student_name, c.title AS course_title, " +
                "CONCAT(i.username, ' (', r.role_name, ')') AS instructor_name " +
                "FROM enrollments e " +
                "JOIN users u ON e.user_id = u.user_id " +
                "JOIN courses c ON e.course_id = c.course_id " +
                "JOIN users i ON c.instructor_id = i.user_id " +
                "JOIN roles r ON i.role_id = r.role_id " +
                "WHERE u.user_id = 6716";

        Query query = em.createNativeQuery(sql);
        List<Object[]> resultList = query.getResultList();

        for (Object[] result : resultList) {
            String studentName = (String) result[0];
            String courseTitle = (String) result[1];
            String instructorName = (String) result[2];
            System.out.println(studentName + " " + courseTitle + " " + instructorName);
        }

        em.close();
        emf.close();
    }
}
