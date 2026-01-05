DROP DATABASE IF EXISTS design_pattern;
CREATE DATABASE design_pattern;
grant all privileges on design_pattern.* to 'gorilla'@'%';

USE design_pattern;

drop table if exists members;

drop table if exists enrollments;


-- Member BC
-- Member.java (@Entity, @Table(name="members"))
-- MemberId.java (@EmbeddedId, BINARY(16))
-- Profile.java (@Embedded, @UniqueConstraint(name = "uk_member_email"))
CREATE TABLE members (
                         member_id BINARY(16) NOT NULL COMMENT '회원 식별자(UUID)',
                         member_name VARCHAR(255) NOT NULL COMMENT '회원 이름',
                         member_email VARCHAR(255) NOT NULL COMMENT '회원 이메일 (UK)',
                         member_phone VARCHAR(255) COMMENT '회원 전화번호',
                         status VARCHAR(20) NOT NULL COMMENT '회원 상태(ACTIVE/INACTIVE/DELETED)',
                         PRIMARY KEY (member_id),
                         CONSTRAINT uk_member_email UNIQUE (member_email)
) COMMENT '회원 (Member BC)';


-- Enrollment BC
-- Enrollment.java (@Entity, @Table(name="enrollments"))
-- @UniqueConstraint(name = "uk_member_email_and_course_id")
CREATE TABLE enrollments (
                             id BIGINT NOT NULL AUTO_INCREMENT COMMENT '수강신청 PK',
                             member_email VARCHAR(255) NOT NULL COMMENT '수강자 이메일(약결합 키)',
                             course_id BIGINT NOT NULL COMMENT '강좌 ID(약결합 키)',
                             status VARCHAR(20) NOT NULL COMMENT '수강 상태 (REQUESTED/APPROVED/REJECTED/CANCELED)',
                             requested_at TIMESTAMP NOT NULL COMMENT '신청 시각',
                             decided_at TIMESTAMP COMMENT '승인/거절 결정 시각',
                             canceled_at TIMESTAMP COMMENT '취소 시각',
                             reject_reason VARCHAR(500) COMMENT '거절 사유',
                             PRIMARY KEY (id),
                             CONSTRAINT uk_member_email_and_course_id UNIQUE (member_email, course_id)
) COMMENT '수강신청 (Enrollment BC)';


-- H2 DB가 아닌 MySQL 기준으로 UUID 바이너리 삽입 (UUID_TO_BIN 함수 사용)
-- (참고: request.http 파일의 테스트 시나리오를 원활히 수행하기 위함)

-- Member BC 데이터
-- 1. 'hong' (ACTIVE) - 탈퇴 테스트용
INSERT INTO members (member_id, member_name, member_email, member_phone, status)
VALUES (UUID_TO_BIN('a0a0a0a0-0000-0000-0000-000000000001'), '홍길동', 'hong.gildong@example.com', '010-1111-2222', 'ACTIVE');

-- 2. 'lim' (ACTIVE) - 수정 테스트용
INSERT INTO members (member_id, member_name, member_email, member_phone, status)
VALUES (UUID_TO_BIN('a0a0a0a0-0000-0000-0000-000000000002'), '임꺽정', 'lim.kkeokjeong@example.com', '010-3333-4444', 'ACTIVE');

-- 3. 'student01' (ACTIVE) - 수강 신청 테스트용
INSERT INTO members (member_id, member_name, member_email, member_phone, status)
VALUES (UUID_TO_BIN('a0a0a0a0-0000-0000-0000-000000000101'), '학생01', 'student01@example.com', '010-0101-0101', 'ACTIVE');

-- 4. 'student02' (ACTIVE) - 수강 신청 테스트용
INSERT INTO members (member_id, member_name, member_email, member_phone, status)
VALUES (UUID_TO_BIN('a0a0a0a0-0000-0000-0000-000000000102'), '학생02', 'student02@example.com', '010-0202-0202', 'ACTIVE');

-- 5. 'student.paid' (ACTIVE) - 유료 강좌 테스트용
INSERT INTO members (member_id, member_name, member_email, member_phone, status)
VALUES (UUID_TO_BIN('a0a0a0a0-0000-0000-0000-000000000103'), '유료학생', 'student.paid@example.com', '010-7777-7777', 'ACTIVE');

-- 6. 'inactive.user' (INACTIVE) - 비활성 회원 테스트용
INSERT INTO members (member_id, member_name, member_email, member_phone, status)
VALUES (UUID_TO_BIN('a0a0a0a0-0000-0000-0000-000000000104'), '휴면회원', 'inactive.user@example.com', '010-8888-8888', 'INACTIVE');


-- Enrollment BC 데이터
-- (참고: command.http 테스트와 겹치지 않도록 미리 몇 개만 생성)
-- (ID는 1부터 AUTO_INCREMENT 가정)

-- 1. 'student01'이 201번 강좌를 '승인'받은 상태 (201: 홀수, >0, 5x아님)
INSERT INTO enrollments (member_email, course_id, status, requested_at, decided_at)
VALUES ('student01@example.com', 201, 'APPROVED', NOW() - INTERVAL 1 DAY, NOW());

-- 2. 'student01'이 203번 강좌를 '신청'만 한 상태 (203: 홀수, >0, 5x아님)
INSERT INTO enrollments (member_email, course_id, status, requested_at)
VALUES ('student01@example.com', 203, 'REQUESTED', NOW());

-- 3. 'student02'가 201번 강좌를 '거절'당한 상태
INSERT INTO enrollments (member_email, course_id, status, requested_at, decided_at, reject_reason)
VALUES ('student02@example.com', 201, 'REJECTED', NOW() - INTERVAL 1 DAY, NOW(), '선수 과목 미이수');

-- 4. 'student.paid'가 205번 강좌를 '신청'한 상태 (205: 홀수, >0, 5배수(유료))
INSERT INTO enrollments (member_email, course_id, status, requested_at)
VALUES ('student.paid@example.com', 205, 'REQUESTED', NOW());

-- 5. 'student02'가 205번 강좌를 '신청'한 상태 (205: 유료, 이메일 'paid' 미포함)
INSERT INTO enrollments (member_email, course_id, status, requested_at)
VALUES ('student02@example.com', 205, 'REQUESTED', NOW());

-- 6. 'student01'이 107번 강좌를 '신청'한 상태 (107: 홀수, >0, 5x아님)
INSERT INTO enrollments (member_email, course_id, status, requested_at)
VALUES ('student01@example.com', 107, 'REQUESTED', NOW());

-- 7. 'student02'가 109번 강좌를 '승인'받은 상태 (회원 탈퇴 이벤트 테스트용)
INSERT INTO enrollments (member_email, course_id, status, requested_at, decided_at)
VALUES ('student02@example.com', 109, 'APPROVED', NOW() - INTERVAL 1 DAY, NOW());


COMMIT;