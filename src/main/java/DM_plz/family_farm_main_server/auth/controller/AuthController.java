package DM_plz.family_farm_main_server.auth.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import DM_plz.family_farm_main_server.auth.dto.EmailAuthentication;
import DM_plz.family_farm_main_server.auth.dto.OAuthSignInDTO;
import DM_plz.family_farm_main_server.auth.dto.SignInSuccessTokenDTO;
import DM_plz.family_farm_main_server.auth.oauth.application.EmailService;
import DM_plz.family_farm_main_server.auth.oauth.application.JwtService;
import DM_plz.family_farm_main_server.auth.oauth.application.OAuthService;
import DM_plz.family_farm_main_server.auth.oauth.application.OAuthServiceResolver;
import DM_plz.family_farm_main_server.common.exception.errorCode.AuthError;
import DM_plz.family_farm_main_server.common.exception.exception.CommonException;
import DM_plz.family_farm_main_server.member.dao.AccountRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AccountRepository accountRepository;
	private final OAuthServiceResolver oAuthServiceResolver;
	private final EmailService emailService;
	private final JwtService jwtService;

	@GetMapping("/login/oauth2/code/{provider}")
	public ResponseEntity<String> authorizationCodeProxy(@PathVariable("provider") String oAuthProvider,
		@RequestParam("code") String authorizationCode) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String url = "http://localhost:8080/auth/oauth/sign-in";

		OAuthSignInDTO oAuthSignInDTO = new OAuthSignInDTO(oAuthProvider, authorizationCode);

		HttpEntity<OAuthSignInDTO> requestEntity = new HttpEntity<>(oAuthSignInDTO, headers);

		return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
	}

	@PostMapping("/auth/oauth/sign-in")
	public ResponseEntity<SignInSuccessTokenDTO> oauthSignIn(@RequestBody OAuthSignInDTO oAuthSignInDTO) {

		String oAuthProvider = oAuthSignInDTO.getOAuthProvider();
		String authorizationCode = oAuthSignInDTO.getAuthorizationCode();
		String accessToken = "";
		String userInfoUri = "";

		OAuthService oAuthService = oAuthServiceResolver.getOAuthService(oAuthProvider);
		accessToken = oAuthService.getAccessToken(authorizationCode);
		userInfoUri = oAuthService.getUserInfoUri();

		String email = emailService.getUserEmail(accessToken, userInfoUri);
		oAuthSignInDTO.setEmail(email);

		if (email == null)
			throw new CommonException(AuthError.EMAIL_PARSING_ERROR, null);

		if (accountRepository.findByEmail(email).isEmpty())
			throw new CommonException(AuthError.UNKNOWN_USER, oAuthSignInDTO);

		Authentication authentication = new EmailAuthentication(email);
		SignInSuccessTokenDTO signInSuccessTokenDTO = jwtService.generateSuccessToken(authentication);
		return ResponseEntity.ok(signInSuccessTokenDTO);
	}
}
