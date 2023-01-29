package site.moasis.monolithicbe.domain.useraccount.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import site.moasis.monolithicbe.domain.useraccount.UserRole;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class UserAccount {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(unique = true)
	private String email;
	private String password;
	private String name;
	private String phoneNumber;
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Builder
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