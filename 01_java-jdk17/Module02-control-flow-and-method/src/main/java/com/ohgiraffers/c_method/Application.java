package com.ohgiraffers.c_method;

public class Application {

    public static void main(String[] args) {

        int num1 = 1;
        int num2 = 2;
        int result = 0;

        if(num1 % 2 == 0 && num2 % 2 == 0){
            result = num1 + num2;
        }

        System.out.println("1번 연산 : " + (result));

        int num3 = 3;
        int num4 = 4;
        if(num3 % 2 == 0 && num4 % 2 == 0){
            result = num3 + num4;
        }
        System.out.println("2번 연산 : " + (result));


        int num5 = 1;
        int num6 = 2;
        if(num5 % 2 == 0 && num6 % 2 == 0){
            result = num5 + num6;
        }
        System.out.println("3번 연산 : " + (result));

        int num7 = 3;
        int num8 = 4;
        if(num7 % 2 == 0 && num8 % 2 == 0){
            result = num7 + num8;
        }
        System.out.println("4번 연산 : " + (result));

        Application app = new Application();

        result = app.add(10, 20);
        System.out.println(result + " 함수의 결과");

        result = app.add(11, 12);
        System.out.println(result + " 함수의 결과");

        result = app.add(10, 20);
        System.out.println(result + " 함수의 결과");

        result = app.add(10, 20);
        System.out.println(result + " 함수의 결과");
    }



    /*
     * 메서드
     * 메서드는 특정 작업을 수행하는 코드 블록으로, 코드의 재사용성과 가독성을 향상시키기 위해 사용된다.
     * 메서드는 입력값(매개변수)를 받고, 결과값(리턴값)을 반환한다.
     * 메서드는 프로그램의 구조를 체계적으로 만들고, 유지보수를 용이하게 한다.
     *
     * 형식
     * [접근 제어자] [반환 타입] 메서드명(매개변수 타입 매개변수 명){
     *   // 실행할 코드
     * }
     *
     * 접근 제어자 : 외부에서 해당 메서드에 접근할 수 있는 범위를 지정한다.
     *   - public : 모든 클래스에서 접근 가능
     *   - protected : 같은 패키지 또는 자식 클래스에서 접근 가능.
     *   - private : 같으 클래스 내에서만 접근 가능.
     *
     * 반환 타입 : 메서드가 작업을 완료하고 반환할 데이터의 타입이다. (기본 자료형 또는 참조 자료형일 수 있다)
     *  - void : 반환값이 없을 때 사용
     *  - String : 문자열을 반환하는 경우
     *   - int, char, short 등의 기본 자료형도 반환 타입으로 사용할 수 있다.
     *
     * 매개변수 : 메서드가 호출될 때 외부에서 전달받은 입력값을 정의한다.
     *   - 매개변수는 여러 개를 가질 수 있으며 함수 내부에서 사용될 변수를 의미한다.
     *   - 이는 일반 변수와 동일하게 타입과 이름을 갖는다.
     * */


    // 접근 제어자 / 반환 타입 / 이름
    /**
     * 입력값 a와 b를 더하고 둘 다 2의 배수인 경우 합의 결과를 반환.
     * @param a 좌항의 값
     * @param b 우항의 값
     * @return a와 b의 합산 결과
     * */
    public int add(int a, int b){

        if(a % 2 == 0 && b % 2 == 0){
            return a + b;
        }

        return 0;
    }
}
