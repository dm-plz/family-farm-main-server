package DM_plz.family_farm_main_server.auth.dto;

import java.time.LocalDateTime;

import DM_plz.family_farm_main_server.member.domain.BirthType;
import DM_plz.family_farm_main_server.member.domain.GroupRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OidcSignUp {

	@NotBlank
	private String nickname;

	@NotBlank
	@Past
	private LocalDateTime birth;

	@NotBlank
	private BirthType birthType;

	@NotBlank
	private String sub;

	@NotBlank
	private GroupRole groupRole;

	private String familyCode;

	@NotBlank
	private AlertToken alertToken;

}
