package com.ohgiraffers.chap01.section03;

import com.ohgiraffers.chap01.section03.config.AppConfig;
import com.ohgiraffers.chap01.section03.service.PaymentsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("=== 싱글톤 스코프 테스트 ===");
        /*
         * 싱글톤 스코프에서 singlePay 빈이 단일 인스턴스로 생성된다.
         * 따라서 singlePay와 singlePay2는 동일한 객체를 참조하며,
         * processPayment() 호출로 상태가 변경되면 모든 참조에서 공유된다.
         *
         * - 상태를 가지지 않는 서비스 로직 : 예를 들어, 결제 로직 자체는 상태를 저장하지 않고 외부 DB나 캐시에 의존
         * - 공통 설정 객체 : 애플리케이션 전반에서 동일한 설정(예: API 키, 환경 변수)를 공유해야 할 때
         * - 메모리 효율성을 극대화해야 할 때 : 단일 인스턴스로 모든 요청을 처리해 자원 낭비를 줄임
         */
        PaymentsService singlePay = context.getBean("singlePay", PaymentsService.class);
        singlePay.processPayment("ORDER-123", 2000);
        System.out.println("single pay의 마지막 주문 ID : " + singlePay.getLastOrderId());
        // single pay의 마지막 주문 ID : ORDER-123

        PaymentsService singlePay2 = context.getBean("singlePay", PaymentsService.class);
        System.out.println("single pay2의 마지막 주문 ID : " + singlePay.getLastOrderId());
        // single pay2의 마지막 주문 ID : ORDER-123
        // => 같은 객체이므로 결제 처리가 없었음에도 동일하게 출력됨

        System.out.println(singlePay == singlePay2); // true
        System.out.println();

        System.out.println("=== 프로토타입 스코프 테스트 ===");
        /*
         * 프로토타입 스코프에서는 protoPay 빈이 요청 시마다 새 인스턴스로 생성된다.
         * protoPay와 protoPay2는 서로 다른 객체이므로, 상태가 독립적으로 유지된다.
         *
         * - 상태를 가지는 (stateful) 객체: 예를 들어 사용자별 결제 세션, 주문별 임시 데이터 저장
         * - 요청별 독립적인 처리 : 각 HTTP 요청마다 별도이 결제 상태를 유지해야 할 때 (웹 애플리케이션에서 유용)
         * - 테스트 환경 : 단위 테스트에서 독립적인 객체를 생성해 테스트 케이스 간 간섭을 방지
         */
        PaymentsService protoPay = context.getBean("protoPay", PaymentsService.class);
        protoPay.processPayment("ORDER-123", 2000);
        System.out.println("proto pay의 마지막 주문 ID : " + protoPay.getLastOrderId());
        // proto pay의 마지막 주문 ID : ORDER-123

        PaymentsService protoPay2 = context.getBean("protoPay", PaymentsService.class);
        System.out.println("proto pay2의 마지막 주문 ID : " + protoPay2.getLastOrderId());
        // proto pay2의 마지막 주문 ID : null
        // => 한 번 객체를 생성해서 주고 그 이후로 context에서 관리하지 않음

        System.out.println(protoPay == protoPay2); // false
        System.out.println();
    }
}
