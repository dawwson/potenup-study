package org.ohgiraffers.junit.chap03.controller;

import org.ohgiraffers.junit.chap03.model.Product;
import org.ohgiraffers.junit.chap03.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * [수업용] 컨트롤러 클래스
 * - `@RestController`: 이 클래스가 REST API 요청을 처리하는 컨트롤러 Bean임을 선언합니다.
 * (@Controller + @ResponseBody)
 * - `@RequestMapping`: 이 컨트롤러의 모든 API는 `/api/v1/products` 경로 하위에 매핑됩니다.
 * [메모용(강사)]: 이 클래스가 `MockMvc` 테스트의 핵심 대상(SUT)입니다.
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    // [수업용] 생성자 주입
    // - Spring 컨테이너가 `@Service` Bean을 찾아 자동으로 주입(DI)합니다.
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /*
     * [수업용] GET /{id} : 상품 ID로 조회
     * - `@GetMapping("/{id}")`: GET 요청과 URL 경로 변수(id)를 매핑합니다.
     * - `@PathVariable`: URL의 {id} 값을 메서드 파라미터(id)로 받습니다.
     * - `ResponseEntity<Product>`: HTTP 상태 코드와 JSON 본문(Product)을 함께 반환합니다.
     *
     * MedioaType.APPLICATION_JSON_VALUE: 응답의 Content-Type을 application/json으로 설정
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> findProductById(@PathVariable("id") long id) {

        try {
            Product product = productService.findProductById(id);
            return ResponseEntity.ok(product); // 📌 성공 시 200 OK + Product JSON
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // 📌 실패(상품 없음) 시 404 Not Found
        }
    }

    /*
     * [수업용] POST / : 새 상품 등록
     * - `@PostMapping`: POST 요청을 매핑합니다.
     * - `@RequestBody`: HTTP Body에 담긴 JSON 데이터를 `Product` 객체로 자동 변환(Binding)합니다.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            // 📌 성공 시 201 Created + 생성된 Product JSON
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 📌 실패(가격 < 0 등) 시 400 Bad Request
        }
    }
}
