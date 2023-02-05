package site.moasis.monolithicbe.integration.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import site.moasis.monolithicbe.controller.useraccount.UserAccountController;
import site.moasis.monolithicbe.domain.useraccount.dto.UserAccountDto;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;

@SpringBootTest
@Transactional
@TestComponent
public class UserRepositoryTest {

    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    UserAccountController userAccountController;

    @Test
    void 회원가입(){
        // given
        var joinUser = new UserAccountDto.UserAccountJoinRequestDto(
                "csw1111@moasis.com","1234","1234","1234");
        userAccountController.join(joinUser);
        // when
        userAccountRepository.findByEmail("csw1111@moasis.com").orElseThrow();
    }
}
