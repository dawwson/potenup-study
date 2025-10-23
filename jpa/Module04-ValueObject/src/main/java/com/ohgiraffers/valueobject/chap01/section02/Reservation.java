package com.ohgiraffers.valueobject.chap01.section02;

/**
 * @Embedded 어노테이션
 * - 이 필드는 JPA의 임베디드 타입(Embedded Type)을 포함하는 필드임을 나타낸다.
 * - 임베디드 타입은 @Embeddable로 정의된 값 객체(Value Object)로 독립적인 엔티티가 아니라 포함된 엔티티의 일부로 사용된다.
 *   즉, 두 객체가 합쳐서 하나의 테이블을 형성한다는 것을 의미한다.
 *
 * 추가 설정
 * - @AttributeOverrides를 사용하여 임베디드 타입의 필드 이름을 커스터마이징 할 수 있다.
 */

import jakarta.persistence.*;

/**
 * Reservation 엔티티 생성 및 값 객체 활용
 * - Reservation 엔티티는 StayPeriod와 GuestCount 값 객체를 포함하고 있다.
 * - Reservation 객체를 생성할 때, 이미 StayPeriod와 GuestCount 객체가 생성되는 과정에서
 *   정의된 비즈니스 규칙들이 적용되어 데이터의 유효성을 일차적으로 검증할 수 있다.
 * - calculateTotalPrice() 메서드에서도 stayPeriod 값 객체의 정보를 활용하여 총 가격을 계산한다.
 */
@Entity
@Table(name = "hotel_reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guest_name")
    private String guestName;

    @Column(name = "room_number")
    private String roomNumber;

    @Embedded
    private GuestCount guestCount;

    @Embedded
    private StayPeriod stayPeriod;

    @Column(name = "room_rate")
    private int roomRate;

    public Reservation() {}

    public Reservation(String guestName, String roomNumber, GuestCount guestCount, StayPeriod stayPeriod, int roomRate) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.guestCount = guestCount;
        this.stayPeriod = stayPeriod;
        this.roomRate = roomRate;
    }

    // 예약 가격을 확인하는 메서드
    // 체크인 날짜는 체크아웃 보다 크다는 것을 보장받기 때문에 별도의 검증 로직을 수행하지 않아도 된다.
    public int calculateTotalPrice() {
        return stayPeriod.getNight() * roomRate;
    }

    public Long getId() {
        return id;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public GuestCount getGuestCount() {
        return guestCount;
    }

    public StayPeriod getStayPeriod() {
        return stayPeriod;
    }

    public int getRoomRate() {
        return roomRate;
    }
}
