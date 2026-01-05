package com.ohgiraffers.e_string;

/*
 * 문자열 다루기
 * String 클래스는 문자열을 저장하고 조작하는 데 사용된다.
 * 불변(immutable) 객체로, 값 변경 시 새로운 객체가 생성된다.
 * 주요 메서드: length(), substring(), concat(), equals() 등.
 *
 * String : 참조 자료형 <- 주소 값
 *
 * String a = 0x001;
 *  string pool
 *   0x001 : 이상우
 *
 *  String b = 0x001;
 *
 * 이상우 == 이상우;
 *
 * 0x001 == 0x001
 *
 * a == b -> 참이다.
 *
 * String name = new String("이상우");
 * String name1 = new String("이상우");
 *
 * name == name1;
 *
 */

public class Application {
    public static void main(String[] args) {
        String text = "Hello, java!";
        // 문자열 길이 확인
        System.out.println("길이 : " + text.length());

        // 부분 문자열
        //.substring(시작위치, 종료위치) :시작 부터 종료까지
        //.substring(시작위치) : 시작 위치부터 쭉
        System.out.println("부분 문자열 : " + text.substring(0,5));

        // 문자열 합치기
        // .concat("") 두 문자열을 합치기 위해서 사용되며 가운데 입력한 값을 기준으로 합친다
        String first = "이";
        String second = "상우";
        String fullName = first.concat(" ").concat(second);
        System.out.println(fullName);

        // 문자열 자르기
        // .split("") 문자열을 ("")의 입력 값을 기준으로 자른다.
        // 반환 형식은 배열로 반환되어 인덱스를 참조하여 확인할 수 있다.
        String[] name = fullName.split(" ");
        System.out.println(name[0]);
        System.out.println(name[1]);

        // 문자열 치환
        // .replace("1번","2번") : 문자열의 값 중 1번이 존재하면 2번으로 변경한다.
        String changed = fullName.replace("이", "김");
        System.out.println(changed);
        // 공백 제거
        System.out.println(changed.replace(" ", ""));

        String lee = new String("이상우");
        String lee1 = new String("이상우");
        System.out.println("이름이 같은지 == " +(lee ==lee1));
        System.out.println("equals로 비교하기 == " + lee.equals(lee1));
    }
}