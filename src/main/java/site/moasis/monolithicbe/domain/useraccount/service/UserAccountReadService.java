package site.moasis.monolithicbe.domain.useraccount.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.BusinessException;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAccountReadService {

    private final UserAccountRepository userAccountRepository;

    public void checkDuplicatedEmail(String email){
        if(userAccountRepository.existsByEmail(email)){
            throw new BusinessException(ErrorCode.DUPLICATE);
        };
    }
}
