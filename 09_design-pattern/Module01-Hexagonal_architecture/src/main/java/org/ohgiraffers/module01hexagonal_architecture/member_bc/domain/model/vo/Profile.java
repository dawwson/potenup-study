package org.ohgiraffers.module01hexagonal_architecture.member_bc.domain.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.regex.Pattern;

/*
 * 1. 값 객체 (value object)란?
 * - 값 그 자체로 의미를 갖는 객체를 의미한다.
 * - member 엔티티와 달리 식별자를 갖지 않고, 속성이 같으면 동일한 객체로 취급한다.
 *   (예: 1000원 == 500원 2개)
 *
 * 2. profile vo의 책임
 * - member 애그리게이트 내의 이름, 이메일, 전화번호라는 관련 값들을 하나의 개념으로 묶어준다.
 * - 핵심 : profile과 관련된 불변식을 스스로 보장한다.
 *
 * 3. 핵심 특성 : 불변성
 * - VO는 불변이어야 한다. 즉, 한 번 생성되면 내부 상태가 변하지 않아야 한다.
 * - 이 클래스에 setName과 같은 setter가 없는 이유이기도 하다.
 * - 값이 변경되어야 한다면 새로운 VO 인스턴스를 생성하여 교체해야 한다.
 *
 * 4. Embeddable
 * - VO가 member 엔티티 내부에 포함될 수 있음을 의미한다.
 */
@Embeddable
public class Profile {

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "member_email", nullable = false, unique = true)
    private String email;

    @Column(name = "member_phone")
    private String phone;

    /*
     * 이메일 정규식 패턴 (static final로 캐싱)
     * 1. 입력된 문자열 정규식을 분석하고 파싱한다.
     * 2. 이 정규식을 실행 가능한 내부 상태 기계로 변환하는 과정을 거친다. (이 과정은 CPU 자원을 많이 소모한다.)
     *
     * 만약 validate() 같은 메서드 내부에서 매번 Pattern.compile()을 호출하면
     * 이메일 검증할 때마다 이 비싼 컴파일 작업을 반복하게 되어 심각한 성능 저하를 유발한다.
     *
     * private static final로 선언하면 이 작업이 클래스 로드 시점에 단 한 번만 실행되며
     * 컴파일된 EmailPattern 객체는 모든 인스턴스가 공유하게 된다.
     */
    private static final Pattern EMAIL_PATTERN =  Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");

    protected Profile() {}

    private Profile(String name, String email, String phone) {
        validate(name, email);

        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public static Profile of(String name, String email, String phone) {
        return new Profile(name, email, phone);
    }

    /*
     * 빈 프로필 (초기 생성용)
     * empty() 팩토리의 필요성
     * NULL 객체 패턴을 적용하여 null 참조를 피하기 위함이다.
     *
     * 1. NullPointerException(NPE) 방지
     * - profile 필드에 실제 null을 할당하는 대신 비어 있음을 나타내는 실제 profile을 할당한다.
     * - 이렇게 하면 member.getProfile().getName()을 해도 (NEP)가 발생하지 않는다.
     *
     * 2. 안전한 기본값 제공
     * - 회원가입 직후처럼 프로필 정보가 아직 없는 경우, null 대신 이 empty 객체를 기본값 또는 초기값으로 사용한다.
     *
     * 3. 클라이언트 코드 단순화
     * - empty 객체는 validate() 검증을 통과한 유효한 객체이다.
     * - 이 객체를 사용하는 클라이언트(서비스 로직 등)은 if(profile != null) 같은 방어 코드를 작성할 필요가 없다.
     */
    public static Profile empty() {
        return new Profile("UNKNOWN", "unknown@example.com", null);
    }

    private void validate(String name, String email) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }

        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException(email);
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    private Profile changeName(String name) {
        return new Profile(name, this.email, this.phone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profile that)) return false;
        Profile profile = (Profile) o;
        return Objects.equals(name, profile.name) && Objects.equals(email, profile.email) && Objects.equals(phone, profile.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return String.format("%s <%s>", name, email);
    }
}
