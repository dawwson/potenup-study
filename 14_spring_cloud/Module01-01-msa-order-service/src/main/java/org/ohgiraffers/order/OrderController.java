package org.ohgiraffers.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/orders")
public class OrderController {

    /*
     * RestClient란?
     * RestClient는 Spring Framework에서 제공하는 HTTP 클라이언트로,
     * RESTful 웹 서비스와의 통신을 간편하게 처리할 수 있도록 도와준다.
     * 이를 통해 개발자는 HTTP 요청을 생성하고, 서버로부터 응답을 받아 처리하는 작업을 쉽게 수행할 수 있다.
     * RestClient는 비동기 및 동기 방식의 요청을 모두 지원하며, JSON, XML 등 다양한 데이터 형식을 처리할 수 있다.
     * 또한, 인증, 헤더 설정, 오류 처리 등 다양한 기능을 제공하며 RESTful API와의 상호작용을 효율적으로 관리할 수 있다.
     *
     * 주요 메서드
     * .get() 메서드
     * : HTTP GET 요청을 생성하는 데 사용된다. 이는 서버로부터 데이터를 조회할 때 주로 사용된다.
     *
     * .post() 메서드
     * : HTTP POST 요청을 생성하는 데 사용된다. 이는 서버에 데이터를 전송할 때 주로 사용된다.
     *
     * .put() 메서드
     * : HTTP PUT 요청을 생성하는 데 사용된다. 이는 서버의 기존 데이터를 업데이트할 때 주로 사용된다.
     *
     * .delete() 메서드
     * : HTTP DELETE 요청을 생성하는 데 사용된다. 이는 서버의 데이터를 삭제할 때 주로 사용된다.
     *
     * .uri() 메서드
     * : 요청을 보낼 대상 URI(Uniform Resource Identifier)를 지정하는 데 사용된다.
     *   여기서는 외부 서비스의 엔드포인트를 설정한다.
     *
     * .retrieve() 메서드
     * : 지정된 uri로 요청을 보내고, 서버로부터 응답을 받아온다.
     *   이 메서드는 응답을 처리하기 위한 후속 작업을 수행할 수 있도록 한다.
     *
     * .body(*.class) 메서드
     * : 서버로부터 받은 응답 본문을 지정된 클래스 타입으로 변환한다.
     *   이를 통해 응답 데이터를 자바 객체로 쉽게 다룰 수 있다.
     */
    private final RestClient restClient = RestClient.create();
    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    public OrderResult createOrder(@RequestBody OrderRequest request) {

        ProductResponse product = restClient.get()
                .uri("http://localhost:8082/products/" + request.productId())
                .retrieve()
                .body(ProductResponse.class);

        logger.info("[createOrder] product = " + product);
        int totalPrice = product.price() * request.quantity();

        return new OrderResult("order-123", totalPrice);
    }
}

record OrderRequest(String productId, int quantity) {}
record ProductResponse(String id, String name, int price) {}
record OrderResult(String orderId, int totalPrice) {}
