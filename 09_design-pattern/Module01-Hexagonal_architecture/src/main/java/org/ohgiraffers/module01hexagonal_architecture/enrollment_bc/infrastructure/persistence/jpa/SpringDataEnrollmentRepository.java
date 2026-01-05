package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.infrastructure.persistence.jpa;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.model.entity.Enrollment;
import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.repository.EnrollmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataEnrollmentRepository extends JpaRepository<Enrollment, Long>, EnrollmentRepository {

    @Override
    Optional<Enrollment> findByMemberEmailAndCourseId(String memberEmail, Long courseId);

    @Override
    Optional<Enrollment> findById(Long id);

    @Override
    List<Enrollment> findAllByMemberEmail(String memberEmail);
}
