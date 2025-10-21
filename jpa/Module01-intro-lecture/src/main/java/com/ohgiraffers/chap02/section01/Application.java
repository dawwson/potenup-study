package com.ohgiraffers.chap02.section01;

import com.ohgiraffers.chap02.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    private static final EntityManagerFactory EMF;

    static {
        EMF = Persistence.createEntityManagerFactory("jpa-config");
    }

    public static void main(String[] args) {
        EntityManager em = EMF.createEntityManager();

        // 조회 시점에 영속성 컨텍스트 조회하고, 없으면 데이터베이스에서 조회해서 영속성 컨텍스트에 저장함
        Role role = em.find(Role.class, 1);  // 데이터베이스에서 조회 -> 영속성 컨텍스트에 저장
        Role role2 = em.find(Role.class, 1); // 영속성 컨텍스트에서 조회

        System.out.println(role == role2); // true

        em.close();
        EMF.close();
    }
}
