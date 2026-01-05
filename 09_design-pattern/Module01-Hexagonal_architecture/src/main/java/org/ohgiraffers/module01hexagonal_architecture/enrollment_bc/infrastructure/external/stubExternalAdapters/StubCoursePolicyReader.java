package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.infrastructure.external.stubExternalAdapters;

import org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.out.CoursePolicyReader;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class StubCoursePolicyReader implements CoursePolicyReader {

    // 0 또는 음수이면 비공개로 간주
    @Override
    public boolean isCoursePublished(Long courseId) {
        // true : 공개 | false : 비공개
        return courseId != null && courseId > 0;
    }

    // 홀수 ID는 정원 여유, 짝수 ID는 정원 초과라고 가정
    @Override
    public boolean hasCapacity(Long courseId) {
        // true : 정원 여유 | false : 정원 초과
        return courseId % 2 == 1;
    }

    // 5의 배수는 유료라고 가정
    @Override
    public boolean isPaidCourse(Long courseId) {
        // true : 유료 | false : 무료
        return courseId % 5 == 0;
    }
}
