package site.moasis.monolithicbe.domain.useraccount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.domain.useraccount.ResponseSignIn;
import site.moasis.monolithicbe.domain.useraccount.TokenProvider;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountJoinRequestDto;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;

@RequiredArgsConstructor
@Service
public class UserAccountService {

	private final UserAccountRepository userAccountRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;


	@Autowired
	public UserAccountService(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
		this.userAccountRepository = userAccountRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}


	@Transactional
	public UserAccount create(UserAccountJoinRequestDto userAccountJoinRequestDto) {
		UserAccount userAccount = UserAccount.create(
				userAccountJoinRequestDto.email(),
				passwordEncoder.encode(userAccountJoinRequestDto.password()),
				userAccountJoinRequestDto.name(),
				userAccountJoinRequestDto.phoneNumber()
		);

		return userAccountRepository.save(userAccount);
	}
	// username 과 패스워드로 사용자를 인증하여 액세스토큰을 반환한다.
	@Transactional
	public ResponseSignIn signIn(String email, String password) {
		// 받아온 유저네임과 패스워드를 이용해 UsernamePasswordAuthenticationToken 객체 생성
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(email, password);

		// authenticationToken 객체를 통해 Authentication 객체 생성
		// 이 과정에서 CustomUserDetailsService 에서 우리가 재정의한 loadUserByUsername 메서드 호출
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		// 그 객체를 시큐리티 컨텍스트에 저장
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String accessToken = null;
		// 인증 정보를 기준으로 jwt access 토큰 생성
		try {
			accessToken = tokenProvider.createToken(authentication);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseSignIn.builder()
				.accessToken(accessToken)
				.build();
	}
}
