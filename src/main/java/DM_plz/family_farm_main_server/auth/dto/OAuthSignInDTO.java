package DM_plz.family_farm_main_server.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OAuthSignInDTO {

	private String oAuthProvider;
	private String authorizationCode;
	private String email;

	public OAuthSignInDTO(String oAuthProvider, String authorizationCode) {
		this.oAuthProvider = oAuthProvider;
		this.authorizationCode = authorizationCode;
	}
}
