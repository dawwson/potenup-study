package com.ohgiraffers.chap01.section02;

/**
 * Enum 타입 선언
 * > enum은 자바의 타입 안정성을 제공하면서 제한된 값만 사용할 수 있도록 강제한다.
 *
 * Enum은 여러 관련 상수를 그룹화하여 정의할 수 있는 데이터 타입이다.
 * 사용자가 정의한 상수들의 집합을 생성할 수 있다.
 */
public enum Role {
    STUDENT,
    INSTRUCTOR,
    ADMIN

    /**
     * Enum의 장점
     * 1. 타입 안정성 : enum 타입을 사용하여 잘못된 값의 할당을 방지한다.
     *   - 예 : Role role = Role.STUDENT;
     * 2. 가독성 : 코드의 의미를 명확하게 전달한다.
     * 3. 네임스페이스 : Role.STUDENT와 같이 네임스페이스를 사용하여 이름 충돌을 피할 수 있다.
     * 4. 반복문 사용 : Enum의 모든 값은 반복할 수 있다.
     */
}
