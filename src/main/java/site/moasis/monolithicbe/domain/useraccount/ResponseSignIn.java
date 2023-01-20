package site.moasis.monolithicbe.domain.useraccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSignIn {
	private String accessToken;
}
