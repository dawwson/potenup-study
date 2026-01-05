package org.ohgiraffers.module01hexagonal_architecture.common.event;

import java.util.Collection;

public interface DomainEventPublisher {

    // 단일 도메인 이벤트 발행
    void publish(DomainEvent event);

    /*
     * 이벤트 컬렉션을 일괄 발행
     */
    default void publishAll(Collection<?extends DomainEvent> events) {
        if (events == null || events.isEmpty()) {
            return;
        }

        events.forEach(this::publish);
    }
}
