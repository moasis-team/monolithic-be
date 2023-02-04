package site.moasis.monolithicbe.domain.useraccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
	Optional<UserAccount> findByEmail(String email);
	boolean existsByEmail(String email);
	Optional<UserAccount> findByRefreshToken(String refreshToken);
	Optional<UserAccount> findByName(String name);
}