package DM_plz.family_farm_main_server.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
	USER_ROLE("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	private final String key;
}
