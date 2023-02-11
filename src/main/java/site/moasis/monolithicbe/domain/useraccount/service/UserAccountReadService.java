package site.moasis.monolithicbe.domain.useraccount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.domain.useraccount.UserAccountInfo;
import site.moasis.monolithicbe.domain.useraccount.UserAccountMapper;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAccountReadService {
	private final UserAccountRepository userAccountRepository;

	public UserAccountInfo findUserAccount(UUID userId) {
		Optional<UserAccount> userAccountOptional = userAccountRepository.findById(userId);
		if (userAccountOptional.isEmpty()) {
			throw new BusinessException(ErrorCode.INVALID_PARAMETER, "user.byCredential", List.of(String.valueOf(userId)));
		}
		UserAccount userAccount = userAccountOptional.get();
		return UserAccountMapper.INSTANCE.toUserAccountInfo(userAccount);
	}
}
