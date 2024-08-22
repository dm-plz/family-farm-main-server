package DM_plz.family_farm_main_server.auth.oauth.application;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import DM_plz.family_farm_main_server.auth.dto.SignInSuccessTokenDTO;
import DM_plz.family_farm_main_server.auth.token.application.TokenProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	private final TokenProvider tokenProvider;

	public SignInSuccessTokenDTO generateSuccessToken(Authentication authentication) {
		String accessToken = tokenProvider.generateAccessToken(authentication);
		String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken);
		String grantType = tokenProvider.getGrantType();
		return new SignInSuccessTokenDTO(accessToken, refreshToken, grantType);
	}
}
