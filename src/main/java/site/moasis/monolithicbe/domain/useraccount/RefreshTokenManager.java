package site.moasis.monolithicbe.domain.useraccount;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenManager extends TokenManager {
	public RefreshTokenManager(@Value("${jwt.refresh-token-secret}") String tokenSecret,
	                           @Value("${jwt.refresh-token-validity-in-seconds}") Long tokenValidityInSeconds){
		super(tokenSecret, tokenValidityInSeconds);
	}
}