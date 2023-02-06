package site.moasis.monolithicbe.domain.useraccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.moasis.monolithicbe.domain.useraccount.entity.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
	Optional<UserAccount> findByEmail(String email);

	@Query(value = "select u.id from UserAccount u where u.email = :email")
	Optional<UUID> selectIdByEmail(@Param("email") String email);
	boolean existsByEmail(String email);
	Optional<UserAccount> findByRefreshToken(String refreshToken);
	Optional<UserAccount> findByName(String name);
}