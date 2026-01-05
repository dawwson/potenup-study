package com.ohgiraffers.doubles.chap01.section02.service;

import com.ohgiraffers.doubles.chap02.section01.model.Product;
import com.ohgiraffers.doubles.chap02.section01.repository.ProductRepository;

/*
 * [전제 2] SUT (테스트 대상)
 * - 'IProductRepository' 인터페이스에 '느슨하게' 의존한다.
 * - '생성자 주입'을 통해 '외부'에서 의존성을 주입받는다.
 */
public class ProductService {
    private final ProductRepository repository;

    // '생성자 주입': 이 '틈'으로 'Mock 객체'가 주입된다.
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /*
     * [TDD 1] '상태 검증(State Verification)' 대상 로직
     * - "리포지토리에서 '찾아온' 객체"를 '그대로' 반환한다.
     * - (우리의 테스트는 '반환된 상태(Product)'가 올바른지 검증해야 한다.)
     */
    public Product getProductById(Long id) {
        return repository.findById(id);
    }

    /*
     * [TDD 2] '행위 검증(Behavior Verification)' 대상 로직
     * - "새 상품(ID가 null)일 때만 'save'를 호출한다."
     * - (우리의 테스트는 'repository.save()'가 '호출되었는지(혹은 안 되었는지)' 검증해야 한다.)
     */
    public Product registerProduct(Product product) {
        if (product.getId() != null) {
            // '가드 클로즈(Guard Clause)': ID가 이미 있으면 예외
            throw new IllegalArgumentException("새 상품은 ID가 없어야 합니다.");
        }
        // ... (기타 비즈니스 로직) ...

        // '핵심 행위': 이 호출이 '발생'했는지 검증해야 한다.
        return repository.save(product);
    }
}