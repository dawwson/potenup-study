package com.ohgiraffers.valueobject.chap01.section02;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * 값 객체 : StayPeriod
 *
 * - 단순히 체크인 날짜와 체크아웃 날짜를 Reservation 엔티티의 필드로 관리할 수 있지만,
 *   StayPeriod라는 값 객체를 도입함으로써 이 두 날짜가 하나의 의미있는 기간이라는 개념을 명확하게 표현할 수 있다.
 * - StayPeriod 내부에 해당 기간과 관련된 비즈니스 로직(예: 체크아웃 날짜가 체크인 날짜 이후여야 한다.)를 캡슐화하여
 *   데이터의 유효성을 확보하고 코드의 응집도를 높일 수 있다.
 */
@Embeddable
public class StayPeriod {
    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    protected  StayPeriod() {}

    public StayPeriod(LocalDate checkInDate, LocalDate checkOutDate) throws IllegalAccessException {
        if (checkInDate.isBefore(checkOutDate)) {
            throw new IllegalAccessException("체크아웃 날짜는 체크인 날짜 이후여야 한다.");
        }
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public int getNight() {
        return Period.between(checkInDate, checkOutDate).getDays();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StayPeriod that = (StayPeriod) o;
        return Objects.equals(checkInDate, that.checkInDate) && Objects.equals(checkOutDate, that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkInDate, checkOutDate);
    }
}
