package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class MemberId {

    @Column(name = "member_id", nullable = false, updatable = false)
    private UUID value;

    protected MemberId() {}

    private MemberId(UUID value) {
        this.value = Objects.requireNonNull(value, "MemberId는 null일 수 없습니다.");
    }

    public static MemberId newId() {
        return new MemberId(UUID.randomUUID());
    }

    public static MemberId of(UUID value) {
        return new MemberId(value);
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return true;

        if (!(o instanceof MemberId that)) return false;

        return value.equals(this.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return "MemberId{" +
                "value=" + value +
                '}';
    }
}
