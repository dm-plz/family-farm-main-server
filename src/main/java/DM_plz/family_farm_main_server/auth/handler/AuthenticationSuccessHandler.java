package DM_plz.family_farm_main_server.auth.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import DM_plz.family_farm_main_server.auth.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final TokenProvider tokenProvider;
	private static final String URI = "/auth/success";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		String accessToken = tokenProvider.generateAccessToken(authentication);
		tokenProvider.generateRefreshToken(authentication, accessToken);

		log.debug("accessToken {}", accessToken);

		String redirectUrl = UriComponentsBuilder.fromUriString(URI)
			.queryParam("accessToken", accessToken)
			.build().toUriString();

		response.sendRedirect(redirectUrl);
	}
}
