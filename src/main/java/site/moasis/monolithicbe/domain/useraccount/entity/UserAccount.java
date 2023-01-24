package site.moasis.monolithicbe.domain.useraccount.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.moasis.monolithicbe.domain.useraccount.UserRole;

@NoArgsConstructor
@Getter
@Entity
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String email;
	private String password;
	private String name;
	private String phoneNumber;
	@Enumerated(EnumType.STRING)
	private UserRole role;

	private UserAccount(String email, String password, String name, String phoneNumber, UserRole role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}

	public static UserAccount create(String email, String password, String name, String phoneNumber, UserRole role) {
		return new UserAccount(email, password, name, phoneNumber, role);
	}
}