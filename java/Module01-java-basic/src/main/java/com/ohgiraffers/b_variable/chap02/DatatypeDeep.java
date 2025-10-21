package com.ohgiraffers.b_variable.chap02;

public class DatatypeDeep {

    public static void main(String[] args) {
        /*
         * [데이터 타입의 딥한 개념 : 부동소수점과 정밀도]
         * 자바의 실수형("float","double")은 IEEE 754 부동소수점 표준을 따른다.
         *
         * - 부동소수점(Floating-point) : 실수를 부호(sing), 지수(Exponent), 가수(Mantissa)로 표현
         *   - "float" : 32비트(부호 1비트, 지수 8비트, 가수 23비트)
         *   - "double" : 64비트(부호 1비트, 지수 11비트, 가수 52비트)
         * - 구조
         *   - 부호 비트 (sing bit) : 숫자가 양수인지 음수인지 표현
         *   - 지수 (Exponent) : 숫자의 크기(스케일)을 결정하며, 2의 거듭제곱으로 표현
         *   - 가수 (mantissa,또는 Significand) : 숫자의 유효 숫자(소수점 이하 포함)을 나타낸다.
         *
         * - 이진 표현의 한계
         *   - 10진수 소수(예 : 0.1)은 이진수로 정확히 표현되지 않음(무한 소수)
         *   - 예 : 0.1(10 진수) = 0.0001100111... (2진수 반복)
         *   - 이로 인해 근사값으로 저장되며, "float"보다 "double"이 더 높은 정밀도를 제공.
         * - 부동소수점 오차 :
         *   - 연산 (덧셈, 비교 등) 시 미세한 오차가 누적될 수 있음
         *   - 예 : 0.1 + 0.2는 정확하게 0.3이 되지 않고, 약 0.030000000000000004 계산
         * - 실무적 합의  :
         *   - 실수 비교 시 "==" 대신 오차 범위(예 Math.abs(a-b) < 0.0001)를 사용 권장.
         *   - 정밀도가 중요한 경우 Bigdecimal 클래스를 사용
         * */

        float fVal = 0.1f;
        double dVal = 0.1;
        System.out.println("fVal = " + fVal);
        System.out.println("dVal = " + dVal);
        System.out.println("fVal == dVal : " + (fVal == dVal));

        // 부동 소수점 오차
        double dNum = 0.1;
        double dNum2 = 0.2;

        double dNum3 = 0.3;
        System.out.println(dNum + dNum2 == dNum3);

    }
}
