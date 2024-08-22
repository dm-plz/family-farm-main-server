package DM_plz.family_farm_main_server.auth.oauth.application;

import org.springframework.stereotype.Service;

import DM_plz.family_farm_main_server.common.exception.errorCode.AuthError;
import DM_plz.family_farm_main_server.common.exception.exception.CommonException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthServiceResolver {

	private final GoogleOAuthService googleOAuthService;
	private final KakaoOAuthService kakaoOAuthService;
	private final AppleOAuthService appleOAuthService;

	public OAuthService getOAuthService(String oAuthProvider) {
		return switch (oAuthProvider.toLowerCase()) {
			case "google" -> googleOAuthService;
			case "kakao" -> kakaoOAuthService;
			case "apple" -> appleOAuthService;
			default -> throw new CommonException(AuthError.INVALID_PROVIDER, null);
		};
	}
}
