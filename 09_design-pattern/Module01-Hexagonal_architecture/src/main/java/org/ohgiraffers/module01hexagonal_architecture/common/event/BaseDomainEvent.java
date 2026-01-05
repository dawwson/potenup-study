package org.ohgiraffers.module01hexagonal_architecture.common.event;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class BaseDomainEvent implements DomainEvent {

    private final String eventId;
    private final Instant occurredAt;

    protected BaseDomainEvent() {
        this(UUID.randomUUID().toString(), Instant.now());
    }

    protected BaseDomainEvent(String eventId, Instant occurredAt) {
        this.eventId = Objects.requireNonNull(eventId, "eventId must not be null");
        this.occurredAt = Objects.requireNonNull(occurredAt, "occurredAt must not be null");
    }


    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public Instant getOccurredAt() {
        return occurredAt;
    }

    @Override
    public String toString() {
        return "BaseDomainEvent{" +
                "eventId='" + eventId + '\'' +
                ", occurredAt=" + occurredAt +
                '}';
    }
}
