package com.ohgiraffers.d_arrays;

/*
 * 다차원 배열 (2D Array)
 * - 2차원 배열은 행(row)과 열(column)으로 구성된 테이블 형태의 데이터 구조이다.
 * - 배열 안에 배열이 중첩된 형태로, matrix[row][column]으로 접근한다.
 * - 행렬, 표, 게임 맵 등 2D 데이터를 표현할 때 유용하다.
 * */
public class Application02 {
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3}, // 0번 row
                {4, 5, 6}  // 1번 row
        };

        System.out.println("martix[1][2] :  " + matrix[1][2]);

        System.out.println("행의 수 : " + matrix.length);
        System.out.println("0번째 행의 열의 수 " + matrix[0].length);

    }
}
