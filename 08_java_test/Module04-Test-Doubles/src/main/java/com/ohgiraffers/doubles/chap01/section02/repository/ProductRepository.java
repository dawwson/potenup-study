package com.ohgiraffers.doubles.chap01.section02.repository;

import com.ohgiraffers.doubles.chap01.section02.model.Product;

/*
 * [리팩토링 1: 추상화(Interface) 추출]
 * - 'ProductRepository'의 '기능(계약)'을 인터페이스로 추출한다.
 * - 'ProductService'는 이제 '이 인터페이스'에만 의존하게 된다.
 */
public interface ProductRepository {
    Product findById(long id);
}