package org.ohgiraffers.module01hexagonal_architecture.common.event;

import java.time.Instant;

/*
 * 공통 계약
 * - 이 시스템의 모든 모메인 이벤트가 반드시 구현해야 하는 최상위 인터페이스이다.
 * - 도메인 이벤트라면 반드시 가져야 할 필수 메타데이터를 정의하여,
 *   시스템 전반의 이벤트 표준을 강제한다.
 *
 * 핵심 메타 데이터 3가지
 * 1. String getEventId();
 * - 목적 : 이벤트 인스턴스를 유일하게 식별 (보통 UUID 사용)
 * - 용도 :
 *   1) 멱등성 보장 : 구독자가 동일한 이벤트를 중복 수신/처리하는 것을 방지
 *   2) 추적성 : 분산 시스템 환경에서 이벤트의 흐름을 추적하고 디버깅 하는 용도
 *
 * 2. instant GetOccurredAt();
 * - 목적 : 이벤트가 도메인 로직 내에서 발생한 정확한 시각
 * - 용도 : 데이터 변경의 타임라인을 분석하고 기록하기 위함
 *
 * 3. default String getType();
 * - 목적 : 이벤트의 타입을 식별하는 문자열
 * - 용도 : (라우팅/필터링) 이벤트 핸들러가 이 타입을 보고 자신이 처리해야 할 이벤트인지 선별하는 데 사용된다.
 */
public interface DomainEvent {

    // 각 이벤트 인스턴스를 유일하게 식별하기 위한 ID
    String getEventId();

    // 이벤트가 도메인에서 발생한 시각
    Instant getOccurredAt();

    // 이벤트 타입의 이름 (로깅 및 핸들러 라우팅용)
    default String getType() {
        return getClass().getSimpleName();
    }
}
