/**
 *
 ### 🥉 기초 미션: 게시글(Post) 엔티티 만들기

 **목표:** `@Column`의 다양한 속성과 `@Enumerated`를 사용하여 기본적인 엔티티를 정확하게 매핑하는 능력을 기릅니다.

 **요구사항:**
 1.  `Post`라는 이름의 엔티티 클래스를 만드세요.
 2.  다음 필드를 포함해야 합니다.
 * `id`: `Long` 타입, 기본 키, 자동 증가 전략 사용.
 * `title`: `String` 타입, 최대 100자, `NULL`을 허용하지 않음.
 * `content`: `String` 타입, 매우 긴 텍스트를 저장할 수 있어야 함 (`@Lob` 어노테이션 사용), `NULL`을 허용하지 않음.
 * `author`: `String` 타입, 작성자 이름.
 * `status`: `PostStatus`라는 `Enum` 타입, `PUBLIC`, `PRIVATE`, `DELETED` 세 가지 상태를 가짐. DB에는 문자열로 저장되어야 합니다.
 * `createdAt`: `LocalDateTime` 타입, 엔티티가 처음 저장될 때의 시간이 자동으로 들어가야 함.
 3.  `PostStatus` `Enum`을 직접 정의하여 사용하세요.
 4.  `Application` 클래스에서 `Post` 객체를 생성하고, 영속화하여 DB에 저장되는지 확인하세요.
 */
package com.ohgiraffers.z_activity.mission.a_basic.model;

import jakarta.persistence.*;

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column()
    @Lob()
    private String content;

    @Column
    private String author;
    private PostStatus status;
    private  String createdAt;
}
