package com.ohgiraffers.valueobject.chap02.section02;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/*
 * 📌 @ElementCollection: 값(Value)은 값답게 다루기
 *
 * =====================================
 * 🏆 1. 핵심 개념: 객체-관계형 패러다임 불일치 해소
 * =====================================
 * 객체 지향 모델에서는 엔티티 내부에 다른 값 객체의 컬렉션을 포함하는 것이 자연스럽습니다.
 * 하지만 관계형 데이터베이스는 테이블 내에 컬렉션 자체를 저장하는 기능을 직접 제공하지 않습니다.
 *
 * `@ElementCollection`은 이러한 패러다임 불일치를 해소하는 핵심적인 메커니즘입니다.
 * 이를 통해 개발자는 엔티티 내에 값 타입(Value Object)의 컬렉션을 자연스럽게 정의할 수 있으며,
 * JPA는 이 컬렉션을 별도의 테이블에 자동으로 매핑하고 관리해줍니다.
 *
 *
 * =====================================
 * 🏆 2. 동작 방식과 생명주기
 * =====================================
 *
 * - `@ElementCollection`이 적용된 필드는 JPA에 의해 별도의 테이블로 매핑됩니다.
 * (이 테이블은 `@CollectionTable` 어노테이션으로 이름과 외래 키 등을 설정할 수 있습니다.)
 * - 컬렉션의 요소들은 String, Integer 같은 기본 자료형이거나 `@Embeddable`로 정의된 값 객체입니다.
 * - 💡 가장 중요한 특징은, 컬렉션 요소들의 생명주기가 소유 엔티티에 완전히 종속된다는 점입니다.
 * 엔티티가 영속화될 때 컬렉션도 함께 저장되고, 엔티티가 삭제될 때 컬렉션도 함께 삭제됩니다.
 *
 *
 * =====================================
 * 🏆 3. 올바른 설계: @OneToMany 대신 @ElementCollection을 사용하는 이유
 * =====================================
 *
 * ✨ 1. 개념적 명확성
 * `@ElementCollection`은 컬렉션의 요소가 독립적인 생명주기를 갖는 엔티티가 아닌,
 * 소유 엔티티에 온전히 '소속된' 값(속성)임을 명확히 나타냅니다. 이는 도메인 모델을 더 정확하게 표현합니다.
 *
 * ✨ 2. 단순하고 안전한 관리
 * 값 타입은 독립적인 생명주기를 갖지 않으므로, 복잡한 양방향 연관관계나 개별적인 생명주기를 관리할 필요가 없습니다.
 * Product에서 사이즈를 추가하거나 제거하면 JPA가 알아서 DB에 반영해주므로 코드가 단순해지고 실수가 줄어듭니다.
 *
 * ✨ 3. 값 기반의 동등성 비교
 * 값 타입은 `equals()`와 `hashCode()`를 재정의하여 식별자(ID)가 아닌 내용(값)을 기반으로 동등성을 비교합니다.
 * 덕분에 'M 사이즈'라는 값 자체로 컬렉션 내에서 객체를 비교하고 `contains()` 같은 메서드를 직관적으로 사용할 수 있습니다.
 *
 *
 * 📖 "엔티티는 식별자(Id)로 관리되는 독립적인 생명주기를 갖는 객체이고,
 * 값 객체는 내용을 기반으로 다뤄지는 객체입니다.
 * 이 둘을 구분하는 것이 도메인 설계의 핵심이며, @ElementCollection은 값 객체의 컬렉션을 다루는 가장 우아한 방법입니다."
 */
public class Application {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-config");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Product product = new Product("멋쟁이 티셔츠");

            AvailableSize sizeS = new AvailableSize("S", 10);
            AvailableSize sizeM = new AvailableSize("M", 10);
            AvailableSize sizeL = new AvailableSize("L", 10);

            product.addAvailableSize(sizeS);
            product.addAvailableSize(sizeM);
            product.addAvailableSize(sizeL);

            em.persist(product);

            /*
             * 해결 1: 불필요한 엔티티 관리 제거
             * - @OneToMany 설계에서는 AvailableSize가 @Entity로 관리되어,
             *   단순한 값 변경(재고 감소)에서도 JPA의 변경 추적(dirty checking)이 발생했다.
             * - @ElementCollection으로 변경하면 AvailableSize가 값 객체로 관리되므로,
             *   JPA가 이를 독립적인 엔티티로 관리하지 않음
             * - 변경 추적은 Product 엔티티만 적용되며, AvailableSize는 Product의 일부로 관리됨
             */
            product.decreaseStock("M", 5);
            System.out.println("티셔츠 M 사이즈 재고 감소 후 : " + product.getAvailableSizes());

            /*
             * 해결 2: 동등성 비교의 용이성 (값 기반 비교)
             * - @OneToMany 설계에서는 AvailableSize가 @Entity로 관리되어 JPA가 ID를 기준으로 비교했다.
             * - 동일한 라벨("M")을 가진 새로운 객체를 생성하더라도 ID가 다르거나 영속성 컨텍스트에 없으면 동일한 객체로 인식되지 않는다.
             * - @ElementCollection으로 변경하면 AvailableSize가 값 객체로 관리되며, equals 메서드를 통해 라벨을 기준으로 값 비교
             */
            AvailableSize searchM = new AvailableSize("M", 0);
            boolean containsM = product.getAvailableSizes().contains(searchM);
            System.out.println("티셔츠 M 사이즈가 포함되어 있는가? " + containsM);

            /*
             * 해결 3: 컬렉션 조작의 간소화
             * - OneToMany 설계에서는 특정 사이즈를 제거하려면 정확한 AvailableSize 인스턴스를 알아야 한다.
             * - ElementCollection으로 변경하면 값 객체의 equals 메서드를 통해 라벨을 기준으로 제거가 가능하다.
             */
            product.removeAvailableSize(new AvailableSize("M", 0));
            System.out.println("티셔츠에서 M 사이즈 제거 후 사이즈 목록 : " + product.getAvailableSizes());

            /*
             * 새로운 "l" 사이즈 추가
             * - OneToMany 설계에서는 동일한 라벨 "L"을 가진 데이터가 중복으로 저장함
             * - ElementCollection으로 변경하면 중복을 방지하기 위해 추가 로직을 구현 가능
             */
            AvailableSize newSizeL = new AvailableSize("L", 10);
            boolean alreadyExists = product.getAvailableSizes().stream()
                    .anyMatch(size -> size.getLabel().equals(newSizeL.getLabel()));

            if (!alreadyExists) {
                product.addAvailableSize(newSizeL);
                System.out.println("티셔츠에 새로운 L 사이즈 추가 후 사이즈 목록 : " + product.getAvailableSizes());
            } else {
                System.out.println("이미 존재하는 사이즈입니다. : " + product.getAvailableSizes());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.clear();
            emf.close();
        }
    }
}
