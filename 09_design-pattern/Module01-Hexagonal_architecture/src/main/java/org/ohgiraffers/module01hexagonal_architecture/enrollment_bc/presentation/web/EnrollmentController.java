package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.presentation.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.ApproveEnrollmentUseCase;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.CancelEnrollmentUseCase;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.RejectEnrollmentUseCase;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.RequestEnrollmentUseCase;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.command.DecideEnrollmentCommand;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.command.RequestEnrollmentCommand;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.presentation.web.dto.EnrollmentDecisionRequest;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.presentation.web.dto.EnrollmentIdResponse;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.presentation.web.dto.EnrollmentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final RequestEnrollmentUseCase requestEnrollmentUseCase;
    private final ApproveEnrollmentUseCase approveEnrollmentUseCase;
    private final RejectEnrollmentUseCase rejectEnrollmentUseCase;
    private final CancelEnrollmentUseCase cancelEnrollmentUseCase;

    public EnrollmentController(RequestEnrollmentUseCase requestEnrollmentUseCase, ApproveEnrollmentUseCase approveEnrollmentUseCase, RejectEnrollmentUseCase rejectEnrollmentUseCase, CancelEnrollmentUseCase cancelEnrollmentUseCase) {
        this.requestEnrollmentUseCase = requestEnrollmentUseCase;
        this.approveEnrollmentUseCase = approveEnrollmentUseCase;
        this.rejectEnrollmentUseCase = rejectEnrollmentUseCase;
        this.cancelEnrollmentUseCase = cancelEnrollmentUseCase;
    }

    @PostMapping
    public ResponseEntity<EnrollmentIdResponse> request(@Valid @RequestBody EnrollmentRequest request) {

        Long id = requestEnrollmentUseCase.request(new RequestEnrollmentCommand(request.memberEmail(), request.courseId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(new EnrollmentIdResponse(id));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable("id") @NotNull Long id) {

        approveEnrollmentUseCase.approve(new DecideEnrollmentCommand(id, null));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> reject(
            @PathVariable("id") @NotNull Long id,
            @Valid @RequestBody EnrollmentDecisionRequest request
    ) {

        rejectEnrollmentUseCase.reject(new DecideEnrollmentCommand(id, request.reason()));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable("id") @NotNull Long id) {

        cancelEnrollmentUseCase.cancel(id);

        return ResponseEntity.ok().build();
    }
}
