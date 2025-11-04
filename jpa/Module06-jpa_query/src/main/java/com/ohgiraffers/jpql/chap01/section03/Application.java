package com.ohgiraffers.jpql.chap01.section03;

import com.ohgiraffers.jpql.chap01.model.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

/*
 * [TypedQuery]
 * - 타입 안정성 : 결과를 제네릭 타입으로 지정하여 컴파일 시점에 오류를 감지할 수 있다.
 * - 객체 중심적 접근 : 쿼리 결과를 직접 지정한 엔티티 클래스로 매핑하여 불필요한 형변환 없이 바로 사용할 수 있다.
 * - 가독성 향상 : 코드의 가독성이 높아지고, 의도를 명확히 전달할 수 있다.
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();

        String sql = "SELECT c FROM Course c WHERE price >= 300";
        TypedQuery<Course> courses = em.createQuery(sql, Course.class);
        // 반환타입에 맞는 클래스를 지정해주지 않으면 컴파일 단계에서 에러를 발생시켜서 타입 안정성 보장

        System.out.println("단일 테이블 조회");
        courses.getResultList().forEach(course -> System.out.println(course.getTitle() + " : " + course.getPrice()));

        em.close();
        emf.close();
    }
}
