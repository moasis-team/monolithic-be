package site.moasis.monolithicbe.domain.useraccount.dto;

import site.moasis.monolithicbe.domain.useraccount.UserRole;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;

public record UserAccountJoinResponseDto(
        Long id,
        String email,
        String name,
        String phoneNumber,
        UserRole role
) {
    public static UserAccountJoinResponseDto toDto(UserAccount userAccount) {
        return new UserAccountJoinResponseDto(
                userAccount.getId(), userAccount.getEmail(), userAccount.getName(),
                userAccount.getPhoneNumber(), userAccount.getRole()
        );
    }
}