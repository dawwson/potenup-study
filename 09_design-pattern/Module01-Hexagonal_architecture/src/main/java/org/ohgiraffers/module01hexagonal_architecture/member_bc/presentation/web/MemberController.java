package org.ohgiraffers.module01hexagonal_architecture.member_bc.presentation.web;

import jakarta.validation.Valid;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.application.port.in.RegisterOrUpdateMemberUseCase;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.application.port.in.WithdrawMemberUseCase;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.application.port.in.command.RegisterOrUpdateMemberCommand;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.presentation.web.dto.MemberUpsertRequest;
import org.ohgiraffers.module01hexagonal_architecture.member_bc.presentation.web.dto.MemberUpsertResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/*
 * 1. 컨트롤러의 역할 : 인바운드 어댑터
 * - 헥사고날 아키텍처에서 컨트롤러는 외부 세계의 요청을 애플리케이션 코어가 이해할 수 있는 언어로 번역하고
 *   위임하는 어댑터이다.
 *
 * 2. 컨트롤러의 핵심 책임 (오케스트레이션이 아님)
 * - HTTP 요청 매핑 : @RequestMapping, @PostMapping 등으로 url과 메서드 연결
 * - 데이터 역직렬화 : JSON 요청 본문을 @RequestBody MemberUpsertRequest(DTO)로 변환
 * - 입력 형식 검증 : @Valid를 통해 DTO 형식 검증
 * - DTO to Command 변환 (*중요)
 *   프레젠테이션 계층의 DTO를 애플리케이션 계층의 command로 번역한다.
 *   -> 이유: Presentation 계층은 Application 계층이 어떻게 동작하는지 몰라야 한다.
 *           DTO는 화면이나 API 스펙에 종속되지만 command는 유즈케이스(기능)에 종속된다.
 *           이 둘을 분리해야 API 스펙이 변경되어도 Application 로직이 영향을 받지 않는다.
 *
 * - 유즈케이스 호출 :
 *   - registerOrUpdateMemberUseCase : 컨트롤러는 구현체를 몰라야 하며, 인터페이스에만 의존해야 한다.(DIP)
 * - 결과 변환 및 HTTP 응답
 * - UseCase의 결과를 ResponseEntity로 감싸 HTTP 응답(상태코드, 본문)을 생성한다.
 *
 * ❌컨트롤러가 하지 말아야 할 일
 * - 비즈니스 로직 처리(예: upsert의 경우 if/else로 호출 조정)
 * - Repository 직접 호출
 * - Member 같은 도메인 엔티티를 직접 반환
 * - 트랜잭션 관리
 */
@RestController
@RequestMapping("/api/members")
@Validated
public class MemberController {

    private final RegisterOrUpdateMemberUseCase registerOrUpdateMemberUseCase;
    private final WithdrawMemberUseCase withdrawMemberUseCase;

    public MemberController(
            RegisterOrUpdateMemberUseCase registerOrUpdateMemberUseCase,
            WithdrawMemberUseCase withdrawMemberUseCase
    ) {
        this.registerOrUpdateMemberUseCase = registerOrUpdateMemberUseCase;
        this.withdrawMemberUseCase = withdrawMemberUseCase;
    }

    @PostMapping
    public ResponseEntity<MemberUpsertResponse> upsert(@Valid @RequestBody MemberUpsertRequest request) {
        RegisterOrUpdateMemberCommand command = new RegisterOrUpdateMemberCommand(request.name(), request.email(), request.phone());

        String id = registerOrUpdateMemberUseCase.registerOrUpdate(command);

        return ResponseEntity.status(HttpStatus.OK).body(new MemberUpsertResponse(id));
    }

    @DeleteMapping
    public ResponseEntity<Void> withdraw(@Valid @RequestBody MemberUpsertRequest request) {
        withdrawMemberUseCase.withdrawByEmail(request.email());

        return ResponseEntity.noContent().build();
    }

}
