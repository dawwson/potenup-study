package org.ohgiraffers.junit.chap03.service;

import org.ohgiraffers.junit.chap03.model.Product;
import org.ohgiraffers.junit.chap03.repository.ProductRepository;
import org.springframework.stereotype.Service;

/*
 * [수업용] 서비스 클래스
 * - `@SpringBootTest` (통합 테스트) 시: 이 클래스는 '실제 Bean'으로 로드됩니다.
 * - `@WebMvcTest` (웹 계층 테스트) 시: 이 클래스는 로드되지 '않으므로',
 *   Controller에서 `@MockBean`으로 '가짜 Bean'을 만들어 주입해야 합니다.
 *
 * [메모용(강사)]: `@SpringBootTest` 시 이 클래스는 *실제 Bean*으로 생성됩니다.
 * (단, `@WebMvcTest` 사용 시에는 `@MockBean`으로 만들어야 함)
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    // [수업용] 생성자 주입
    // - Spring 컨테이너가 `@Repository` Bean을 찾아 자동으로 주입(DI)합니다.
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductById(long id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new IllegalArgumentException("상품 없음: " + id);
        }
        // (가상) 서비스 로직: 상품 이름에 "[Product]" 접두사 붙이기
        product.setName("[Product] " + product.getName());
        return product;
    }

    public Product createProduct(Product product) {
        // (가상) 서비스 로직: 새 상품은 가격이 0 이상이어야 함
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }

        return productRepository.save(product);
    }
}