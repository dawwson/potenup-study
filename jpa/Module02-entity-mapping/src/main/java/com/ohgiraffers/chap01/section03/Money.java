package com.ohgiraffers.chap01.section03;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;



/**
 * [값 객체]
 * Product 엔티티 가격을 저장하기 위해 @Column private BigDecimal amount와
 * @Column private String currency: 두 필드를 따로 둘 수도 있다.
 * 그러나 이렇게 하면 가격이라는 비즈니스 개념이 코드에 드러나지 않고 두 필드는 그저 관련 없는 데이터 조각처럼 보인다.
 * 가격 관련 로직(예: 통화 변환, 가격 비교)가 있다면 Product 엔티티나 서비스 계층에 흩어져 캡슐화가 깨질 것이다.
 *
 * [해결 아이디어]
 * 가격과 통화는 항상 함께 움직이는 하나의 개념이 아닐까? -> 만드려는 애플리케이션의 특징마다 차이가 있음
 * > 현재 값 객체는 위 아이디어를 반영한 것이다.
 *
 * @Embeddable의 본질
 * - @Embeddable은 이 클래스는 독립적인 실체가 아니라 다른 엔티티에 포함되어 특정 개념을 나타내는 값의 묶음이다- 라는 선언
 * - 이를 통해 Product는 Money라는 개념을 소유하게 되며 객체 모델이 훨씬 풍부하고 명확해진다.
 */
@Embeddable
public class Money {
    @Column(name = "price_amount", nullable = false, precision = 10, scale = 2)
    // BigDecimal 10자리까지, 소수점 둘째자리까지 표현
    private BigDecimal priceAmount;

    @Column(name = "price_currency", length = 10)
    private String currency;

    protected Money() {}

    public Money(BigDecimal priceAmount, String currency) {
        if (priceAmount == null || currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("Money price cannot be null or empty");
        }
        this.priceAmount = priceAmount;
        this.currency = currency;
    }

    public BigDecimal getPriceAmount() {
        return priceAmount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Money{" +
                "priceAmount=" + priceAmount +
                ", currency='" + currency + '\'' +
                '}';
    }

    public Money add(Money other) {
        validateSameCurrency(other);

        return new Money(this.priceAmount.add(other.priceAmount), this.currency);
    }

    public Money subtract(Money other) {
        validateSameCurrency(other);

        return new Money(this.priceAmount.subtract(other.priceAmount), this.currency);
    }

    private void validateSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency does not match");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Money)) {
            return false;
        }

        Money other = (Money) obj;
        // compareTo : 현재 객체의 값이 크면 1, 같으면 0, 더 작으면 -1
        return this.priceAmount.compareTo(other.priceAmount) == 0
                && Objects.equals(this.currency, other.currency);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
