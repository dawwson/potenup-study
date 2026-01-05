package com.ohgiraffers.doubles.chap02.section01.repository;

import com.ohgiraffers.doubles.chap02.section01.model.Product;

public interface ProductRepository {
    Product findById(Long id);
    Product save(Product product);
}
