package site.moasis.monolithicbe.domain.useraccount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.domain.useraccount.UserRole;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountDto;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAccountWriteService {

	private final UserAccountRepository userAccountRepository;
	private final PasswordEncoder passwordEncoder;

	private void checkDuplicatedEmail(String email){
		if(userAccountRepository.existsByEmail(email)){
			throw new BusinessException(ErrorCode.DUPLICATE);
		};
	}
	public UserAccount signUp(UserAccountDto.UserAccountJoinRequestDto userAccountJoinRequestDto) {
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
}
