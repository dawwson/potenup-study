package org.ohgiraffers.module01hexagonal_architecture.member_bc.application.service;

import org.ohgiraffers.module01hexagonal_architecture.common.event.DomainEventPublisher;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.application.port.in.RegisterOrUpdateMemberUseCase;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.application.port.in.WithdrawMemberUseCase;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.application.port.in.command.RegisterOrUpdateMemberCommand;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception.EmailRequiredException;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception.MemberNotFoundException;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.exception.NameRequiredOnRegisterException;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.entity.Member;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.vo.MemberId;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.vo.Profile;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/*
 * Application Service의 책임
 * 이 서비스는 오케스트레이션을 담당한다.
 * - 핵심 차이 : 도메인 객체는 자신의 상태를 지키는 규칙(불변식)에 집중하고,
 *   애플리케이션 서비스는 프로세스의 흐름에 집중한다.
 */
@Service
@Transactional
public class MemberCommandService implements WithdrawMemberUseCase, RegisterOrUpdateMemberUseCase {

    private final MemberRepository memberRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Autowired

    public MemberCommandService(MemberRepository memberRepository, DomainEventPublisher domainEventPublisher) {
        this.memberRepository = memberRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public String registerOrUpdate(RegisterOrUpdateMemberCommand command) {
        Objects.requireNonNull(command, "command는 null일 수 없습니다.");

        if (command.email() == null || command.email().isBlank()) {
            throw new EmailRequiredException();
        }

        return memberRepository.findByEmail(command.email())
                .map(existing -> updateExisting(existing, command))
                .orElseGet(() -> createNewMember(command));
    }

    private String createNewMember(RegisterOrUpdateMemberCommand command) {
        if (command.name() == null || command.name().isBlank()) {
            throw new NameRequiredOnRegisterException();
        }

        Member created = Member.register(Profile.of(
                command.name(),
                command.email(),
                command.phone()
        ));

        Member saved = memberRepository.save(created);

        MemberId memberId = saved.getId();

        return memberId != null ? memberId.toString() : null;
    }

    private String updateExisting(Member existing, RegisterOrUpdateMemberCommand command) {
        Profile profile = existing.getProfile();

        String name = (command.name() != null && !command.name().isBlank()) ? command.name() : profile.getName();
        String phone = (command.phone() != null && !command.phone().isBlank()) ? command.phone() : profile.getPhone();

        existing.updateProfile(Profile.of(name, profile.getEmail(), phone));

        Member updated = memberRepository.save(existing);

        return updated.getId().toString();
    }

    @Override
    public void withdrawByEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        member.withdraw();

        memberRepository.save(member);

        domainEventPublisher.publishAll(member.pullDomainEvents());
    }
}
