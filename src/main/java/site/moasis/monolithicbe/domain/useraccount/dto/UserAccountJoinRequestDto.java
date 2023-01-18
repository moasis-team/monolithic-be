package site.moasis.monolithicbe.domain.useraccount.dto;

import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;

public record UserAccountJoinRequestDto(
        String email,
        String password,
        String name,
        String phoneNumber) {
}
