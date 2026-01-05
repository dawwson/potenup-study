package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.repository;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.model.entity.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository {

    Enrollment save(Enrollment enrollment);

    Optional<Enrollment> findById(Long id);

    Optional<Enrollment> findByMemberEmailAndCourseId(String memberEmail, Long courseId);

    List<Enrollment> findAllByMemberEmail(String memberEmail);
}
