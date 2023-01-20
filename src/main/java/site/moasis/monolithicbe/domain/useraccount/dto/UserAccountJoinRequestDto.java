package site.moasis.monolithicbe.domain.useraccount.dto;

public record UserAccountJoinRequestDto(
		String email,
		String password,
		String name,
		String phoneNumber) {
}
