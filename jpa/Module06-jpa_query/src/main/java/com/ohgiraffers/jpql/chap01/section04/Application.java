package com.ohgiraffers.jpql.chap01.section04;

import com.ohgiraffers.jpql.chap01.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

/*
 * Query Parameter
 * - 동적 값 처리 : 쿼리에 변수를 사용하여 안전하게 값 주입
 * - 객체 중심 : 속성명에 직접 바인딩
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();

        String badJpql = "SELECT c FROM Course c WHERE c.title LIKE '%자바%'";
        TypedQuery<Course> query = em.createQuery(badJpql, Course.class);
        query.getResultList().forEach(course -> System.out.println(course.getTitle() + " : " + course.getPrice()));

        String jpql = "SELECT c FROM Course c WHERE c.title LIKE :title";
        TypedQuery<Course> findCourse = em.createQuery(jpql, Course.class);
        findCourse.setParameter("title", "%알고리즘%");

        List<Course> courseList = findCourse.getResultList();
        courseList.forEach(course -> System.out.println(course.getTitle() + " : " + course.getPrice()));
        
        em.close();
        emf.close();
    }
}
