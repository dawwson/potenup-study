package com.ohgiraffers.doubles.chap01.section01.repository;

import com.ohgiraffers.doubles.chap01.section01.model.Product;

/*
 * [문제의 원인 1: '무거운' 외부 의존성]
 * - 이 클래스는 '실제 DB'에 연결하는 '무거운' 객체라고 가정한다.
 * - '실제 DB 연결'은 TDD의 'FIRST' 원칙을 정면으로 위반한다.
 *
 * - F (Fast): '느리다'. (DB 커넥션은 수십 ms 이상 소요)
 * - I (Isolated): '고립 실패'. (DB 서버가 다운되면 무관한 테스트가 실패)
 * - R (Repeatable): '반복 불가'. (DB 데이터 상태에 따라 결과가 달라짐)
 */
public class ProductRepository {
    public Product findById(long id) {
        // (가상) "DB 연결... SELECT * FROM product WHERE id = ?"
        System.out.println("!!! [실제 DB 연결 발생] !!! - findById(" + id + ")");

        // '실제' DB에는 1L 데이터가 '없으므로' 항상 'null'을 반환한다고 가정
        return null;
    }
}