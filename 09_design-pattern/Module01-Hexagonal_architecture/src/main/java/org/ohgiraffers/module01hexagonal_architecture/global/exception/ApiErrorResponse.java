package org.ohgiraffers.module01hexagonal_architecture.global.exception;

import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record ApiErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String code,
        String message,
        List<String> errors
) {

    public ApiErrorResponse {
        if (errors == null) {
            errors = Collections.emptyList();
        } else {
            errors = List.copyOf(errors);
        }

        Objects.requireNonNull(timestamp, "time은 null일 수 없습니다.");
        Objects.requireNonNull(code, "code는 null일 수 없습니다.");
        Objects.requireNonNull(message, "message는 null일 수 없습니다.");
    }

    public static ApiErrorResponse of(int status, String code, String message) {

        return new ApiErrorResponse(OffsetDateTime.now(), status, code, message, Collections.emptyList());
    }

    public static ApiErrorResponse withErrors(int status, String code, String message, List<String> errors) {

        return new ApiErrorResponse(
                OffsetDateTime.now(),
                status,
                code,
                message,
                errors
        );
    }
}

