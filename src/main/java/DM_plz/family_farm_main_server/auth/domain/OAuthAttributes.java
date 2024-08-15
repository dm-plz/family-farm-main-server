package DM_plz.family_farm_main_server.auth.domain;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String attributeKey;
	private String email;
	private String name;
	private String picture;
	private String provider;
	
	public static OAuthAttributes of(String provider, String attributeKey,
		Map<String, Object> attributes) {
		switch (provider) {
			case "google":
				return ofGoogle(provider, attributeKey, attributes);
			case "kakao":
				return ofKakao(provider, "email", attributes);
			case "naver":
				return ofNaver(provider, "id", attributes);
			default:
				throw new RuntimeException();
		}
	}

	private static OAuthAttributes ofGoogle(String provider, String attributeKey,
		Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.email((String)attributes.get("email"))
			.provider(provider)
			.attributes(attributes)
			.attributeKey(attributeKey)
			.build();
	}

	private static OAuthAttributes ofKakao(String provider, String attributeKey,
		Map<String, Object> attributes) {
		Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");

		return OAuthAttributes.builder()
			.email((String)kakaoAccount.get("email"))
			.provider(provider)
			.attributes(kakaoAccount)
			.attributeKey(attributeKey)
			.build();
	}

	private static OAuthAttributes ofNaver(String provider, String attributeKey,
		Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>)attributes.get("response");

		return OAuthAttributes.builder()
			.email((String)response.get("email"))
			.attributes(response)
			.provider(provider)
			.attributeKey(attributeKey)
			.build();
	}

	public Map<String, Object> convertToMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("id", attributeKey);
		map.put("key", attributeKey);
		map.put("email", email);
		map.put("provider", provider);

		return map;
	}
}