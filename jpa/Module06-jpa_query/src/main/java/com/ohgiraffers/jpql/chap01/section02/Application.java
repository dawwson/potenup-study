package com.ohgiraffers.jpql.chap01.section02;

import com.ohgiraffers.jpql.chap01.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

/*
 * [2단계 : JPQL - 객체의 언어로 질문하다]
 *
 * 1. 대상의 차이 : JPQL의 FROM Course c는 테이블이 아닌 Course 엔티티 객체를 의미한다.
 *   JPQL은 DB 테이블의 존재를 모르고 오직 엔티티 객체의 세상만 알고 있다.
 *
 * 2. 조건의 차이 : c.price는 DB 컬럼명이 아니라 Course 엔티티의 필드(속성) 이름이다.
 * 3. 결과의 차이 : JPQL은 "Object[]"가 아닌 명확한 Course 객체를 반환한다.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();
        String sql = "SELECT c FROM Course c WHERE price >= 300";

        List<Course> courses = em.createQuery(sql).getResultList();

        System.out.println("단일 테이블 조회");
        courses.forEach(course -> System.out.println(course.getTitle() + " : " + course.getPrice()));

        System.out.println("다중 테이블 조회");
        String joinQuery = "SELECT c FROM Course c JOIN c.lessons WHERE c.price >= 300";

        courses = em.createQuery(joinQuery).getResultList();

        for (Course course : courses) {
            System.out.println(course.getTitle() + " : " + course.getPrice());
            course.getLessons().forEach(System.out::println);
            System.out.println();
            System.out.println();
        }
    }
}
