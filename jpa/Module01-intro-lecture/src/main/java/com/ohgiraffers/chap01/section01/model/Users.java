package com.ohgiraffers.chap01.section01.model;

import java.time.LocalDate;

/**
 * 객체지향(OOP)와 RDB의 패러다임 차이 : 객체 참조 vs 외래 키
 * 이 user 클래스는 객체지향 프로그래밍의 관점에서 하나의 사용자를 나타낸다.
 * 객체지향에서는 객체 간의 관계를 객체 참조로 표현하는 것이 일반적이다.
 * 예를 들어, user 객체가 특정 역할을 가진다고 할 때, 객체지향에서는 Role role과 같은 필드를 추가해,
 * user 객체가 role 객체에 직접 참조하도록 설계한다.
 *
 * 반명 관계형 데이터베이스(RDB)에서는 관계를 외래 키(Foreign Key)로 표현한다.
 * users 테이블에서 role_id라는 컬럼을 통해 roles 테이블의 특정 레코드를 참조하는 방식이다.
 * 즉, RDB에서는 객체 참조 대신 단순히 role_id 라는 정수 값으로 관계를 나타낸다.
 *
 * 따라서 객체지향에서는 user 클래스에 Role role 필드를 두는 것이 자연스럽지만,
 * 데이터베이스에서는 Users 테이블에 role_id라는 외래키 컬럼을 두는 것이 일반적이다.
 * 이 차이는 객체지향과 RDB의 패러다임 차이를 보여준다.
 *
 * 이러한 차이로 인해 설계와 구현에서 혼란이 발생할 수 밖에 없다.
 * - 객체지향 관점 : user 객체는 Role 객체를 직접 참조하므로, user.getRole().getRoleName()과 같은 방식으로 역할 이름을 쉽게 가져올 수 있다.
 * - RDB 관점 : users 테이블의 role_id 값을 기반으로 roles 테이블과 조인(join) 쿼리를 작성해야 역할 이름을 조회할 수 있다.
 * 이로 인해 객체지향엣는 객체 간의 관계를 직관적으로 다룰 수 있지만, RDB에서는 추가적인 쿼리 작업이 필요하게 된다.
 */
public class Users {
    private int userId;
    private String userName;
    private String email;
    private int roleId;
    private LocalDate date;
}
