package site.moasis.monolithicbe.domain.useraccount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.domain.useraccount.AccessTokenManager;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountDto;
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAccountReadService {
	private final AccessTokenManager accessTokenManager;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	public UserAccountDto.UserAccountSignInResponseDto signIn(String email, String password) {
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(email, password);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = null;

		try {
			accessToken = accessTokenManager.createToken(authentication);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return UserAccountDto.UserAccountSignInResponseDto.toDto(accessToken);
	}
}
