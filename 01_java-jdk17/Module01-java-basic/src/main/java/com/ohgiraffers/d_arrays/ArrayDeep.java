package com.ohgiraffers.d_arrays;

public class ArrayDeep {

    public static void main(String[] args) {

        int[] arr = new int[]{1,2,3};

        System.out.println("배열의 주소" + arr);

        /*
         * 배열의 깊은 개념: 메모리 구조와 한계
         * 배열은 힙 메모리에 연속적으로 할당되며, 참조는 스택에 저장된다.
         * - 예 : int[] arr = new int[3]; -> 힙에 12바이트 (4바이트 *3) 할당
         * 배열의 연속성 덕분에 인덱스 접근이 O(1)로 빠르지만, 크기가 고정되어 동적으로 확장이 불가능하다.
         * */
        arr = new int[]{1,2,3,4}; // 0x001
        // arr = 0x001
        int[] a = arr;
        // a = 0x001

        a[3] = 10; // <-
        System.out.println(arr[3]);


        /* 배열 복사 (깊은 복사)
         * 깊은 복사는 원본 배열의 요소를 새로운 배열에 복사하여,
         * 두 배열이 서로 독립적으로 존재하도록 만든다.
         * 원본 배열의 데이터는 변경되지 않으며, 깊은 복사된 배열에 대한 변경은
         * 원본 배열에 영향을 미치지 않는다.
         */
        int[] deepCopy  = java.util.Arrays.copyOf(arr, 5);


        deepCopy[1] = 10;
        System.out.println("원본 배열: " + java.util.Arrays.toString(arr));
        System.out.println("깊은 복사 배열: " + java.util.Arrays.toString(deepCopy));
        System.out.println("깊은 복사 배열의 주소 : " + deepCopy);
        System.out.println();


        /* 배열 복사 (얕은 복사)
         * 얕은 복사는 원본 배열의 참조를 새로운 변수에 할당한다.
         * 이 경우, 두 변수는 같은 배열을 가리키며,
         * 한 배열에서 데이터를 변경하면 다른 배열에도 영향을 미친다.
         */
        int[] shallowCopy = arr;
        shallowCopy[1] = 10;
        System.out.println("원본 배열: " + java.util.Arrays.toString(arr));
        System.out.println("얕은 복사 배열: " + java.util.Arrays.toString(shallowCopy));
        System.out.println("앝은 복사 배열의 주소 : " + shallowCopy);
        System.out.println("원본 배열 주소  : " + arr);
    }
}
