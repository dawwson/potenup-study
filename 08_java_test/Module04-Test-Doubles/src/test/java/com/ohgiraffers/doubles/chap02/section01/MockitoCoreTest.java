package com.ohgiraffers.doubles.chap02.section01;

import com.ohgiraffers.doubles.chap01.section02.service.ProductService;
import com.ohgiraffers.doubles.chap02.section01.model.Product;
import com.ohgiraffers.doubles.chap02.section01.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/*
 * 테스트 더블의 유형
 * 1. Dummy 더미
 * - 역할 : 단지 자리 채우기 용도로 객체가 필요하지만 실제로 사용되지 않는 용도
 *
 * 2. stub
 * - 역할 : 테스트 중 발생하는 호출에 대해 미리 준비된 (하드코딩) 답변을 반환
 *
 * 3. spy
 * - 역할 : stub의 기능을 가지면서 + 어떻게 호출되었는지를 기록
 *
 * 4. Mock
 * - 역할 : 호출될 행위에 대한 기대를 미리 명세
 *
 * 5. fake
 * - 역할 실제 구현을 단순화하여 대체
 */

/*
 * BDD (행위 주도 개발)
 * 행위 주도 개발은 소프트웨어 개발 방법론 중 하나로, 테스트를 작성할 때 시나리오 기반 접근 방식을 강조한다.
 * 개발자는 시스템이 어떻게 행동해야 하는지에 대한 스토리를 작성하고, 이를 기반으로 테스트를 설계한다.
 *
 * BDDMockito란?
 * - Mockito에서 행위 주도 개발(Behavior-Driven Development, BDD) 스타일로 작성할 수 있도록 도와주는 API이다.
 * - 기존의 Mockito.when() / Mockito.verify() 구문과 기능은 동일하지만,
 *   테스트의 의도와 흐름(스토리라인)을 "given-when-then" 패턴에 맞게 표현할 수 있다.
 *
 * 1. BDD와 연결
 * - BDD는 TDD의 확장 개념이다.
 * - TDD가 무엇을 테스트할까에 집중한다면,
 *   BDD는 시스템이 어떤 행동을 보여야 하는가?에 집중한다.
 *
 * - 즉, 테스트를 명령이 아닌 이야기처럼 읽게 만드는 것이 목적이다.
 *   예) Given(상황이 주어졌을 때) -> When(어떤 행동을 하면) -> Then(이런 결과가 나와야 한다.)
 *
 * 2. BDDMockito의 주요 메서드
 * - given() : 기존의 when()과 동일 (테스트의 사전 조건 설정)
 * - then() : 기존의 verify()와 동일 (테스트 기대 행동 검증)
 *
 * ------
 * (기존 mockito)
 * xxx일 때 (when : 언제 또는 경우에) xxx를 반환한다.
 * when(repository.findById(1)).thenReturn(new Product(테스트 상품));
 *
 * verify(repository).findById(1);
 *
 * (BDDMockito) : 스토리로 테스트 코드 작성
 * 주어진 레포지토리는 다음을 반환할 것이다.
 * given(repository.findById(1).willReturn(new Product("테스트 상품"));
 *
 * then(repository).should().findById(1)
 *
 * 기능은 완전히 동일하지만
 * "행동 기반의 문장"으로 읽히기 때문에 테스트 목적이 더 명확해진다.
 *
 *
 * BDDMockito 사용 권장 이유
 * 1. 가동성 : 테스트 코드를 자연어처럼 읽을 수 있다.
 * 2. 유지보수성 : 테스트 시나리오를 문서화된 요구사항처럼 재활용이 가능하다.
 * 3. 일관성 : 팀 내에서 모든 Mockito 코드가 동일한 BDD 스타일로 표현되면 "테스트 시나리오"와 "요구사항 문서"의 구조가 일치하게 된다.
 */
@ExtendWith(MockitoExtension.class)
public class MockitoCoreTest {
    /*
     * @Mock : 가짜 의존 객체
     * 1. 목적 : 테스트 대상(sut)을 고립시키기 위해, sut이 의존하는 가짜 객체를 생성한다.
     * 2. 기능 : 이 가짜 객체는 프로그래밍이 가능하다.
     *   - given() : 특정 메서드 호출 시 반환할 값/예외를 미리 정의
     *   - then() : 특정 메서드가 호출되었는지 검증한다.
     * 3. 원리 : 실제 DB에 접근하지 않고, 메모리 상에서만 동작을 흉내낸다.
     */
    @Mock
    private ProductRepository productRepository;

    /*
     * @InjectMocks : SUT 생성 및 Mock 자동 주입
     * 1. 목적 : 테스트 대상 객체의 생성과 의존성 주입을 자동화한다.
     * 2. 동작 : SUT의 생성자를 찾아 현재 테스트 클래스 내에 선언된 모든 @Mock 객체를 자동으로 주입한다.
     * 3. 개발자가 setup() 메서드에서 ProductService = new ProductService(repository);
     *    를 하지 않는다.
     */
    @InjectMocks
    private ProductService service;

    @Test
    @DisplayName("Mock 객체가 성공적으로 주입되는지 검증")
    void shouldCreateMocks_whenUsingAnnotation() {
        Assertions.assertNotNull(service, "productService가 null 입니다.");
        Assertions.assertNotNull(productRepository, "productRepository가 null 입니다.");
    }

    @Test
    @DisplayName("Stubbing(Given/Return) : findById 호출 시, 정의된 객체를 반환")
    void shouldReturnStubbedDate_WhenMethodIsCalled() {

        // 반환값 준비
        Product stubbedProduct = new Product(1L, "테스트 상품");

        // 사용자가 1번을 조회하면 stubbedProduct 반환한다.
        BDDMockito.given(productRepository.findById(1L)).willReturn(stubbedProduct);

        Product foundProduct = service.getProductById(1L);

        Assertions.assertEquals(foundProduct, stubbedProduct, "결과가 다릅니다.");
    }

    @Test
    @DisplayName("Stubbing(Given/Throw) : FindById(99) 호출 시, 정의된 예외를 발생")
    void shouldThrowException_WhenStubbedToThrow() {
        BDDMockito.given(productRepository.findById(99L)).willThrow(new RuntimeException("상품 없음"));

        Assertions.assertThrows(RuntimeException.class, () -> {
            service.getProductById(1L);
        });
    }

    @Test
    @DisplayName("Verification(Then/should) : registerProduct 호출 시, save가 1번 호출된다.")
    void shouldCallSave_WhenRegisteringProduct() {
        Product newProduct = new Product(null, "새 상품");

        service.registerProduct(newProduct);

        BDDMockito.then(productRepository)
                .should(BDDMockito.times(1)) // 정확하게 1번 호출되었는지 검증
                .save(BDDMockito.any(Product.class));  // save가 호출되고, Product 클래스로 전달됐는지 검증
    }

    @Test
    @DisplayName("Verification(Then/Never) : ID가 있는 상품 등록 시, Save가 절대 호출되지 않음")
    void shouldNotCallSave_WhenProductIdIsNotNull() {
        Product invalidProduct = new Product(1L, "이미 ID가 있는 상품");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.registerProduct(invalidProduct);
        });

        BDDMockito.then(productRepository)
                .should(BDDMockito.never())
                .save(BDDMockito.any(Product.class));
    }
}
