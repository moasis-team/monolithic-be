package site.moasis.monolithicbe.domain.useraccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
	Optional<UserAccount> findByEmail(String email);
	boolean existsByEmail(String email);
	Optional<UserAccount> findByRefreshToken(String refreshToken);
}