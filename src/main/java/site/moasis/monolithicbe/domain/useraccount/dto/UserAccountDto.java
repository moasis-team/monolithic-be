package site.moasis.monolithicbe.domain.useraccount.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import site.moasis.monolithicbe.domain.useraccount.UserRole;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;

import java.util.UUID;

public record UserAccountDto() {
	public record UserAccountJoinRequestDto(
			String email,
			String password,
			String name,
			String phoneNumber) {
	}

	public record UserAccountJoinResponseDto(
	        UUID id,
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

	public record EmailCertificationRequestDto(
			@NotBlank String email,
			@NonNull Integer code
	) {
	}

	public record UserAccountSignInRequestDto(
			String email,
			String password
	) {
	}

	public record UserAccountSignInResponseDto(
			String accessToken,
			String refreshToken
	) {
		public static UserAccountSignInResponseDto toDto(String accessToken, String refreshToken) {
			return new UserAccountSignInResponseDto(accessToken, refreshToken);
		}
	}

	public record ReissueTokenResponseDto(
			String accessToken,
			String refreshToken
	) {
		public static ReissueTokenResponseDto toDto(String accessToken, String refreshToken) {
			return new ReissueTokenResponseDto(accessToken, refreshToken);
		}
	}

	public record ResetPasswordRequestDto(
			@NotBlank String email,
			@NotNull String phoneNumber
	) {
	}

	public record ResetCertificationRequestDto(
			@NotBlank String email,
			@NotBlank String password
	) {
	}
}
