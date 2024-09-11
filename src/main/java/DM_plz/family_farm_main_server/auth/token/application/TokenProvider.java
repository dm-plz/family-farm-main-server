package DM_plz.family_farm_main_server.auth.token.application;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;

import DM_plz.family_farm_main_server.auth.dto.CustomAuthentication;
import DM_plz.family_farm_main_server.auth.dto.JwtSet;
import DM_plz.family_farm_main_server.auth.token.constants.TokenKey;
import DM_plz.family_farm_main_server.common.exception.errorCode.AuthError;
import DM_plz.family_farm_main_server.common.exception.exception.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TokenProvider {

	@Value("${jwt.key}")
	private String key;
	private JWTVerifier verifier;
	private SecretKey secretKey;
	// access token 만료 시간 : 30분
	private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L;
	// refresh token 만료 시간 : 6개월
	private final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 30 * 6;
	private final String GRANT_TYPE = "Bearer";

	private final TokenService tokenService;

	@PostConstruct
	private void setting() {
		secretKey = Keys.hmacShaKeyFor(key.getBytes());
		verifier = JWT.require(Algorithm.HMAC512(secretKey.getEncoded())).build();
	}

	public String getAccessToken(String authorization) {
		if (authorization == null)
			throw new AuthException(AuthError.NOT_EXIST_AUTHORIZATION, null);
		if (!authorization.startsWith(TokenKey.TOKEN_PREFIX))
			throw new AuthException(AuthError.INVALID_AUTHORIZATION_FORMAT, null);
		return authorization.substring(TokenKey.TOKEN_PREFIX.length());
	}

	public JwtSet generateJwtSet(CustomAuthentication authentication) {
		String accessToken = generateAccessToken(authentication);
		String refreshToken = generateRefreshToken(authentication);
		String grantType = getGrantType();
		return new JwtSet(accessToken, refreshToken, grantType);
	}

	public String generateAccessToken(CustomAuthentication authentication) {
		return generateToken(authentication, ACCESS_TOKEN_EXPIRE_TIME);
	}

	public String generateAccessToken(CustomAuthentication authentication, int expireTime) {
		return generateToken(authentication, expireTime);
	}

	public String generateRefreshToken(CustomAuthentication authentication) {
		String refreshToken = generateToken(authentication, REFRESH_TOKEN_EXPIRE_TIME);
		tokenService.saveOrUpdate(authentication.getSubject(), authentication.getUserId(),
			refreshToken);
		return refreshToken;
	}

	public String generateRefreshToken(CustomAuthentication authentication, int expireTime) {
		String refreshToken = generateToken(authentication, expireTime);
		tokenService.saveOrUpdate(authentication.getSubject(), authentication.getUserId(),
			refreshToken);
		return refreshToken;
	}

	private String generateToken(CustomAuthentication authentication, long expireTime) {
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + expireTime);

		Map<String, Object> claims = new HashMap<>();
		claims.put("user-id", authentication.getUserId());
		claims.put("family-id", authentication.getFamilyId());

		return Jwts.builder()
			.subject(authentication.getSubject())
			.issuedAt(now)
			.expiration(expiredDate)
			.claims(claims)
			.signWith(secretKey, Jwts.SIG.HS512)
			.compact();
	}

	public String getGrantType() {
		return GRANT_TYPE;
	}

	public CustomAuthentication getAuthentication(String token) {
		Claims claims = forceParseClaim(token);
		String sub = claims.getSubject();
		Long userId = (Long)claims.get("user-id");
		Long familyId = (Long)claims.get("family-id");
		return new CustomAuthentication(sub, userId, familyId);
	}

	public JwtSet reissueToken(RefreshTokenDTO refreshTokenDTO) {
		String refreshToken = refreshTokenDTO.getRefreshToken();
		try {
			validateToken(refreshToken);
		} catch (TokenExpiredException e) {
			throw new TokenException(TokenError.EXPIRED_REFRESH_TOKEN, refreshToken);
		}
		tokenService.findByRefreshToken(refreshToken);
		String reissueAccessToken = generateAccessToken(getAuthentication(refreshToken));
		String reissueRefreshToken = generateRefreshToken(getAuthentication(refreshToken));
		tokenService.updateToken(reissueRefreshToken);
		return new JwtSet(reissueAccessToken, reissueRefreshToken, GRANT_TYPE);
	}

	public boolean validateToken(String token) {
		verifier.verify(token);
		return true;
	}

	public Claims parseClaim(String token) {
		return Jwts.parser().verifyWith(secretKey).build()
			.parseSignedClaims(token).getPayload();
	}

	public Claims forceParseClaim(String token) {
		try {
			return Jwts.parser().verifyWith(secretKey).build()
				.parseSignedClaims(token).getPayload();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
