package com.ohgiraffers.chap01.section01.model;

/**
 * ORM과 RDB의 차이 1탄 : 네이밍
 * 이 클래스는 객체지향 프로그래밍의 관점에서 롤을 나타낸다.
 * 객체지향에서는 일반적으로 단순형을 사용하여 개별 객체를 표현한다.
 *
 * 반면, 데이터베이스에서는 테이블 이름을 복수형(Roles)로 사용하는 것이 일반적이다.
 * 이는 테이블이 여러 개의 레코드를 포함하고 있기 때문이다.
 *
 * 따라서, 객체지향에서는 "role"이라는 클래스 이름을 사용하고,
 * 데이터베이스에서는 "roles"라는 테이블 이름을 사용하는 것이 패러다임 차이를 나타낸다.
 *
 * Role 클래스는 권한 정보를 관리하는 클래스로 인식을 하게 된다.
 * 그러나 roles 클래스는 권한들을 관리한다고 생각하며,
 * 이는 특정 "이벤트"의 주체가 어디인지 파악하는데 혼란이 생기게 된다.
 */
public class Roles {
    private int roleId;
    private String roleName;

    public Roles() {}

    public Roles(int roleId, String roleName) {
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
}
