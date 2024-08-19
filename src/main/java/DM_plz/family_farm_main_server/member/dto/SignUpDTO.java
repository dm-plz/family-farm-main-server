package DM_plz.family_farm_main_server.member.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import DM_plz.family_farm_main_server.member.domain.AuthProvider;
import DM_plz.family_farm_main_server.member.domain.BirthType;
import DM_plz.family_farm_main_server.member.domain.GroupRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpDTO {

	private String nickname;

	private AuthProvider OAuthProvider;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime birth;

	private BirthType birthType;

	private String email;

	private GroupRole groupRole;

	private String familyCode;

}
