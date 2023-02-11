package site.moasis.monolithicbe.domain.useraccount;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UserAccountInfo {
	private UUID userId;
	private String email;
	private String name;
	private String phoneNumber;
	private UserRole role;
	private String provider;
	private Boolean isDeleted;
}
