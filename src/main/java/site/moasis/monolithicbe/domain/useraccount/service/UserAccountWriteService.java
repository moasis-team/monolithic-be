package site.moasis.monolithicbe.domain.useraccount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.domain.useraccount.AccessTokenManager;
import site.moasis.monolithicbe.domain.useraccount.RefreshTokenManager;
import site.moasis.monolithicbe.domain.useraccount.UserRole;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;

import java.util.Optional;

import static site.moasis.monolithicbe.domain.useraccount.dto.UserAccountDto.UserAccountJoinRequestDto;
import static site.moasis.monolithicbe.domain.useraccount.dto.UserAccountDto.UserAccountSignInResponseDto;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAccountWriteService {
	private final AccessTokenManager accessTokenManager;
	private final UserAccountRepository userAccountRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final RefreshTokenManager refreshTokenManager;

	private void checkDuplicatedEmail(String email){
		if(userAccountRepository.existsByEmail(email)){
			throw new BusinessException(ErrorCode.DUPLICATE);
		};
	}
	public UserAccount signUp(UserAccountJoinRequestDto userAccountJoinRequestDto) {
		checkDuplicatedEmail(userAccountJoinRequestDto.email());

		UserAccount userAccount = UserAccount.create(
				userAccountJoinRequestDto.email(),
				passwordEncoder.encode(userAccountJoinRequestDto.password()),
				userAccountJoinRequestDto.name(),
				userAccountJoinRequestDto.phoneNumber(),
				UserRole.ROLE_USER
		);

		return userAccountRepository.save(userAccount);
	}


	public UserAccountSignInResponseDto signIn(String email, String password) {
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(email, password);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String refreshToken = refreshTokenManager.createToken(authentication);
		Optional<UserAccount> userAccountOptional = this.userAccountRepository.findByEmail(authentication.getName());

		userAccountOptional.ifPresentOrElse(
				userAccount -> {
					userAccount.registerRefreshToken(refreshToken);
					userAccountRepository.save(userAccount);
				},
				() -> {
					throw new BusinessException(ErrorCode.NOT_FOUND);
				});

		return UserAccountSignInResponseDto.toDto(accessTokenManager.createToken(authentication), refreshToken);
	}
}
