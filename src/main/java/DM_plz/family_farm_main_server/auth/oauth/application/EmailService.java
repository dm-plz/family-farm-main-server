package DM_plz.family_farm_main_server.auth.oauth.application;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmailService {
	public String getUserEmail(String accessToken, String userInfoUri) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(accessToken);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(
			userInfoUri,
			HttpMethod.GET,
			entity,
			String.class
		);

		// 응답 본문 (사용자 정보)을 반환
		String responseBody = response.getBody();
		ObjectMapper mapper = new ObjectMapper();

		String email;
		try {
			JsonNode root = mapper.readTree(responseBody);
			email = root.path("email").asText();
		} catch (JsonProcessingException e) {
			email = null;
		}
		return email;
	}
}
