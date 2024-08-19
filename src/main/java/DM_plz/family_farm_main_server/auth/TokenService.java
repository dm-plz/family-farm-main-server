package DM_plz.family_farm_main_server.auth;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DM_plz.family_farm_main_server.auth.redis.entity.Token;
import DM_plz.family_farm_main_server.auth.redis.repository.TokenRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {

	private final TokenRepository tokenRepository;

	public void deleteRefreshToken(String memberKey) {
		tokenRepository.deleteById(memberKey);
	}

	@Transactional
	public void saveOrUpdate(String memberKey, String refreshToken, String accessToken) {
		Token token = tokenRepository.findByAccessToken(accessToken)
			.map(o -> o.updateRefreshToken(refreshToken))
			.orElseGet(() -> new Token(memberKey, refreshToken, accessToken));

		tokenRepository.save(token);
	}

	public Token findByAccessTokenOrThrow(String accessToken) {
		return tokenRepository.findByAccessToken(accessToken)
			.orElseThrow();
	}

	@Transactional
	public void updateToken(String accessToken, Token token) {
		token.updateAccessToken(accessToken);
		tokenRepository.save(token);
	}
}
