##  rule (역할)
`Enrollment` Bounded Context의 '인바운드 포트(Inbound Ports)'이다.

## 📝 설명
이 패키지는 '외부'의 프라이머리 어댑터(예: `EnrollmentController`)가 '내부'의 애플리케이션 기능을 호출하기 위해 사용하는 '메뉴판'이자 '계약(Contract)'이다.

이 인터페이스들은 시스템이 '무엇을 할 수 있는지' (유스케이스)를 정의한다.

### 주요 구성 요소 (Use Cases)
* **`RequestEnrollmentUseCase`:** '수강 신청' 유스케이스
* **`ApproveEnrollmentUseCase`:** '수강 승인' 유스케이스
* **`RejectEnrollmentUseCase`:** '수강 거절' 유스케이스
* **`CancelEnrollmentUseCase`:** '수강 취소' 유스케이스
* **`FindEnrollmentUseCase`:** '수강 조회' 유스케이스 (CQS - Query)

`EnrollmentCommandService`와 `EnrollmentQueryService` (Application Service)가 이 인터페이스들을 구현한다.