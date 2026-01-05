package org.ohgiraffers.module01hexagonal_architecture.enrollment_bc.application.port.out;

public interface MemberStatusReader {

    boolean isActive(String memberEmail);
}
