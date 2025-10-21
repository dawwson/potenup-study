package com.ohgiraffers.chap02.model;

import jakarta.persistence.*;

/**
 * 객체지향(OOP)와 RDB 패러다임 차이 : 네이밍 문제와 JPA의 해결
 * 이 role 클래스는 객체지향 프로그래밍의 관점에서 하나의 권한(role)을 나타낸다.
 * 객체지향에서는 일반적으로 단수형을 사용하여 개별 객체를 표현한다.
 * 따라서 클래스의 이름으로 Role을 사용하는 것이 자연스럽다.
 *
 * JPA는 이를 해결하기 위해 다음 방법을 사용한다.
 * JPA는 객체지향과 RDB 간의 패러다임 차이를 해결하기 위해 매핑 매커니즘을 제공한다.
 * - @Entity 어노테이션을 사용해 Role 클래스를 JPA 엔티티로 정의한다.
 * - @Table(name=Roles) 어노테이션을 사용해 객체지향의 Role 클래스와 RDB의 roles 테이블을 명시적으로 매핑한다.
 * 이를 통해 이름 관례의 차이(단수형 vs 복수형)를 조정한다.
 * - @Column(name="role_id")와 @Column(name="role_name")을 사용해 필드("roleId", "roleName")간의 이름 차이를 매핑한다.
 * - @Id와 @GeneratedValue를 사용해 roleId를 기본키로 설정하고, 자동 증가 값을 관리하며, RDB의 기본 키 제약 조건을 객체지향적으로 처리한다.
 *
 * JPA를 통해 객체지향의 Role 클래스는 RDB의 "Roles" 테이블과 자연스럽게 연결되며,
 * 네이밍 차이로 인한 혼란 없이 객체와 테이블 간의 매핑을 일관성 있게 유지할 수 있다.
 */
@Entity // 엔티티는 name을 명시하지 않으면 클래스의 앞 글자를 소문자로 변경해서 관리한다.
@Table(name = "roles") // 이는 데이터베이스의 "roles" 테이블임을 명시하는 것이다.
public class Role {
    /**
     * JPA - 객체에게 신분을 부여한다.
     * @Entity와 @Id의 본질적인 의미
     * - @Entity : 이 클래스는 이제부터 단순한 데이터 묶음이 아닌 JPA가 관리하는 실체이다- 라는 선언
     * JPA는 이 객체의 생성, 변화, 소멸 (생명주기)를 모두 추적하고 관리하기 시작한다.
     *
     * - @Id : 이 필드가 바로 이 객체의 고유한 신분을 증명하는 식별자이다- 라는 의미
     * JPA는 이 @Id 값을 기준으로 객체의 통일성을 판단한다. 즉, id가 같으면 DB에서 몇 번을 조회하든
     * 같은 존재로 인식하여 동일한 메모리 주소와 객체를 반환해준다.
     * -> 이를 통해 chap01에서 발생했던 다른 객체로 인식되는 문제를 해결한다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name")
    private String roleName;

    /**
     * 기본 생성자가 필요한 이유
     * JPA는 엔티티 객체를 생성하고 관리하는 과정에서 기본 생성자를 반드시 요구한다.
     * - JPA 동작 방식 : JPA는 데이터베이스에서 조회한 데이터를 객체로 매핑할 때, 먼저 기본 생성자를 호출해 객체를 생성한 후,
     * 리플렉션을 사용해 필드에 값을 주입한다. -> JDK 17부터는 다른 방식으로 우회함
     * - 기본 생성자 없을 시 문제 : 만약 기본 생성자가 없다면, JPA는 객체를 생성할 수 없어 "instaniationException"이 발생된다.
     * - 예 : em.find(role.class, 1)로 role 객체를 조회할 때, JPA는 new Role()을 호출해 빈 객체를 생성한 후,
     * 데이터베이스에서 가져온 role_id와 role_name 값을 필드에 설정한다.
     * - 추가 생성자 : public Role(int roleId, String roleName)과 같은 생성자는 개발자가 객체를 생성할 때 유용하지만,
     * JPA는 이를 사용하지 않으므로, 반드시 인자가 없는 기본 생성자가 필요하다.
     * - 접근 제어자 : 기본 생성자는 public 또는 protected 로 설정해야 한다.
     */
    protected Role() {}

    // section02 Application 테스트용 생성자
    public Role(String roleName) {
        this.roleName = roleName;
    }

    private Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
