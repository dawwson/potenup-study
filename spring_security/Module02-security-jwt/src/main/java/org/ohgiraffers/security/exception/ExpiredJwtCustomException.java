package org.ohgiraffers.security.exception;

import java.util.jar.JarException;

public class ExpiredJwtCustomException extends JarException {

    public ExpiredJwtCustomException(String message) {
        super(message);
    }
}
