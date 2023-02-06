package site.moasis.monolithicbe.domain.useraccount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import site.moasis.monolithicbe.domain.useraccount.UserRole;
import site.moasis.monolithicbe.domain.useraccount.entity.NaverUserInfo;
import site.moasis.monolithicbe.domain.useraccount.entity.OAuth2UserInfo;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.entity.UserDetailsImpl;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	private final UserAccountRepository userAccountRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(userRequest);

		OAuth2UserInfo oAuth2UserInfo = null;
		String provider = userRequest.getClientRegistration().getRegistrationId();

		if (provider.equals("naver")) {
			oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());

		}

		String providerId = oAuth2UserInfo.getProviderId();
		String username = provider + "_" + providerId;
		String uuid = UUID.randomUUID().toString().substring(0, 10);
		String password = "패스워드" + uuid;
		String email = oAuth2UserInfo.getEmail();
		UserRole role = UserRole.ROLE_USER;

		Optional<UserAccount> userAccountOptional = userAccountRepository.findByName(username);
		UserAccount userAccount;
		if (userAccountOptional.isPresent()) {
			userAccount = userAccountOptional.get();
		} else {
			userAccount = new UserAccount(email, password, username, role, provider, providerId);
			userAccountRepository.save(userAccount);
		}

		return new UserDetailsImpl(userAccount, oAuth2UserInfo);
	}
}

