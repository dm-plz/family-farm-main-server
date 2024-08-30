package DM_plz.family_farm_main_server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtSetDTO {

	private String accessToken;
	private String refreshToken;
	private String grantType;

}
