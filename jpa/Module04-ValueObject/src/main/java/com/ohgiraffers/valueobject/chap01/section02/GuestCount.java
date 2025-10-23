package com.ohgiraffers.valueobject.chap01.section02;

import jakarta.persistence.Column;

import java.util.Objects;

/**
 * Embeddable 어노테이션
 * - 임베디드 타입은 독립적인 엔티티가 아니라, 다른 엔티티에 포함되어 사용되는 값 객체이다.
 * - 데이터베이스 테이블에서 별도의 테이블로 생성되지 않고, 포함된 엔티티의 테이블에 컬럼으로 매핑된다.
 *
 * 사용목적
 * - 관련 데이터를 하나의 객체로 묶어 재사용성을 높이고, 코드 가독성과 유지보수성을 개선한다.
 *
 * 값 객체의 필요성 GuestCount
 * - 투숙객 수 역시 단순한 정수형 필드로 관리할 수 있지만, GuestCount라는 값 객체를 통해
 *   투숙객 수라는 도메인적인 의미를 부여하고 관련 비즈니스 규칙을 적용할 수 있다. (예: 투숙객 수는 1명 이상이어야 한다)
 * - 이렇게 값 객체를 사용하면 데이터의 의미를 더 잘 드러내고, 유효성 검증 로직을 한 곳에 모아 관리하기 용이해진다.
 * - GuestCount 클래스 또한 @Embeddable로 정의되어 JPA에서 값 타입으로 인식되며, Reservation 엔티티에 포함된다.
 */
public class GuestCount {
    @Column(name = "number_of_guest")
    private int value;

    protected  GuestCount(int value) {
        this.value = value;
    }

    public void setValue(int value) throws IllegalAccessException {
        if (value <= 0) {
            throw new IllegalAccessException("투숙객 수는 1명 이상이어야 합니다.");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GuestCount that = (GuestCount) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
    /**
     * 불변 객체 형성에 기여하는 요소
     * 1. setter 메서드는 제공하지 않는다.
     *   - 객체 생성 후 내부 상태(Value 필드)를 변경할 수 없도록 하여 불변성을 유지한다.
     * 2. 생성 시점에 유효성 검증을 수행한다.
     *   - 생성될 때부터 유효한 상태를 가지도록 보장한다.
     * 3. getValue() 메서드를 통해 내부 상태를 읽기만 할 수 있도록 제공한다.
     *   - 외부에서 내부 상태를 간접적으로 변경할 수 있는 방법을 제공하지 않는다. -> 불변성 보장을 위해 값 변경 시 새로운 객체를 생성하도록.
     */
}
