package DM_plz.family_farm_main_server.auth.token.domain;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@RedisHash(value = "jwt", timeToLive = 60 * 60 * 24 * 7 * 4 * 6)
public class Token {

	@Id
	private String id;

	private String refreshToken;

	@Indexed
	private String accessToken;

	public Token updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
		return this;
	}

	public void updateAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
