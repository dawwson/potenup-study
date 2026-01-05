package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.dto.EnrollmentResponse;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.dto.EnrollmentSummaryResponse;

import java.util.List;

public interface FindEnrollmentUseCase {

    EnrollmentResponse findById(Long id);

    List<EnrollmentSummaryResponse> findByMemberEmail(String memberEmail);
}