package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.presentation.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.FindEnrollmentUseCase;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.dto.EnrollmentResponse;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.dto.EnrollmentSummaryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentQueryController {

    private final FindEnrollmentUseCase findEnrollmentUseCase;

    public EnrollmentQueryController(FindEnrollmentUseCase findEnrollmentUseCase) {
        this.findEnrollmentUseCase = findEnrollmentUseCase;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> findEnrollment(@PathVariable("id") @NotNull Long id) {

        EnrollmentResponse enrollmentResponse = findEnrollmentUseCase.findById(id);

        return ResponseEntity.ok(enrollmentResponse);
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentSummaryResponse>> findByMemberEmail(
            @RequestParam("memberEmail") @NotBlank @Email String memberEmail
    ) {

        List<EnrollmentSummaryResponse> responses = findEnrollmentUseCase.findByMemberEmail(memberEmail);

        return ResponseEntity.ok(responses);
    }
}
