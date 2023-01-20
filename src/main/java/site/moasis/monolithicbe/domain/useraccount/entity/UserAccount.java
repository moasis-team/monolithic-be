package site.moasis.monolithicbe.domain.useraccount.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String email;
	private String password;
	private String name;
	private String phoneNumber;

	private UserAccount(String email, String password, String name, String phoneNumber) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public static UserAccount create(String email, String password, String name, String phoneNumber) {
		return new UserAccount(email, password, name, phoneNumber);
	}
}
