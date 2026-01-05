package org.ohgiraffers.junit.chap03;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.BDDMockito;
import org.ohgiraffers.junit.chap03.model.Product;
import org.ohgiraffers.junit.chap03.repository.ProductRepository;
import org.ohgiraffers.junit.chap03.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/*
 * 1. 단위 테스트 - 부품 테스트
 * - 클래스 또는 메서드 하나의 논리
 * - 빠르고, 고립되어 있다.
 * - 환경 spring 컨테이너를 로드하지 않는다.
 * - 의존성 Mockito를 사용하여 모든 외부 의존성을 가짜로 만든다.
 *
 * 2. 통합 테스트 = 완제품
 * - 여러 컴포넌트가 조립되어 하나의 시나리오를 테스트한다.
 * - 느리지만 실제 환경과 유사하게 동작한다.
 * - SpringBootTest 어노테이션을 활용해 Spring container를 전부 로드한다.
 * - 실제 Bean을 주입받아 사용된다.
 */

/*
 * @SpringBootTest
 * - Junit 테스트에서 Spring Boot의 전체 ApplicationContext를 완전히 로드한다.
 * - 테스트가 시작될 때, 실제 애플리케이션이 부팅되는 것과 동일하게 모든 Bean(@Service, @Repository)를 생성하고 DI를 완료한다.
 * - 테스트 코드 내에서 @Autowired를 사용하여 실제 Bean을 주입받아 사용할 수 있게 된다.
 */
@SpringBootTest
/*
 * @AutoConfigureMockMvc
 * - SpringBootTest와 함께 사용하며, 웹 계층을 네트워크 없이 테스트할 수 있는 MockMVC 객체를 자동으로 설정한다.
 * - @Autowired를 통해 MockMVC 객체를 주입받을 수 있도록 해준다.
 * - [MockMVC] 가짜 HTTP 요청을 시뮬레이션하며 Controller의 API를 테스트할 수 있도록 해주는 도구이다.
 */
@AutoConfigureMockMvc
class Module05JunitWithSpringApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /*
     * MockitoBean
     * @SpringBootTest로 로드된 ApplicationContext 내부의 실제 Bean을
     * Mockito mock으로 대체한다.
     * (spring boot 3.4부터 @MockBean -> MockitoBean으로 변경되었음
     *
     * @Mock vs @MockitoBean
     * - Mock
     * 순수 단위 테스트용
     * 스프링 컨테애너 없이 독립적으로 Mockito Mock 객체를 생성한다.
     *
     * - MockitoBean
     * 통합 테스트용
     * 스프링 컨텍스트 내부의 실제 Bean을 mock 객체로 교체한다.
     * SpringBootTest 환경에서 Mock 의존성을 주입할 때 사용한다.
     *
     * - 용도
     * Controller 테스트 시 service/repository 계층의 실제 로직을 차단하고
     * 가짜 응답을 반환하도록 설정해 Controller의 요청 매핑. 데이터 변환 로직만 검증하고 싶은 경우 사용한다.
     */
    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private ProductService productService;

    @Test
    @DisplayName("GET /api/v1/products/{id} - 상품 ID로 조회 시 200 OK와 상품 Json 반환")
    void shouldReturnAndProduct_WhenRequestingProductById() throws Exception {
        Long productId = 1L;
        Product product = new Product(productId, "테스트 상품", 10000);

        BDDMockito.given(productService.findProductById(productId)).willReturn(product);

        /*
         * 실제 HTTP 요청을 보내지 않고 DispatcherServlet을 직접 호출하여 요청을 처리하는 과정을 시뮬레이션 한다.
         */
        ResultActions actions = mockMvc.perform(
                MockMvcTester.MockMvcRequestBuilder.get("/api/v1/products/{id}", productId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        /*
         * andDo() : 응답 내용을 콘솔 등에 출력하여 디버깅 용도로 사용한다.
         *
         * andExpert() : HTTP 응답 상태, 헤더, 본문(json/xml) 등 응답 결과를 검증
         *
         * andReturn() : 최종적으로 MVCResult 객체를 반환하여 응답 객체 자체에 대한 더 복잡한 접근이 필요할 때 사용된다.
         */
        actions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()) // 200
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("테스트 상품"));
    }

    @Test
    @DisplayName("POST /api/v1/products: 새 상품 등록 시 201 Created와 생성된 상품 JSON 반환")
    void shouldReturnCreatedAndProduct_WhenPostingNewProduct() throws Exception {

        Product requestDto = new Product(0L, "새 상품", 15000);
        Product responseDto = new Product(1L, "새 상품", 15000);

        BDDMockito.given(productService.createProduct(BDDMockito.any(Product.class))).willReturn(responseDto);

        String jsonContent = objectMapper.writeValueAsString(requestDto);

        ResultActions actions = mockMvc.perform(MockMvcTester.MockMvcRequestBuilder.post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .accept(MediaType.APPLICATION_JSON)
        );

        actions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("새 상품"));
    }

    @Test
    @DisplayName("GET /admin/resource: 유효하지 않은 토큰으로 접근 시 403 forbidden 반환")
    void shouldReturnForbidden_WhenTokenIsInvalid() throws Exception {
        String invalidToken = "invalid-jwt-token";

        ResultActions actions = mockMvc.perform(
                MockMvcTester.MockMvcRequestBuilder.get("/admin/resource")
                        .header("Authorization", "Bearer " + invalidToken)
        );

        actions
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}