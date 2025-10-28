package com.ohgiraffers.chap01.section04.service;

/*
 * [PaymentsService]
 * 주요 생명주기 단계
 * 1. 생성 : 객체가 인스턴스화 되는 단계
 * 2. 의존성 주입 : 생성자를 통해 주입함
 * 3. 초기화 : @PostConstruct로 init() 메서드가 호출되어 초기 설정 수행
 * 4. 사용 : processPayment() 메서드를 통해 결제 로직 실행
 * 5. 소멸 : @PreDestroy로 destroy() 메서드가 호출되어 리소스 정리
 */
public class PaymentsService {
    private final PaymentsInterface paymentInterface;

    /*
     * [생성자]
     * - bean 인스턴스 생성 단계에서 호출됨
     * - 주로 의존성 주입(DI)을 수행하거나, 객체 생성 시 필수적인 초기 작업을 처리
     *
     * 예시
     *  - 의존성 객체 주입
     *  - 필수 필드 값 검증(객체가 생성되기 위한 필수 조건)
     */
    public PaymentsService(PaymentsInterface paymentInterface) {
        if (paymentInterface == null) {
            throw new NullPointerException("paymentInterface is null");
        }
        this.paymentInterface = paymentInterface;
        System.out.println("1. bean 생성");
    }

    /*
     * [초기화 메서드]
     * - bean 생성 및 의존성 주입 후 호출됨
     * - 주로 리소스 초기화, 설정 로드, 준비 작업을 수행
     * - 예시
     *   - 데이터베이스 연결 설정
     *   - 캐시 초기화
     *   - 외부 API 인증 토큰 발급
     * - 주의
     *   - 초기화 작업은 bean의 사용 준비를 위한 최소한의 작업으로 유지해야 하며,
     *   - 무거운 작업(예: 대량의 데이터 로드)는 별도의 비동기 프로세스로 분리하는 것이 좋다.
     */
    public void init() {
        System.out.println("2. bean 초기화 : paymentsService가 초기화되었습니다.");
    }

    /*
     * [소멸 메서드]
     * 컨테이너 종료 시 bean 소멸 직전에 호출됨.
     * 리소스 해제나 정리 작업을 수행하는데 사용.
     */
    public void destroy() {
        System.out.println("4. bean 소멸");
    }


    public boolean processPayment(String orderId, double amount) {
        System.out.println("3. 결제 처리를 시작합니다. 주문 ID : " + orderId + ", 주문 금액 : " + amount);
        boolean paymentResult = paymentInterface.processPayment(orderId, amount);

        if (paymentResult) {
            System.out.println("결제가 성공적으로 처리되었습니다.");
        } else {
            System.out.println("결제가 실패하였습니다.");
        }
        return paymentResult;
    }
}
