package org.ohgiraffers.module01hexagonal_architecture.common.infrastructure.messaging;

import org.ohgiraffers.module01hexagonal_architecture.common.event.DomainEvent;
import org.ohgiraffers.module01hexagonal_architecture.common.event.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringDomainEventPublisher implements DomainEventPublisher {

    /*
     * ApplicationEventPublisher
     * 스프링 프레임워크에서 애플리케이션 이벤트를 발행하는 데 사용되는 핵심 인터페이스
     * 서로 다른 컴포넌트들이 직접적인 의존관계 없이 서로 통신할 수 있도록 하는 것이다.
     *
     * 발행자 (Publisher)
     * 한 컴포넌트가 "회원탈퇴"라는 작업을 완료한 후,
     * ApplicationEventPublisher를 통해 회원탈퇴 이벤트를 방송한다.
     *
     * 구독자 (Listener)
     * 다른 컴포넌트가 EnrollmentService, MemberService 등을 전혀 모르더라도,
     * @EventListener를 통해 회원탈퇴 이벤트를 구독하고 있다가 이벤트가 방송되면 수강신청 취소라는 후속 작업을 수행
     */
    private final ApplicationEventPublisher delegate;

    public SpringDomainEventPublisher(ApplicationEventPublisher delegate) {
        this.delegate = delegate;
    }

    @Override
    public void publish(DomainEvent event) {
        delegate.publishEvent(event);
    }
}
