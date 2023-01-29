package site.moasis.monolithicbe;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import site.moasis.monolithicbe.domain.useraccount.UserRole;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class MonolithicBeApplication implements ApplicationRunner {

	private final UserAccountRepository userAccountRepository;

	public static void main(String[] args) {
		SpringApplication.run(MonolithicBeApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		userAccountRepository.save(UserAccount.builder()
				.email("jhj13062004@naver.com")
				.name("정학제")
				.password("0000")
				.phoneNumber("01038951306")
				.role(UserRole.ROLE_ADMIN)
				.build());
	}
}
