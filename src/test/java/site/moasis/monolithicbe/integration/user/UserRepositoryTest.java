package site.moasis.monolithicbe.integration.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Test
    void 유저정보조회() {
        UserAccount userAccount = userAccountRepository.findByEmail("jhj13062004@naver.com").orElseThrow();
        System.out.println("userAccount id = " + userAccount.getId());
    }
}
