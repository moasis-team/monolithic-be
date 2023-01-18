package site.moasis.monolithicbe.domain.useraccount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountJoinRequestDto;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;

@RequiredArgsConstructor
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

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
}
