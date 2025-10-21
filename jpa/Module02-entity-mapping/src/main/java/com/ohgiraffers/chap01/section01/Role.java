package com.ohgiraffers.chap01.section01;

import jakarta.persistence.*;

/**
 * @Entity
 * - 해당 클래스가 JPA에서 관리할 수 있는 엔티티 클래스임을 명시
 * - 이 어노테이션이 있어야 JPAsms 객체를 테이블로 인식하고 관리할 수 있다.
 * - 반드시 기본 생성자가 존재해야 한다.
 */
@Entity

/**
 * @Table
 * - 매핑할 테이블 이름을 명시
 * - 생략하면 클래스의 이름을 테이블로 인식 (User -> USER)
 *
 * 주요 속성
 * - name : 실제 테이블 이름
 * - schema : 스키마 지정 (postgreSQL 등에서 사용됨)
 * - uniqueConstraints : 복합 유니크 제약조건 설정
 * - indexes : 인덱스 설정
 */
@Table
public class Role {
    /**
     * id + generatedValue
     * 역할
     * - 엔티티의 기본키 지정
     * - 자동 증가 전략 선택
     * > GeneratedValue:
     *   - Identity : DB가 자동 증가
     *   - Sequence : DB 시퀀스 객체 사용
     *   - table : 별도의 테이블로 키 관리 (성능상 좋지 않음)
     *   - Auto : DB에 따라 자동 선택
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long id;

    /**
     * @Column 어노테이션
     * > 역할 : 필드를 테이블의 컬럼으로 매핑
     * > 주요 속성
     * - name : 컬럼명
     * - nullable : null 허용 여부
     * - unique : 유일 값 여부
     * - length : 문자 길이 제한 (문자열에만 적용됨)
     */
    @Column(name = "role_name", nullable = false, unique = true, length = 50)
    private String name;
}
