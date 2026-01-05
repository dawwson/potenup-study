package com.ohgiraffers.c_method;

public class Application02Method {

    /*
     * ✅ 인스턴스 메서드 (Instance Method)
     * - 객체를 생성한 후에 호출할 수 있는 메서드
     * - JVM의 "힙(Heap)" 영역에 저장되는 객체와 함께 동작
     * - 객체 내부의 인스턴스 변수와 함께 사용될 수 있으며, 개별 객체마다 독립적으로 존재
     * - 메서드 호출 방식: 객체를 생성한 후 `객체명.메서드명()` → `new MethodTest().instanceMethod();`
     */
    public String instanceMethod() {
        return "저는 인스턴스 메서드 입니다.";
    }

    /*
     * ✅ 정적 메서드 (Static Method)
     * - 클래스 레벨에서 실행되는 메서드로, 객체를 생성하지 않고 호출 가능
     * - JVM의 "메서드 영역(Method Area)"에 저장되며, 모든 객체가 공유하는 영역
     * - 정적 메서드는 인스턴스 변수(instance variable) 또는 "인스턴스 메서드"를 직접 사용할 수 없음
     *   → 이유: 정적 메서드는 "클래스 로딩 시점"에 메서드 영역에 등록되지만,
     *           인스턴스 변수는 객체가 생성된 이후 "힙(Heap) 메모리"에 저장되기 때문
     * - 메서드 호출 방식: 클래스명.메서드명() → `MethodTest.getGreeting();`
     */
    public static String getGreeting() {
        return "저는 정적 메서드입니다.";
    }


    /*
     * ✅ 오버로딩 (Overloading)
     * - 하나의 클래스 내에서 '같은 이름'의 메서드를 여러 개 정의하는 것을 의미한다.
     * - 💡 오버로딩 규칙: 메서드 이름은 같지만 '메서드 시그니처(Method Signature)'가 달라야 한다.
     * - 메서드 시그니처: 메서드의 이름과 매개변수 리스트(개수, 타입, 순서)를 의미한다.
     * - 반환 타입은 시그니처에 포함되지 않으므로, 반환 타입만 다른 메서드는 오버로딩이 성립되지 않는다.
     * - 컴파일러가 메서드 호출 시 전달된 인자의 타입을 보고 "적절한 메서드를 선택"하여 실행한다.
     */
    public String overloadedMethod() {
        return "저는 overloadedMethod 입니다.";
    }

    /*
     * 아래와 같이 메서드의 시그니처 부분을 다르게 설정하여 동일한 이름의 메서드를 생성할 수 있다.
     * */
    public String overloadedMethod(int a) {
        return "저도 overloadedMethod 입니다.";
    }
}
