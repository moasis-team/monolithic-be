package site.moasis.monolithicbe.domain.useraccount.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.moasis.monolithicbe.domain.useraccount.UserRole;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class UserAccount {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "useraccount_id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(unique = true)
	private String email;
	private String password;
	private String name;
	private String phoneNumber;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	private String refreshToken;
	private String provider;
	private String providerId;

	@Builder
	private UserAccount(String email, String password, String name, String phoneNumber, UserRole role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}
	public UserAccount(String email, String password, String name, UserRole role, String provider, String providerId) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
	}

	public static UserAccount create(String email, String password, String name, String phoneNumber, UserRole role) {
		return new UserAccount(email, password, name, phoneNumber, role);
	}

	public void changeRefreshToken(String refreshToken){
		this.refreshToken = refreshToken;
	}

	public void changePassword(String password) {
		this.password = password;
	}
}