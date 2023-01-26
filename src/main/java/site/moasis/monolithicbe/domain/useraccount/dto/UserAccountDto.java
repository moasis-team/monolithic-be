package site.moasis.monolithicbe.domain.useraccount.dto;

import site.moasis.monolithicbe.domain.useraccount.UserRole;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;

public record UserAccountDto() {
	public record UserAccountJoinRequestDto(
			String email,
			String password,
			String name,
			String phoneNumber) {
	}

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

	public record UserAccountSignInRequestDto(
			String email,
			String password
	) {
	}

	public record UserAccountSignInResponseDto(
			String accessToken
	) {
		public static UserAccountSignInResponseDto toDto(String accessToken) {
			return new UserAccountSignInResponseDto(accessToken);
		}
	}
}
