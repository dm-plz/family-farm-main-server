package DM_plz.family_farm_main_server.auth.token.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import DM_plz.family_farm_main_server.auth.token.domain.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {

	Optional<Token> findByAccessToken(String accessToken);
}
