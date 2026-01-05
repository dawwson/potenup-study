package com.ohgiraffers.c_method;

/*
 * 메서드의 종류
 * 1. 정적 메서드 (static method):
 *   클래스에 속하며, 객체 생성 없이 호출할 수 있다.
 *   예 :  Math.max(a,b)
 *
 * 2. 인스턴스 메서드 (instance Method) :
 *   객체에 속하며, 객체를 생성한 후에 호출해야한다.
 *   예 : String str = "hello"; str.length();
 *
 * 3. 생성자(constructor):
 *   클래스의 인스턴스를 생성할 때 호출되는 특별한 메서드이다.
 *   생성자 반환 타입이 없고, 클래스 이름과 동일하다.
 *
 * 4. 오버로딩 (Meethod Overloading)
 *   같은 이름의 메서드를 여러 개 정의하되, 매개변수의 타입이나 개수(시그니처)를 다르게 하여 사용한다.
 *   예 :
 *   public int add(int a, int b) {...}
 *   public double add(double a, double b) {...}
 * 메서드 오버로딩을 통해 같은 기능을 수행하는 다양한 메서드를 제공할 수 있다.
 * */
public class Application02 {
    public static void main(String[] args) {
        System.out.println(10);
        System.out.println(10.0);
        System.out.println("10");

        Application02.print(10);
        Application02.print(10.0);
        Application02.print("10");


        // 정적 메서드
        // 정적 메서드는 jvm의 method Area에서 관리가 되며 클래스 로딩시 해당 메서드를 저장하고
        // 모든 클래스가 이를 공유할 수 있도록 한다.
        System.out.println(Application02Method.getGreeting());

        // 인스턴스를 생성하는 것으로 지금은 중요도를 두지 않고 다음 모듈에서 공부한다.
        Application02Method methodTest = new Application02Method();

        // 인스턴스 메서드는 객체와 생명주기를 함께 한다.
        System.out.println(methodTest.instanceMethod());

        // 오버로딩
        // "여러 개의 함수가 동일한 이름을 가지면서도 시그니처(매개변수의 개수나 타입)가 다르게 정의되어 있을 때,
        // 호출 시 어떤 함수가 실행될지를 결정하는 것을 오버로딩.
        System.out.println(methodTest.overloadedMethod());
        System.out.println(methodTest.overloadedMethod(10));

        // 이러한 오버로딩은 우리가 그동안 자주 사용하였다.
        System.out.println(1); // 숫자를 매개변수로 받는 print 함수
        System.out.println('a'); // 문자를 매개변수로 받는 print 함수
        System.out.println(1.1); // 실수를 매개변수로 받는 print 함수

    }
    public static void print(String x){
        System.out.println(x);
    }
    public static void print(int x){
        System.out.println(x);
    }
    public static void print(double x){
        System.out.println(x);
    }
}
