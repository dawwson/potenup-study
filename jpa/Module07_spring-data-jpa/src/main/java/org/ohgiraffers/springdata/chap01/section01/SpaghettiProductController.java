package org.ohgiraffers.springdata.chap01.section01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.ohgiraffers.springdata.common.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller // 🚨 주의: 실제로는 이렇게 작성하면 안 됩니다! 역할 분리 예시를 위한 안티 패턴입니다.
public class SpaghettiProductController {

    private final EntityManagerFactory emf; // 데이터 접근을 위해 EntityManagerFactory 직접 주입 (🚨 안티 패턴)

    // Spring 컨테이너가 EntityManagerFactory Bean을 자동으로 주입
    @Autowired
    public SpaghettiProductController(EntityManagerFactory emf) {
        System.out.println("✅ SpaghettiProductController 생성: EntityManagerFactory 주입됨");
        this.emf = emf;
    }

    /*
     * 실행 후 생각해보기:
     * 만약 필터링 조건을 가격 외에 다른 것(e.g., 상품명 키워드)으로 추가하려면 이 코드를 어떻게 수정해야 할까요?
     * 데이터베이스 대신 다른 저장소(e.g., 외부 API)에서 상품 정보를 가져오려면 코드를 얼마나 바꿔야 할까요?
     * findCheapProductNames 메서드의 가격 필터링 로직만 따로 테스트할 수 있을까요?
     * 이런 질문들에 답하기 어렵다면, 스파게티 코드의 문제점을 체감한 것입니다!
     * */

    // (가정) GET /spaghetti/products/cheap?maxPrice=10000 요청 처리
    @GetMapping("/spaghetti/products/cheap") // 웹 요청 매핑
    @ResponseBody // 결과를 HTTP 응답 본문에 직접 작성 (JSON 변환)
    public List<String> findCheapProductNames(@RequestParam("maxPrice") Integer maxPrice) { // 요청 파라미터 받기
        System.out.println("\nSpaghetti - findCheapProductNames 호출: maxPrice = " + maxPrice);



        // --- 🏛️ 데이터 접근 로직 (원래 Repository 역할) ---
        EntityManager em = emf.createEntityManager(); // EntityManager 획득 (매번 생성)
        List<Product> allProducts;
        try {
            System.out.println("Spaghetti - DB에서 모든 상품 조회 시도...");
            // JPQL 실행
            allProducts = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
            System.out.println("Spaghetti - DB 조회 완료, 총 상품 수: " + allProducts.size());
        } finally {
            em.close(); // EntityManager 반납 (반드시!)
            System.out.println("Spaghetti - EntityManager closed.");
        }


        // --- 💼 비즈니스 로직 (원래 Service 역할) ---
        System.out.println("Spaghetti - 가격 필터링 로직 수행...");
        List<Product> filteredProducts = allProducts.stream()
                .filter(product -> product.getPrice() <= maxPrice) // 가격 비교
                .toList();
        System.out.println("Spaghetti - 필터링 후 상품 수: " + filteredProducts.size());




        // --- 🎮 결과 가공 및 응답 (원래 Controller 역할) ---
        System.out.println("Spaghetti - 결과 가공 (상품명 추출)...");
        List<String> productNames = filteredProducts.stream()
                .map(Product::getProductName) // 이름만 추출
                .collect(Collectors.toList());

        System.out.println("Spaghetti - 최종 응답 데이터: " + productNames);



        return productNames; // 상품명 리스트 반환 (JSON 등으로 변환되어 응답)
    }

    // 💡 참고: 실제 Spring Boot 애플리케이션에서는 애플리케이션 종료 시
    // EntityManagerFactory가 자동으로 관리/종료됩니다.
}