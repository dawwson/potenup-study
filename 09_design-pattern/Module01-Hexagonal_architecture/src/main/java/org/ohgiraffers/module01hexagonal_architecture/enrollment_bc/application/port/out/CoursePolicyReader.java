package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.out;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.domain.model.entity.Enrollment;

public interface CoursePolicyReader {

    boolean isCoursePublished(Long courseId);

    boolean hasCapacity(Long courseId);

    boolean isPaidCourse(Long courseId);
}
