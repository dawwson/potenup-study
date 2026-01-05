package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.vo;

public enum MemberStatus {

    ACTIVE,
    INACTIVE,
    DELETED;

    public boolean canTransitionTo(MemberStatus other) {
        return switch (this) {
            case ACTIVE -> other == ACTIVE || other == DELETED;
            case INACTIVE -> other == INACTIVE || other == DELETED;
            case DELETED -> false;
        };
    }

    public boolean isActive() {
        return this == ACTIVE;
    }
}
