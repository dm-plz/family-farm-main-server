package DM_plz.family_farm_main_server.auth.token.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import DM_plz.family_farm_main_server.auth.token.domain.Token;

public interface TokenRepository extends CrudRepository<Token, String> {

	Optional<Token> findByRefreshToken(String refreshToken);
}
