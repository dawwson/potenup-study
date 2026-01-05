package com.ohgiraffers.a_controlflow.chap02;

/*
 * switch 문의 깊은 개념 : 메모리 최적화
 * switch는 jvm에서 jump table로 컴파일되어 if-else 보다 빠를 수 있다.
 * - 식의 값은 스택에 저장, case 레이블은 메서드 영역의 상수 풀과 비교
 * -  break가 없는 경우 fall-through로 연속 실행
 *
 * jump table:
 * switch 문을 효율적으로 구현하기 위한 기법 중 하나로
 * 이는특정 값에 대한 실행 경로를 빠르게 찾기 위해,
 * 각 case "결과 값"의 실행 코드 주소로 저장한 테이블을 사용
 * ex)
 * swtich(2){
 *   case 1 : print("hi");
 *   case 2 : print("hhh"); <- case가 2 이기 때문에 2번 인덱스 참조해서 바로 실행
 * }
 *
 * jdk 17 개선
 * - switch 표현식 (예 case ->)으로 값 반환 가능.
 * - 패턴 매칭 (preview) 으로 객체 타입 분기 가능.
 * 메모리 효율성 : 상수 값이 많을수록 jump table 크기가 증가하지만, 실행 속도는 O(1)
 * */
public class SwitchDeep {
    public static void main(String[] args) {

        int day = 1;

        String result = switch (day){
            case 1 -> "월요일";
            case 2 -> "화요일";
//            case 3 -> yield System.out.println("수요일");
            case 4 -> {
                System.out.println("4번");
                yield "목요일";
            }
            case 5,6,7 -> "주말";
            default -> "기타";
        };

        System.out.println("1은  " + result);
    }
}
