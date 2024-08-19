package DM_plz.family_farm_main_server.auth.domain;

import java.util.Map;

import DM_plz.family_farm_main_server.member.domain.Account;
import DM_plz.family_farm_main_server.member.domain.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2UserInfo {
	private Map<String, Object> attributes;
	private String attributeKey;
	private String email;
	private String name;
	private String picture;
	private String provider;

	public static OAuth2UserInfo of(String provider,
		Map<String, Object> attributes) {
		switch (provider) {
			case "google":
				return ofGoogle(provider, attributes);
			case "kakao":
				return ofKakao(provider, attributes);
			case "naver":
				return ofNaver(provider, attributes);
			default:
				throw new RuntimeException();
		}
	}

	private static OAuth2UserInfo ofGoogle(String provider,
		Map<String, Object> attributes) {
		return OAuth2UserInfo.builder()
			.email((String)attributes.get("email"))
			.provider(provider)
			.attributes(attributes)
			.build();
	}

	private static OAuth2UserInfo ofKakao(String provider,
		Map<String, Object> attributes) {
		Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");

		return OAuth2UserInfo.builder()
			.email((String)kakaoAccount.get("email"))
			.provider(provider)
			.attributes(kakaoAccount)
			.build();
	}

	private static OAuth2UserInfo ofNaver(String provider,
		Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>)attributes.get("response");

		return OAuth2UserInfo.builder()
			.email((String)response.get("email"))
			.attributes(response)
			.provider(provider)
			.build();
	}

	public Account toEntity() {
		return Account.builder()
			.email(email)
			.userRole(UserRole.USER_ROLE)
			.build();
	}
}