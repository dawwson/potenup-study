package org.ohgiraffers.springdata.chap05.service;

import org.ohgiraffers.springdata.chap02.repository.ProductRepository;
import org.ohgiraffers.springdata.common.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/*
 * 1. 트랜잭션 격리 수준(Isolation Level)이란?
 *   - 여러 트랜잭션이 동시에 실행될 때
 *   - 서로의 작업 내용에 얼마나 노출될지를 결정하는 안전 장치 레벨이다.
 *
 * 2. 왜 필요한가?
 *   - 동시성과 데이터 일관성은 트레이드오프 관계이다.
 *   - [낮은 격리 수준] : 동시성은 높지만(빠름), 데이터 일관성이 깨질 수 있다.(읽기 오류 발생)
 *   - [높은 격리 수준] : 데이터 일관성은 높지만(안전), 동시성은 낮아진다.
 *
 * 3. Spring에서 설정 방법
 *   - @Transactional 어노테이션의 isolation 속성을 사용하여 설정한다.
 *
 * 4. 격리 수준의 종류
 *   - Isolation.READ_UNCOMMITTED : 커밋되지 않은 데이터도 읽을 수 있음
 *   - Isolation.READ_COMMITTED : 커밋된 데이터만 읽음 (대부분 DB의 기본값)
 *   - Isolation.REPEATABLE_READ : 같은 트랜잭션 내에서 같은 데이터를 여러 번 읽을 때 항상 동일한 결과를 보장. 트랜잭션 내내 반복읽기 보장.
 *   - Isolation.SERIALIZABLE : 가장 높은 격리 수준. 트랜잭션이 순차적으로 실행되는 것처럼 동작. 완전한 직렬화 보장. 트랜잭션을 순차적으로 보장.
 *                              (가장 안전하며, 가장 느림)
 *
 * 5. Isolation.DEFAULT : 기본 데이터베이스 격리 수준 사용
 *   - 나는 Spring에게 맡기지 않고 연결된 DB의 기본 설정을 따르겠다는 의미
 *   - [참고] oracle : READ_COMMITTED, MySQL : REPEATABLE_READ
 *
 * =====================
 *
 * [트랜잭션 격리 수준 필요성]
 * 트랜잭션은 ACID 원칙을 따라야 한다. 그중 I가 Isolation(격리성)이다.
 * 만약 격리성이 완벽하지 않다면 여러 트랜잭션이 동시에 실행될 때 다음과 같은 3가지 읽기 이상 현상이 발생한다.
 *
 * 1. dirty read(더티 리드) - 취소된 주문을 읽다
 *   - [문제] 트랜잭션 A가 데이터를 수정(재고 100 -> 90)하고 아직 커밋하지 않았다.
 *           트랜잭션 B가 이 "90"이라는 임시 데이터를 읽어가는 현상이다.
 *   - [위험] 만약 A가 롤백하면 B는 존재하지 않는 90이라는 데이터를 기반으로 로직을 처리하게 되며 데이터가 꼬인다.(심각!)
 *
 * 2. non-repeatable read(반복 불가능한 읽기) - 재고가 갑자기 바뀌다.
 *   - [문제] 트랜잭션 A가 데이터를 읽었다. (재고 100)
 *           트랜잭션 B가 이 데이터를 수정하고 커밋했다. (재고 100 -> 80)
 *           트랜잭션 A가 다시 같은 데이터를 읽었을 때 (재고 80)
 *   - [위험] 트랜잭션 A는 같은 트랜잭션 내에서 같은 데이터를 읽었지만 다른 값을 받게 된다.
 *           이는 데이터 일관성을 해치며, A의 로직이 잘못된 결과를 초래할 수 있다.
 *
 * 3. phantom read(팬텀 리드) - 없던 데이터가 생겨나다.
 *   - [문제] 트랜잭션 A가 "재고가 100개 이상인 상품"을 조회했더니 5건이 나왔다.
 *           그 직후, 트랜잭션 B가 재고 110개 짜리 새 상품을 추가하고 커밋했다.
 *           잠시 후 A가 같은 조건의 (재고 100개 이상)으로 다시 조회를 했더니 이번에는 6건이 조회단다.
 *   - [위헙] A 입장에서는 유령 처럼 없던 데이터가 생겨난 것이다.
 */
@Service("chap05-isolationTestService")
public class IsolationTestService {
    private final ProductRepository productRepository;

    @Autowired
    public IsolationTestService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 가장 기본적인 격리 레벨로 일반적으로 해당 격리 레벨에서 충분하다.
    @Transactional(isolation = Isolation.DEFAULT, readOnly = true)
    public Product findProductDefault(Integer productId) throws IllegalAccessException {
        System.out.println("Service(Isolation) - findProductDefault() 호출됨. 격리 레벨 default");

        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalAccessException("상품 없음 " + productId));
    }

    /*
     * Dirty Read 방지
     * - 다른 트랜잭션이 커밋한 데이터만 읽을 수 있다.
     * - read 작업이 write 작업을 막지 않는다.
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public Product findProductReadCommitted(Integer productId) throws IllegalArgumentException {
        System.out.println("service(Isolation) - findProductReadCommitted() 호출됨. 격리 레벨 READ_COMMITTED");
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("상품 없음 : " + productId));
    }

    /*
     * non-repeatable read 방지
     * - 한 트랜잭션 내에서 같은 데이터를 여러 번 읽어도 항상 같은 결과를 보장한다.
     * - 트랜잭션이 시작된 시점의 데이터 버전을 스냅샷으로 만들어 트랜잭션이 끝날 때까지 해당 스냅샷만 읽는다.
     * - 이에 따라 반복 읽기가 보장된다.
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ, readOnly = true)
    public Product findProductRepeatableRead(Integer productId) throws IllegalArgumentException {
        System.out.println("service(Isolation) - findProductRepeatableRead() 호출됨. 격리 레벨 REPEATABLE_READ");
        return productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("상품 없음 : " + productId));
    }

    /*
     * 가장 높은 격리 수준 (phantom read 방지)
     * - 트랜잭션을 순차적으로 실행하는 것처럼 동작시켜 동시성 문제를 원천 차단한다.
     * - 동시 처리 성능이 크게 저하될 수 있으므로 매우 신중하게 사용해야 한다.
     * - 예 : 중요한 재고 차감 로직, 금융 거래 처리 등
     * - 다음 설정을 처리하면 Read 작업에도 lock(공유 락)을 건다.
     * - 다른 트랜잭션은 해당 데이터를 읽거나, 쓰거나, 추가할 수 없게 된다.
     * - 이는 phantom read는 방지할 수 있으나 데드락(deadlock) 가능성이 커지고 동시설 처리가 최악이다.
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void processProductSerializable(Integer productId) throws IllegalAccessException {
        System.out.println("service(Isolation) - processProductSerializable() 호출됨. 격리 레벨 SERIALIZABLE");
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("상품 없음 : " + productId));
        System.out.println("조회된 상품 " + product.getProductName());
        System.out.println("(가정) 재고 차감 로직 수행");
        System.out.println("service(Isolation) - processProductSerializable() 종료");
    }
}
