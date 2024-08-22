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

@Service
public class GoogleOAuthService implements OAuthService {

	@Value("${oauth.registration.google.client-id}")
	private String clientId;

	@Value("${oauth.registration.google.client-secret}")
	private String clientSecret;

	@Value("${oauth.registration.google.redirect-uri}")
	private String redirectUri;

	@Value("${oauth.provider.google.token-uri}")
	private String tokenUri;

	@Value("${oauth.provider.google.user-info-uri}")
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
		return (String)response.getBody().get("access_token");
	}

	public String getUserInfoUri() {
		return userInfoUri;
	}
}
