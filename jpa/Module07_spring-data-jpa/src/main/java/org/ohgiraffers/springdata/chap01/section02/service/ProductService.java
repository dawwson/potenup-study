package org.ohgiraffers.springdata.chap01.section02.service;

import org.ohgiraffers.springdata.chap01.section02.repository.ProductRepository;
import org.ohgiraffers.springdata.common.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * 1. Service Layer(비즈니스 계층)
 *   - 애플리케이션의 핵심 비즈니스 로직을 처리한다.
 *   - 트랜잭션을 관리하는 단위이다.
 *
 * 2.책임과 위임
 *   - HTTP 요청/응답 처리는 controller에게 데이터 영속성 처리는 repository에게 위임한다.
 *   - 서비스는 이 둘을 조율하는 역할을 하게 된다.
 *
 * 3. Service 어노테이션
 *   - 이 클래스를 스프링 Bean으로 등록한다. (@Component 특수화)
 *
 * 4. @Transactional 어노테이션
 *   - 메서드가 포함된 트랜잭션의 경계를 설정한다.
 *   - 메서드 시작 시 트랜잭션을 시작하고
 *   - 성공적으로 종료되면 커밋, 예외 발생 시 롤백을 자동으로 수행한다.
 *
 * 5. @Transactional(readOnly=true)
 *   - 조회 전용 트랜잭션임을 명시하여 성능 최적화
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        System.out.println("✅ productService 생성");
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findProductCheaperThan(Integer maxPrice) {
        List<Product> allProducts = productRepository.findAll();

        System.out.println("[service] 가격 필더링 로직 수행...");

        return allProducts.stream()
                .filter(product -> product.getPrice() <= maxPrice)
                .toList();
    }

    @Transactional(readOnly = true)
    public Product findProductById(Integer productId) {
        System.out.println("[service] findProductById 호출 : productId = " + productId);

        return productRepository.findById(productId);
    }
}
