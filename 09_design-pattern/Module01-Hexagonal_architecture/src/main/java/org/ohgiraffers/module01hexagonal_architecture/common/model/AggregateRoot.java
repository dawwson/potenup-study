package org.ohgiraffers.module01hexagonal_architecture.common.model;

import org.ohgiraffers.module01hexagonal_architecture.common.event.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AggregateRoot {

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    // 이벤트를 꺼내가는 용도
    public List<DomainEvent> pullDomainEvents() {

        List<DomainEvent> copied = List.copyOf(domainEvents);

        // 동일한 이벤트가 발행되는 것을 막기 위해 비움
        domainEvents.clear();

        return Collections.unmodifiableList(copied);
    }
}
