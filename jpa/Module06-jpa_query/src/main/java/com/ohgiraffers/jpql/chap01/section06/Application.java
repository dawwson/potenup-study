package com.ohgiraffers.jpql.chap01.section06;

import com.ohgiraffers.jpql.chap01.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();

        String sql = "SELECT c FROM Course c";
        TypedQuery<Course> course = em.createQuery(sql, Course.class);

        course.setFirstResult(0);
        course.setMaxResults(2);

        List<Course> courses = course.getResultList();
        for (Course c : courses) {
            System.out.println(c.getCourseId());
        }
    }
}
