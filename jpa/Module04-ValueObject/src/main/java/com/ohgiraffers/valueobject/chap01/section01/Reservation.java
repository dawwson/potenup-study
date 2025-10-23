package com.ohgiraffers.valueobject.chap01.section01;

import java.time.LocalDate;

/**
 * [Value Object의 필요성]
 * VO를 사용하지 않으면 다음과 같은 문제점을 초래할 수 있다.
 * 1. 가독성 저하
 *   - 생성자에 많은 파라미터가 전달되면 코드가 복잡해지고, 각 파라미터가 무엇을 의미하는지 직관적으로 이해하기 어려움.
 *
 * 2. 유지보수성 저하
 *   - 필드 추가/삭제 시 생성자와 호출 코드 모두를 수정해야 함
 *
 * 3. 타입 안정성 부족 :
 *   - 개별 필드를 문자열이나 기본 타입으로 전달하면 타입 안정성이 떨어지고, 잘못된 데이터가 전달될 가능성이 높음
 *
 * 4. 코드 중복 :
 *   - 동일한 데이터 그룹을 여러 객체에서 반복적으로 정의해야 함
 *
 * 5. 불변성 보장의 어려움
 *   - 개별 필드를 직접 다루면 불변 객체로 만들기 어려움
 *
 * 6. 비즈니스 로직 분산
 *   - VO를 사용하지 않으면 데이터 검증 로직이 여러 곳에 분산되어 일관성 유지가 어려움
 *
 * VO를 사용하는 경우 이러한 문제점을 해결할 수 있다.
 * - 관련 데이터를 하나의 객체로 묶어 가독성과 유지보수성을 높임
 * - 타입 안정성과 불변성을 보장
 * - 데이터 검증 로직을 VO에 캡슐화하여 비즈니스 로직을 일관되게 관리
 */
public class Reservation {
    private String guestName;
    private String roomNumber;
    private int numberOfGuests;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalPrice;

    public Reservation(String guestName, String roomNumber, int numberOfGuests, LocalDate checkInDate, LocalDate checkOutDate, int totalPrice) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.numberOfGuests = numberOfGuests;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
    }

    public int calculateNights() {
        return (int) checkOutDate.toEpochDay() - (int) checkInDate.toEpochDay();
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPrice() {
        return String.valueOf(totalPrice);
    }
}