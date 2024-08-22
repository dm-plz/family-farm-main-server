package DM_plz.family_farm_main_server.auth.oauth.application;

public interface OAuthService {

	public String getAccessToken(String authorizationCode);

	public String getUserInfoUri();
}
