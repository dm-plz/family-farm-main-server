package DM_plz.family_farm_main_server.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OidcSignIn {

	private String oauthProvider;
	private String idToken;

}
