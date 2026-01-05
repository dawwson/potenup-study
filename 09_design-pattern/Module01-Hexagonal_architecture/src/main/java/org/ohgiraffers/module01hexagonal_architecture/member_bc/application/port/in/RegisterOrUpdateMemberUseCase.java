package org.ohgiraffers.module01hexagonal_architecture.member_bc.application.port.in;

import org.ohgiraffers.module01hexagonal_architecture.member_bc.application.port.in.command.RegisterOrUpdateMemberCommand;

public interface RegisterOrUpdateMemberUseCase {

    String registerOrUpdate(RegisterOrUpdateMemberCommand command);
}
