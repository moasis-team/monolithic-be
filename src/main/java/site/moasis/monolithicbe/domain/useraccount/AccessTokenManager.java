package site.moasis.monolithicbe.domain.useraccount;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenManager extends TokenManager {
	public AccessTokenManager(@Value("${jwt.access-token-secret}") String tokenSecret,
	                           @Value("${jwt.access-token-validity-in-seconds}") Long tokenValidityInSeconds)
	{
		super(tokenSecret, tokenValidityInSeconds);
	}
}