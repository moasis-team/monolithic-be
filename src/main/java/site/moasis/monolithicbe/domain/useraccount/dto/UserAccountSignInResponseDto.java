package site.moasis.monolithicbe.domain.useraccount.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountSignInResponseDto {
	private String accessToken;
}
