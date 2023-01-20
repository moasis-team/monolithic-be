package site.moasis.monolithicbe.domain.useraccount.dto;

public record UserAccountSignInRequestDto(
		String email,
		String password
) {
}



