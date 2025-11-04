package com.ohgiraffers.aop.section01.problem;

public class Member {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String role;

    public Member() {};

    public Member(String email, String password, String name, String phoneNumber, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }
}
