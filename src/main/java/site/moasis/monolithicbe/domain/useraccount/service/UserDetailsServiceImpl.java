package site.moasis.monolithicbe.domain.useraccount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserAccountRepository userAccountRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userAccountRepository.findOneByEmail(email).map(this::createUserDetails).orElseThrow(() -> {
			throw new UsernameNotFoundException(email + " -> 데이터베이스에서 찾을 수 없습니다.");
		});
	}

	private UserDetails createUserDetails(UserAccount userAccount) {
		try {
			return User.builder()
					.username(userAccount.getEmail())
					.password(userAccount.getPassword())
					.authorities(Collections.emptyList())
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
