package org.ohgiraffers.security.exception;

public class InvalidJwtCustomException extends RuntimeException {
    public InvalidJwtCustomException(String message) {
        super(message);
    }
}
