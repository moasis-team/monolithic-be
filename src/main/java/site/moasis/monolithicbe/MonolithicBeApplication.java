package site.moasis.monolithicbe;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import site.moasis.monolithicbe.domain.article.entity.Article;
import site.moasis.monolithicbe.domain.article.repository.ArticleRepository;
import site.moasis.monolithicbe.domain.useraccount.UserRole;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;
import site.moasis.monolithicbe.domain.useraccount.repository.UserAccountRepository;

@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class MonolithicBeApplication implements ApplicationRunner {

	private final UserAccountRepository userAccountRepository;
	private final ArticleRepository articleRepository;

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

		userAccountRepository.save(UserAccount.builder()
				.email("test")
				.name("test")
				.password("test")
				.phoneNumber("test")
				.role(UserRole.ROLE_USER)
				.build());

		articleRepository.save(Article.builder()
				.title("게시글 예시 제목")
				.content("게시글 예시 내용")
				.build());
	}
}
