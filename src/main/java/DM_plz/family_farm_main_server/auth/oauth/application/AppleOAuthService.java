package DM_plz.family_farm_main_server.auth.oauth.application;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppleOAuthService implements OAuthService {

	@Value("${oauth.registration.apple.client-id}")
	private String clientId;

	@Value("${oauth.registration.apple.client-secret}")
	private String clientSecret;

	@Value("${oauth.registration.apple.redirect-uri}")
	private String redirectUri;

	@Value("${oauth.provider.apple.token-uri}")
	private String tokenUri;

	@Value("${oauth.provider.apple.user-info-uri}")
	private String userInfoUri;

	public String getAccessToken(String code) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("code", code);
		requestBody.add("client_id", clientId);
		requestBody.add("client_secret", clientSecret);
		requestBody.add("redirect_uri", redirectUri);
		requestBody.add("grant_type", "authorization_code");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

		ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, request, Map.class);
		String accessToken = (String)response.getBody().get("access_token");
		log.info("accessToken = {}", accessToken);
		return accessToken;
	}

	public String getUserInfoUri() {
		return userInfoUri;
	}
}
