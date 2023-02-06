package site.moasis.monolithicbe.domain.useraccount.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
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

import java.util.List;
import java.util.Optional;

import static site.moasis.monolithicbe.domain.useraccount.dto.UserAccountDto.*;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAccountWriteService {
//	private final Logger logger = LoggerFactory.getLogger(UserAccountWriteService.class);

	private final AccessTokenManager accessTokenManager;
	private final UserAccountRepository userAccountRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final RefreshTokenManager refreshTokenManager;

	private void checkDuplicatedEmail(String email){
		if(userAccountRepository.existsByEmail(email)){
			throw new BusinessException(ErrorCode.DUPLICATE, "user.byEmail", List.of(email));
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
		Authentication authentication;

		try {
			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(email, password);
			authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (BadCredentialsException e) {
			throw new BusinessException(ErrorCode.INVALID_PARAMETER, "user.byCredential", null);
		}

		String refreshToken = refreshTokenManager.createToken(authentication);
		Optional<UserAccount> userAccountOptional = this.userAccountRepository.findByEmail(authentication.getName());

		userAccountOptional.ifPresentOrElse(
				userAccount -> {
					userAccount.changeRefreshToken(refreshToken);
					userAccountRepository.save(userAccount);
				},
				() -> {
					throw new BusinessException(ErrorCode.NOT_FOUND);
				});

		return UserAccountSignInResponseDto.toDto(accessTokenManager.createToken(authentication), refreshToken);
	}

	public ReissueTokenResponseDto reissueToken(String accessToken, String refreshToken){

		if(accessTokenManager.validateToken(accessToken)){
//			logger.info("아직 엑세스토큰이 유효합니다");
			throw new BusinessException(ErrorCode.FORBIDDEN, "user.byValidAccessToken", null);
		}
		if(!refreshTokenManager.validateToken(refreshToken)){
//			logger.info("리프레쉬 토큰이 유효하지 않습니다");
			throw new BusinessException(ErrorCode.FORBIDDEN, "user.byInvalidRefreshToken", null);
		}

		Optional<UserAccount> userAccountOptional = this.userAccountRepository.findByRefreshToken(refreshToken);
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String newAccessToken = accessTokenManager.createToken(authentication);
		String newRefreshToken = refreshTokenManager.createToken(authentication);

		userAccountOptional.ifPresentOrElse(userAccount -> {
			userAccount.changeRefreshToken(newRefreshToken);
			userAccountRepository.save(userAccount);
		}, () -> {
			throw new BusinessException(ErrorCode.NOT_FOUND);
		});

		return ReissueTokenResponseDto.toDto(newAccessToken, newRefreshToken);
	}

	public void logout(String refreshToken) {

		Optional<UserAccount> userAccountOptional = this.userAccountRepository.findByRefreshToken(refreshToken);

		userAccountOptional.ifPresentOrElse(userAccount -> {
			userAccount.changeRefreshToken(null);
			userAccountRepository.save(userAccount);
		}, () -> {
			throw new BusinessException(ErrorCode.NOT_FOUND);
		});
	}
}
