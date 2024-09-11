package DM_plz.family_farm_main_server.auth.token.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@RedisHash(value = "jwt", timeToLive = 60 * 60 * 24 * 7 * 4 * 6)
public class Token {

	@Id
	private String sub;

	private Long userId;

	@Indexed
	private String refreshToken;

	public Token updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
		return this;
	}
}
