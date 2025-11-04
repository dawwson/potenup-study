package org.ohgiraffers.springdata.chap02.service;

import org.ohgiraffers.springdata.chap02.repository.ProductRepository;
import org.ohgiraffers.springdata.common.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("chap02-service")
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Product findProductById(Integer id) {
        System.out.println("[service] findProductById 호출 : id = " + id);

        Optional<Product> product = productRepository.findById(id);

        Product findProduct = product.orElseThrow(() -> new IllegalArgumentException("id에 해당하는 제품이 없습니다."));

        return findProduct;
    }

    @Transactional
    public Product createProduct(Product product) {
        System.out.println("[service] createProduct 호출 : product = " + product);

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }
}
