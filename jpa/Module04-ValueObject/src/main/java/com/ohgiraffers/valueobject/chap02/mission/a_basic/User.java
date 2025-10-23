package com.ohgiraffers.valueobject.chap02.mission.a_basic;

import jakarta.persistence.*;

@Entity(name = "mission_users")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private FullName fullName;

    protected User() {}

    public User(FullName fullName) {
        this.fullName = fullName;
    }

    public FullName getFullName() {
        return fullName;
    }
}
