package site.moasis.monolithicbe.domain.useraccount;

import org.springframework.stereotype.Component;
import site.moasis.monolithicbe.configuration.AppProperties;

@Component
public class AccessTokenManager extends TokenManager {

	public AccessTokenManager(AppProperties prop)
	{
		super(prop.getJwt().getAccess_token_secret(), prop.getJwt().getAccess_token_validity_in_seconds());
	}
}