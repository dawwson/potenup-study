package org.ohgiraffers.module01hexagonal_architecture.member_bc.application.port.in.command;

public record RegisterOrUpdateMemberCommand(
        String name,
        String email,
        String phone
) {
}
