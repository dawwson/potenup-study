package com.ohgiraffers.c_method;

/*
 * 1. 메서드의 기본 구조와 동작
 * - 매서드는 특정 작업을 수행하는 코드 블록으로, jvm의 스택(stack) 영역에서 관리된다.
 * - 메서드가 호출되면, 해당 메서드를 위한 공간인 '스택 프레임(stack Frame)'이 스택에 쌓인다.(push)
 * - 이 스택 프레임에는 매개변수, 지역 변수, 리턴 주소 등이 저장된다.
 * - 메서드 실행이 끝나면, 스택 프레임은 스택에서 제거된다.(pop)
 * - 장점 : 이런 구조 덕분에 메모리 관리가 용이하고 재귀 호출이 가능하며, 디버깅 시 호출 스택을 추적할 수 있다.
 * */

/*
 * 2. 메서드와 성능의 관계 : JIT 컴파일러와 인라이닝
 * - 메서드 호출에는 스택 프레임을 쌓고 제거하는 미세한 비용(오버헤드)가 발생한다.
 * - 하지만 현대 jvm은 매우 똑독해서, JIT(just-in-Time) 컴파일러를 통해 이 오버헤드를 최적화한다.
 * - jit 컴파일러는 자주 실행되는 코드(hot spot)을 실시간으로 기계어로 번역하여 성능을 최적화한다.
 * - 주요 최적화 기법 중 하나가 "인라이닝" 이라는 것이다.
 * */

/*
 * 3. 단일 책임의 원칙
 * - 메서드(또는 클래스)는 단 하나의 책임만 가져야 한다.
 * - 즉, 해당 메서드를 변경해야 하는 이유가 오직 하나여야 한다.
 *
 * [srp)의 진짜 목적
 * - 1. 가독성 및 유지보수성 향상 : 코드가 간결하고 명확해져 이해하고 수정하기 쉬워진다. (핵심)
 * - 2. 테스트 용이성 : 기능 단위가 명확히 분리되어 단위 테스트 작성이 매우 쉬워진다.
 * - 3. 재사용성 증가 : 범용적인 기능을 가진 메서드는 다른 곳에서도 재사용하기 좋다.
 * - 4. 부수적인 효과 성능 최적화 가능한 : 메서드가 작고 단순하면 jit 컴파일러가 인라이닝과 같은 최적화를 적용하기 쉬워진다.
 *
 * [중요]
 * srp는 성능을 위한 원칙이 아니라 좋은 설꼐를 위한 원칙이다.
 * 성능 향상은 그에 따른 부수적인 효과일 뿐이다.
 * */


public class MethodDeep {
    public static void main(String[] args) {

        /*
         * 4. 실전 성능 최적화 기법 (올바른 예제)
         * (1) 메서드 크기 조정: SRP에 따라 작고 응집력 있게 설계하는 것이 기본이다.
         * (2) 객체 생성 최소화: 특히 반복문 내에서 불필요한 객체 생성은 성능 저하의 주범이다.
         * (3) 반복문 내 연산 최적화: 반복문 밖에서 계산할 수 있는 것은 미리 계산한다.
         * (4) 예외 처리 최적화: 예외(Exception)를 일반적인 제어 흐름에 사용하지 않는다.
         */
        System.out.println("### 1. SRP의 가치: 유지보수성 시연 ###");
        demonstrateSRP();

        System.out.println("\n\n### 2. 최적화 기법(2): 반복문 내 객체 생성 ###");
        demonstrateStringConcatenation();
    }

    // ====== 유지보수 사례

    /**
     * [시연 1] SRP의 진짜 가치인 '유지보수성'과 '가독성'을 보여주는 예제
     */
    public static void demonstrateSRP() {
        System.out.println("--- SRP 위반 메서드 실행 ---");
        // 하나의 메서드가 너무 많은 일을 해서 수정이 어렵다.
        // 이메일 주소 변경 시 report도 영향을 받을 수 있다.
        processAndSendReport("연간 실적 데이터");

        System.out.println("\n--- SRP 준수 메서드 실행 ---");
        // 각 메서드의 역할이 명확하여 코드를 이해하고 재사용하기 쉽다.
        String report = generateReport("연간 실적 데이터");
        sendReport(report);
    }


    // SRP 위반 메서드
    public static void processAndSendReport(String data) {
        // 책임 1: 데이터를 가공하여 보고서 생성
        System.out.println("데이터 가공 중...");
        String report = "<html><body><h1>보고서</h1><p>" + data + "</p></body></html>";
        System.out.println("보고서 생성 완료: " + report);

        // 책임 2: 생성된 보고서를 이메일로 전송
        System.out.println("이메일 서버에 연결 중...");
        System.out.println("'" + report + "'를 admin@company.com 으로 전송합니다.");

        // 만약 '전송 방식을 SMS로 변경'하거나 '보고서 형식을 PDF로 변경'해야 한다면?
        // 이 메서드는 두 가지 이유로 변경될 수 있으므로 SRP를 위반한다.
    }

    // SRP 준수 메서드 1
    public static String generateReport(String data) {
        // 오직 '보고서 생성' 책임만 가진다.
        System.out.println("데이터 가공 중...");
        String report = "<html><body><h1>보고서</h1><p>" + data + "</p></body></html>";
        System.out.println("보고서 생성 완료: " + report);
        return report;
    }

    // SRP 준수 메서드 2
    public static void sendReport(String report) {
        // 오직 '보고서 전송' 책임만 가진다.
        System.out.println("이메일 서버에 연결 중...");
        System.out.println("'" + report + "'를 admin@company.com 으로 전송합니다.");
    }
    // =========


    // ========= 객체 생성 최소화 테스트
    /**
     * [시연 2] 최적화 기법(2) - 반복문 내 불필요한 객체 생성의 문제와 해결책
     * String의 '+' 연산과 StringBuilder의 성능을 비교한다.
     */
    public static void demonstrateStringConcatenation() {
        int iterations = 50000; // 반복 횟수를 늘려 차이를 극명하게 확인

        // 나쁜 예: String의 '+' 연산은 반복될 때마다 새로운 String 객체를 생성한다.
        long startTime1 = System.nanoTime();
        String result1 = "";
        for (int i = 0; i < iterations; i++) {
            result1 += "x"; // 내부적으로 new String(...)이 계속 호출됨
        }
        long endTime1 = System.nanoTime();
        System.out.printf("나쁜 예 (String '+'): %,d ns%n", (endTime1 - startTime1));

        // 좋은 예: StringBuilder는 내부 버퍼(char 배열)의 크기를 조절하여 문자열을 합친다.
        long startTime2 = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("x"); // 새로운 객체 생성 없이 내부 버퍼에 추가
        }
        String result2 = sb.toString(); // 마지막에 한 번만 String 객체로 변환
        long endTime2 = System.nanoTime();
        System.out.printf("좋은 예 (StringBuilder): %,d ns%n", (endTime2 - startTime2));
    }

}
