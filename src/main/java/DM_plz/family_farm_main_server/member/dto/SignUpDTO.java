package DM_plz.family_farm_main_server.member.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import DM_plz.family_farm_main_server.auth.dto.AlertToken;
import DM_plz.family_farm_main_server.member.domain.BirthType;
import DM_plz.family_farm_main_server.member.domain.GroupRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {

	private String nickname;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime birth;

	private BirthType birthType;

	private String sub;

	private GroupRole groupRole;

	private String familyCode;

	private AlertToken alertToken;

}
