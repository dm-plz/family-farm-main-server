package DM_plz.family_farm_main_server.auth.token.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DM_plz.family_farm_main_server.auth.token.dao.TokenRepository;
import DM_plz.family_farm_main_server.auth.token.domain.Token;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final TokenRepository tokenRepository;

	public void deleteToken(String memberKey) {
		tokenRepository.deleteById(memberKey);
	}

	@Transactional
	public void saveOrUpdate(String memberKey, String refreshToken, String accessToken) {
		Token token = tokenRepository.findByAccessToken(accessToken)
			.map(o -> o.updateRefreshToken(refreshToken))
			.orElseGet(() -> new Token(memberKey, refreshToken, accessToken));
		tokenRepository.save(token);
	}

	@Transactional
	public Token findBySub(String sub) {
		return tokenRepository.findById(sub)
			.orElseThrow(EntityNotFoundException::new);
	}

	public Token findByAccessTokenOrThrow(String accessToken) {
		return tokenRepository.findByAccessToken(accessToken)
			.orElseThrow(() -> new EntityNotFoundException());
	}

	@Transactional
	public void updateToken(String accessToken, Token token) {
		token.updateAccessToken(accessToken);
		tokenRepository.save(token);
	}
}
