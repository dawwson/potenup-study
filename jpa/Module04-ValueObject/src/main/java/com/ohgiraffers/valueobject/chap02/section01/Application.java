package com.ohgiraffers.valueobject.chap02.section01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Product product = new Product("멋쟁이 티셔츠");

            AvailableSize sizeS = new AvailableSize("S", 10);
            AvailableSize sizeM = new AvailableSize("S", 20);
            AvailableSize sizeL = new AvailableSize("S", 15);

            product.addAvailableSize(sizeS);
            product.addAvailableSize(sizeM);
            product.addAvailableSize(sizeL);

            em.persist(product);

            /**
             * 문제점 1: 불필요한 엔티티 관리로 인한 오버헤드 발생
             * - AvailableSize는 Product에 종속적인 데이터로, 독립적인 생명주기를 가질 필요가 없음
             * - 하지만 @Entity로 정의되어 JPA가 이를 독립적인 엔티티로 관리
             * - 단순한 값 변경(재고 감소)에도 JPA의 변경 추적이 발생하여 불필요한 오버헤드 초래
             */
            product.decreaseStock("M", 5);
            System.out.println("티셔츠 M 사이즈 재고 감소 후 : " + product.getAvailableSizes());

            /**
             * 문제점 2: 동등성 비교의 어려움
             * - AvailableSize가 엔티티로 관리되므로, JPA는 참조 기반으로 객체를 비교함
             * - 동일한 라벨("M")을 가진 새로운 AvailableSize 객체를 생성하더라도,
             *   컬렉션에서 이를 동일한 객체로 인지하지 않음
             * - 이는 JPA에서 엔티티를 식별하는 기준은 ID가 되기 때문에 값만으로 비교하지 않음
             */
            AvailableSize searchM = new AvailableSize("M", 0);
            boolean containsM = product.getAvailableSizes().contains(searchM);
            System.out.println("티셔츠에 M사이즈가 포함되어 있는가? " + containsM);

            /**
             * 문제점 3: 컬렉션 조작의 번거로움
             * - M 사이즈를 제거하려면, 컬렉션 내의 정확한 AvailableSize 인스턴스를 알아야 한다.
             * - 값객체로 설계하면 단순히 라벨 "M"을 기준으로 제거 가능
             * - 또한, 양방향 연관관계를 관리해야 하므로 removeAvailableSize 메서드에서
             *   AvailableSize의 product 필드를 null로 설정하는 추가 작업이 필요함
             */
            // 다른 객체로 전달되기 때문에 식별의 기준이 다름. 삭제되지 않음.
            product.removeAvailableSize(searchM);

            // 다음과 같이 동일한 엔티티를 전달해야 함
            product.removeAvailableSize(sizeM);

            /**
             * 새로운 M 사이즈 추가
             * - 동일한 라벨("m")을 가진사이즈를 다시 추가 가능
             * - 하지만 Entity로 관리되기 때문에 동일한 라벨을 가진 데이터가 중복으로 저장됨
             * - 값 객체로 설계하면 라벨을 기준으로 중복을 방지할 수 없음
             */
            AvailableSize newSize = new AvailableSize("M", 20);
            product.addAvailableSize(newSize);
            System.out.println("티셔츠에 L사이즈 추가 후 사이즈 목록 : " + product.getAvailableSizes());

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("유효하지 않은 값입니다.");
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
