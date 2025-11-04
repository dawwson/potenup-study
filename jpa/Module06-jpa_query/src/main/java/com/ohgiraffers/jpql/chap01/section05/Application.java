package com.ohgiraffers.jpql.chap01.section05;

import com.ohgiraffers.jpql.chap01.section05.dto.CourseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

/*
 * [DTO 프로젝션 : 원하는 정보만 골라서 듣기]
 * select c from course c와 같이 엔티티 전체를 조회하는 것은 간단한 보고서를 받으려고 책 한 권을 통째로 빌리는 것과 같다.
 * 화면에 필요한 것은 강좌 이름과 소속된 강의 수 뿐인데 "Course" 엔티티의 모든 정보를 메모리에 올리는 것은 낭비가 된다.
 *
 * [DTO 프로젝션의 본질]
 * - "select new com.package.DTO(생성자) " : Course의 엔티티의 모든 정보는 필요 없고,
 *   필요한 정보만 도출해서 객체를 만드는 것을 의미한다.
 * - 이는 성능 최적화 뿐만 아니라, 조회 계층과 도메인 모델 계층을 명확하게 분리하는 설계 방식이다.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();

        // 경로를 다 알려줘야 함(고유함을 보장하기 위해)
        String jpql = " SELECT new com.ohgiraffers.jpql.chap01.section05.dto.CourseDTO(c.courseId, c.title, count(l))" +
                " FROM Course c join c.lessons l group by c.courseId, c.title" +
                " having count(l) > :cnt";

        TypedQuery<CourseDTO> query = em.createQuery(jpql, CourseDTO.class);
        query.setParameter("cnt", 5);
        List<CourseDTO> courseList = query.getResultList();
        courseList.forEach(System.out::println);
    }
}
