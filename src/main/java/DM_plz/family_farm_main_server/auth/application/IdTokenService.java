package DM_plz.family_farm_main_server.auth.application;

import java.security.interfaces.RSAPublicKey;

import org.springframework.stereotype.Service;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import DM_plz.family_farm_main_server.common.exception.errorCode.AuthError;
import DM_plz.family_farm_main_server.common.exception.exception.AuthException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdTokenService {

	private final JwkService jwkService;

	public String getSubject(String idToken, String oauthProvider) {
		DecodedJWT jwtOrigin = JWT.decode(idToken);

		Jwk jwk = getJwk(oauthProvider, jwtOrigin);
		JWTVerifier verifier = getVerifier(jwk);
		DecodedJWT verifiedJWT = verifier.verify(jwtOrigin);
		return verifiedJWT.getSubject();
	}

	private Jwk getJwk(String oauthProvider, DecodedJWT jwtOrigin) {
		try {
			return jwkService.getJwkProvider(oauthProvider).get(jwtOrigin.getKeyId());
		} catch (JwkException e) {
			throw new AuthException(AuthError.GET_PUBLIC_KEY_FAIL, null);
		}
	}

	private static JWTVerifier getVerifier(Jwk jwk) {
		try {
			Algorithm algorithm = Algorithm.RSA256((RSAPublicKey)jwk.getPublicKey(), null);
			return JWT.require(algorithm).build();
		} catch (InvalidPublicKeyException e) {
			throw new AuthException(AuthError.INVALID_PUBLIC_KEY, null);
		}
	}

}
