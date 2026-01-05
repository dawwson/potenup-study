package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception;

import org.ohgiraffers.module01hexagonal_architecture.global.error.DomainException;

public class MemberException extends DomainException {

    private final String code;

    protected MemberException(String code, String message) {
        super(message);
        this.code = code;
    }

    protected MemberException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
