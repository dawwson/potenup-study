package org.ohgiraffers.module01hexagonal_architecture.global.exception;

import jakarta.validation.ConstraintViolationException;
import org.ohgiraffers.module01hexagonal_architecture.global.error.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * [예외 핸들러 1] 도메인 예외 처리 (Application/Domain 계층에서 발생)
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiErrorResponse> handleDomainException(DomainException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiErrorResponse body = ApiErrorResponse.of(
                status.value(),
                "DOMAIN_VALIDATION_ERROR",
                ex.getMessage()
        );

        return ResponseEntity.status(status).body(body);
    }

    /*
     * [예외 핸들러 2] spring validation 예외
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .toList();

        ApiErrorResponse body = ApiErrorResponse.withErrors(
                status.value(),
                "INVALID_REQUEST",
                "요청 데이터가 유효하지 않습니다.",
                errors
        );

        return ResponseEntity.status(status).body(body);
    }

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }

    /*
     * [예외 3] PathValue, ParamValue
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<String> errors = ex.getConstraintViolations().stream().map(violation -> {
            String path = violation.getPropertyPath().toString();
            String msg = violation.getMessage();
            return path + ": " + msg;
        }).toList();

        ApiErrorResponse body = ApiErrorResponse.withErrors(
                status.value(),
                "INVALID_REQUEST",
                "요청 데이터가 유효하지 않습니다.",
                errors
        );

        return ResponseEntity.status(status).body(body);
    }

    /*
     * [예외 4] 처리되지 않은 모든 예외
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiErrorResponse body = ApiErrorResponse.of(
                status.value(),
                "INTERNAL_SERVER_ERROR",
                "서버 내부 오류가 발생했습니다. 관리자에게 문의해주세요"
        );

        return ResponseEntity.status(status).body(body);
    }
}
