package com.ohgiraffers.chap02.section02;

import com.ohgiraffers.chap02.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");

        // 비영속
        //testNewState(emf);

        // 영속 상태 : 해당 엔티티는 영속성 컨텍스트에서 관리가 되는 객체로, 값이 DB에 저장된다.
        //testManagedState(emf);

        // 준영속 상태 : 준영속 이전 시점의 상태를 저장하고 DB에 반영함
        //testDetachedState(emf);

        // 삭제 : 영속화 이후 삭제가 되면 데이터베이스에서 값을 제거한다.
        testRemovedState(emf);

        emf.close();
    }


    /**
     * 비영속 상태 테스트
     * - 엔티티 객체가 JPA와 연관되지 않은 상태를 확인한다.
     */
    public static void testNewState(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /**
         * 비영속 상태 (new)
         * - Role 객체를 생성하였지만, 아직 JPA의 영속성 컨텍스트와 연관되지 않은 상태이다.
         * - JPA는 이 객체를 관리하지 않으며, 데이터베이스와도 연결되지 않는다.
         * - 이 상태에서 객체를 수정하거나 삭제해도 JPA나 데이터베이스에 영향을 주지 않는다.
         */
        Role role = new Role("비영속 권한");

        System.out.println("비영속 상태 - role = " + role);

        // JPA에서 아직 관리가 되는 객체가 아닌 일반 객체이기 때문에 변경을 추적하지 않음
        role.setRoleName("변경된 비영속 권한");
        em.getTransaction().commit();
        em.close();
    }

    public static void testManagedState(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Role role = new Role("영속 권한");

        /**
         * 영속 상태
         * - em.persist(role)를 호출하여 Role 객체가 영속성 컨텍스트에 등록된다.
         * - JPA는 이 객체를 1차 캐시에 보관하며, 상태 변화를 추적한다.
         * - 트랜잭션 커밋 시 insert 쿼리가 실행되어 데이터베이스에 반영된다.
         */
        em.persist(role); // 영속화(JPA가 관리함)

        System.out.println("영속 상태 : " + role);

        role.setRoleName("변경된 영속 권한");
        em.getTransaction().commit();

        //Role role2 = em.find(Role.class, 5);
        //System.out.println("변경 확인 : " + role2);

        em.close();
    }

    private static void testDetachedState(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        /**
         * 준영속 상태
         * - em.persist(role)로 영속 상태가 된 객체를 em.detach(role)로 준영속 상태로 전환한다.
         * - 준영속 상태에서는 영속성 컨텍스트에서 분리되어 더이상 JPA가 변경을 추적하지 않는다.
         * - 그러나 persist로 인해 이미 insert 작업이 등록되었기 때문에
         *   트랜잭션 커밋 시 insert 쿼리가 실행되게 된다.
         * - 준영속 상태에서 변경 사항은 반영되지 않는다.
         */
        Role role = new Role("준영속 권한");
        em.persist(role);
        System.out.println("영속 상태 : " + role);

        em.detach(role);
        System.out.println("준영속 상태 : " + role);

        role.setRoleName("준영속 상태 변경");
        em.getTransaction().commit();
        em.close();
    }

    public static void testRemovedState(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Role role = new Role("삭제 권한");

        em.persist(role);

        System.out.println("영속 상태 - role = " + role);
        /**
         * 삭제 상태
         * - em.persist(role)로 영속 상태가 된 객체를 em.remove(role)로 삭제 상태로 전환한다.
         * - 삭제 상태에서는 트랜잭션 커밋 시 delete 쿼리가 실행되며 데이터베이스에 해당 레코드가 삭제된다.
         * - 단, 데이터베이스에 아직 저장되지 않은 객체(예: persist 후 커밋 전)에 remove를 호출하면
         *   insert, delete 모두 실행되지 않을 수 있다.
         */
        em.remove(role); // 삭제 상태 => delete
        System.out.println("삭제 상태 - role = " + role);
        em.getTransaction().commit();
        em.close();
    }
}
