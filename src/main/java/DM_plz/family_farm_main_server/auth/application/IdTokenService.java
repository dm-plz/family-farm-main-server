package DM_plz.family_farm_main_server.auth.application;

import java.security.interfaces.RSAPublicKey;
import java.util.List;

import org.springframework.stereotype.Service;

import com.auth0.jwk.InvalidPublicKeyException;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import DM_plz.family_farm_main_server.auth.dto.OidcSignIn;
import DM_plz.family_farm_main_server.common.exception.errorCode.OidcErrorCode;
import DM_plz.family_farm_main_server.common.exception.exception.OidcException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdTokenService {

	private final JwkService jwkService;
	private final List<String> supportOAuthProvider;

	public String getCustomSub(OidcSignIn oidcSignIn) {
		isInvalidOAuthProvider(oidcSignIn.getOauthProvider());
		DecodedJWT verifiedJWT = verifyJWT(oidcSignIn.getOauthProvider(), oidcSignIn.getIdToken());
		return oidcSignIn.getOauthProvider() + verifiedJWT.getSubject();
	}

	public void isInvalidOAuthProvider(String oauthProvider) {
		try {
			supportOAuthProvider.contains(oauthProvider);
		} catch (IndexOutOfBoundsException e) {
			throw new OidcException(OidcErrorCode.INVALID_PROVIDER);
		}
	}

	public DecodedJWT verifyJWT(String oauthProvider, String jwt) {
		DecodedJWT jwtOrigin = JWT.decode(jwt);
		Jwk jwk = getJwk(oauthProvider, jwtOrigin);
		JWTVerifier verifier = getVerifier(jwk);
		return verifier.verify(jwtOrigin);
	}

	private Jwk getJwk(String oauthProvider, DecodedJWT jwtOrigin) {
		try {
			return jwkService.getJwkProvider(oauthProvider).get(jwtOrigin.getKeyId());
		} catch (JwkException e) {
			throw new OidcException(OidcErrorCode.FAIL_TO_GET_JWK);
		}
	}

	private static JWTVerifier getVerifier(Jwk jwk) {
		try {
			Algorithm algorithm = Algorithm.RSA256(getPublicKey(jwk), null);
			return JWT.require(algorithm).build();
		} catch (InvalidPublicKeyException e) {
			throw new OidcException(OidcErrorCode.INVALID_PUBLIC_KEY);
		}
	}

	private static RSAPublicKey getPublicKey(Jwk jwk) throws InvalidPublicKeyException {
		return (RSAPublicKey)jwk.getPublicKey();
	}
}
