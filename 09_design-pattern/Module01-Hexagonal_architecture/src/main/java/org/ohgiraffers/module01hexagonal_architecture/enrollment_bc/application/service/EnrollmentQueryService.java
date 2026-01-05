package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.service;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.FindEnrollmentUseCase;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.dto.EnrollmentResponse;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.in.dto.EnrollmentSummaryResponse;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.exception.EnrollmentException;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.model.entity.Enrollment;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EnrollmentQueryService implements FindEnrollmentUseCase {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentQueryService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public EnrollmentResponse findById(Long id) {

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentException("해당 수강신청을 찾을 수 없습니다."));

        return EnrollmentResponse.from(enrollment);
    }

    @Override
    public List<EnrollmentSummaryResponse> findByMemberEmail(String memberEmail) {

        return enrollmentRepository.findAllByMemberEmail(memberEmail)
                .stream()
                .map(EnrollmentSummaryResponse::from)
                .toList();
    }
}
