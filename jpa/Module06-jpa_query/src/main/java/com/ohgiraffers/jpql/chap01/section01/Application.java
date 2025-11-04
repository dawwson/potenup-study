package com.ohgiraffers.jpql.chap01.section01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

/*
 * [Native sql의 문제점]
 * 1. 깨져버린 추상화 : "Course" 객체만 알고 있으면 됐는데, 갑자기 "courses"라는 DB 테이블 이름과 그 안의 컬럼 구조까지 알아야 한다.
 * 2. 특정 DB에 종속 : 만약 다른 데이터베이스로 교체했을 때, 특정 DB에서만 동작하는 SQL 함수를 사용했다면? 모든 쿼리를 수정해야 한다.
 * 3. 타입 안정성 부재 : 쿼리 결과는 "object[]" 배열이다. row[0]이 course_id인지, row[1]이 title인지 컴파일 시점에 알 수 없다.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();

        String sql = "SELECT * FROM courses WHERE price >= 300";
        List<Object[]> results = em.createNativeQuery(sql).getResultList();

        System.out.println("조회된 결과의 수 : " + results.size());

        for (Object[] result : results) {
            System.out.println("Course ID : " + result[0] + " Name : " + result[1] + " Description : " + result[2]);
        }

        em.close();
        emf.close();
    }
}
