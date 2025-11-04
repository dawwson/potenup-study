package org.ohgiraffers.springdata.chap01.section02.controller;

import org.ohgiraffers.springdata.chap01.section02.service.ProductService;
import org.ohgiraffers.springdata.common.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * 1. controller layer (표현 계층)
 *   - 애플리케이션의 관문으로 사용자의 HTTP 요청을 최초로 수신하는 진입점이다.
 *   - @GetMapping, @PostMapping 등과 같은 매핑 어노테이션을 통해 특정 url 요청을 처리할 메서드를 연결한다.
 *
 * 2. 핵심 역할 : 조율 및 위임
 *   (1) 요청 처리 : 요청 파라미터(@RequestParam), 경로 변수(@PathVariable)
 *     - 요청 본문(@RequestBody) 등을 자바 객체로 변환한다.
 *
 *   (2) 서비스 호출 : 실제 비즈니스 로직을 처리하는 "Service Layer"의 적절한 메서드를 호출한다.
 *   (3) 응답 생성 : 서비스 계층에서 반환된 데이터를 적절한 형식(JSON, XML, HTML 등)으로 변환하여
 *
 * 3. 관심사 분리
 *   - 컨트롤러는 비즈니스 로직이나 데이터 접근 로직을 절대 직접 수행하지 않는다.
 *   - 컨트롤러는 조율자이며 모든 실제 작업은 Service에게 위임된다.
 *
 * 4. @Controller vs @RestController
 *   - @Controller : (전통적) 주로 view(HTML 페이지)를 반환할 때 사용
 *                   메서드 반환값은 뷰 이름이 된다.
 *   - @RestController : (현대 API) @Controller + @ResponseBody
 *                       주로 JSON, XML과 같은 데이터를 반환할 때 사용되거나 REST API 서버에 사용된다.
 *                       메서드 반환값(객체)가 응답 본문에 직력화되어 들어간다.
 */
@RestController  // @Controller + @ResponseBody
@RequestMapping("/chap01/section02/")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        System.out.println("✅ productController 생성");
        this.productService = productService;
    }

    @GetMapping("/products/cheap")
    public List<String> findCheapProductNames(Integer maxPrice) {
        System.out.println("\n[controller] findCheapProductNames 호출 : maxPrice = " + maxPrice);

        List<String> productNames = productService.findProductCheaperThan(maxPrice)
                .stream()
                .map(product -> product.getProductName()) // .map(Product::getProductName) 와 동일함
                .toList();

        System.out.println("[controller] 응답할 상품명 등록 : " + productNames);
        return productNames;
    }

    @GetMapping("/products/{productId}")
    public Product findProduct(@PathVariable  Integer productId) {
        System.out.println("\n[controller] findProductById 호출 : productId = " + productId);

        Product product = productService.findProductById(productId);

        System.out.println("[controller] 응답할 상품명 등록 : " + product);
        return product;
    }
}
