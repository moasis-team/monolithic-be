package site.moasis.monolithicbe.domain.useraccount.dto;

public record UserAccountSignInResponseDto(
		String accessToken
) {
	public static UserAccountSignInResponseDto toDto(String accessToken) {
		return new UserAccountSignInResponseDto(accessToken);
	}
}