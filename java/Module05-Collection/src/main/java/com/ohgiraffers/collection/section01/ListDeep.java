package com.ohgiraffers.collection.section01;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * 깊은 개념: ArrayList의 메모리 구조와 성능
 *
 * - 내부적으로 동적 배열로 구현:
 *   ArrayList는 초기 용량을 설정할 수 있으며, 용량이 초과하면 새로운 배열을 생성하고
 *   기존 요소를 복사하여 동적 확장을 수행합니다. 보통 크기는 1.5배로 증가한다.
 *
 * - 메모리 관리:
 *   데이터는 힙 메모리에 연속적으로 저장되며, 자바의 가비지 컬렉터(GC)가 메모리를 관리한다.
 *   이로 인해 빠른 접근 속도를 제공한다.
 *
 * - 성능 특성:
 *   - 순차 접근: O(1) - 인덱스를 통한 요소 접근이 빠르다.
 *   - 중간 삽입/삭제: O(n) - 요소 이동이 필요해 시간이 많이 소요된다.
 *
 * - 동적 확장:
 *   용량 초과 시 새 배열을 할당하고 기존 요소를 복사하는 과정은 O(n) 시간이 걸린다.
 *   따라서 초기 용량을 적절히 설정하는 것이 중요하다.
 *
 * - 결론:
 *   ArrayList는 빠른 접근 속도를 제공하지만, 중간 삽입/삭제가 잦은 경우에는
 *   LinkedList를 고려하는 것이 좋다.
 */

public class ListDeep {
    public static void main(String[] args) {
        /* 동적 확장 */
        List<Integer> numbers = new ArrayList<>(2);  // 초기 용량 2
        numbers.add(1);
        numbers.add(2);
        System.out.println("초기 number의 주소 : " + numbers.hashCode());
        numbers.add(3);  // 용량 초과 → 배열 확장
        System.out.println("확장된 number의 주소 : " + numbers.hashCode());

        /* 코드 설명 */
        // - 용량 초과 시 약 1.5배 크기의 새 배열 생성, 기존 데이터 복사.
        // - 시간 복잡도: 추가 O(1), 확장 시 O(n).
        // - 메모리: 힙에서 새 배열 생성 후 이동.
        System.out.println("ArrayList 확장: " + numbers);


        long startTime = System.nanoTime(); // 시작 시간 기록
        List<String> unoptimized = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            unoptimized.add("책" + i);  // 빈번한 재할당
        }
        long endTime = System.nanoTime(); // 종료 시간 기록
        long unoptimizedDuration = endTime - startTime; // 소요 시간 계산

        // 최적화 리스트 성능 체크
        startTime = System.nanoTime(); // 시작 시간 기록
        List<String> optimized = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            optimized.add("책" + i);  // 재할당 최소화
        }
        endTime = System.nanoTime(); // 종료 시간 기록
        long optimizedDuration = endTime - startTime; // 소요 시간 계산

        // 결과 출력
        System.out.println("비최적화 리스트 소요 시간: " + unoptimizedDuration + " 나노초");
        System.out.println("최적화 리스트 소요 시간: " + optimizedDuration + " 나노초");
        // 성능: 초기 용량 지정으로 O(n) 비용 감소.

        /* 잘못된 예제 */
        // List<String> badList = new ArrayList<>();
        // badList.add(10);  // 컴파일 에러: 타입 불일치

        System.out.println("\n=== ArrayList vs LinkedList 성능 비교 ===");

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();


        // 1. 중간에 데이터 추가 성능
        System.out.println("--- 중간 데이터 추가 (100,000건) ---");
        startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            arrayList.add(0, i); // 항상 첫 번째 인덱스에 추가 (최악의 시나리오)
        }
        endTime = System.nanoTime();
        System.out.println("ArrayList 소요 시간: " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            linkedList.add(0, i); // 첫 번째 노드에 추가 (최적의 시나리오)
        }
        endTime = System.nanoTime();
        System.out.println("LinkedList 소요 시간: " + (endTime - startTime) + " ns");

        // 2. 순차적 데이터 조회 성능
        System.out.println("\n--- 순차 데이터 조회 (100,000건) ---");
        startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            arrayList.get(i);
        }
        endTime = System.nanoTime();
        System.out.println("ArrayList 소요 시간: " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        for (int i = 0; i < 100000; i++) {
            linkedList.get(i);
        }
        endTime = System.nanoTime();
        System.out.println("LinkedList 소요 시간: " + (endTime - startTime) + " ns");
    }
}