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

	public void deleteToken(String sub) {
		tokenRepository.deleteById(sub);
	}

	@Transactional
	public void saveOrUpdate(String sub, Long userId, String refreshToken) {
		Token token = tokenRepository.findByRefreshToken(refreshToken)
			.map(findToken -> findToken.updateRefreshToken(refreshToken))
			.orElseGet(() -> new Token(sub, userId, refreshToken));
		tokenRepository.save(token);
	}

	@Transactional
	public Token findBySub(String sub) {
		return tokenRepository.findById(sub)
			.orElseThrow(EntityNotFoundException::new);
	}

	@Transactional
	public void updateToken(String refreshToken) {
		Token token = tokenRepository.findByRefreshToken(refreshToken).orElseThrow();
		token.updateRefreshToken(refreshToken);
		tokenRepository.save(token);
	}
}
