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
	private void setSecretKey() {
		secretKey = Keys.hmacShaKeyFor(key.getBytes());
	}

	@PostConstruct
	private void setVerifier() {
		verifier = JWT.require(Algorithm.HMAC512(secretKey.getEncoded())).build();
	}

	public String getAccessToken(String authorization) {
		if (authorization == null)
			throw new AuthException(AuthError.AUTHORIZATION_NOT_EXIST, null);
		if (!authorization.startsWith(TokenKey.TOKEN_PREFIX))
			throw new AuthException(AuthError.INVALID_AUTHORIZATION_FORMAT, null);
		return authorization.substring(TokenKey.TOKEN_PREFIX.length());
	}

	public JwtSetDTO generateSuccessToken(CustomAuthentication authentication) {
		String accessToken = generateAccessToken(authentication);
		String refreshToken = generateRefreshToken(authentication, accessToken);
		String grantType = getGrantType();
		return new JwtSetDTO(accessToken, refreshToken, grantType);
	}

	public String generateAccessToken(CustomAuthentication authentication) {
		return generateToken(authentication, ACCESS_TOKEN_EXPIRE_TIME);
	}

	public String generateAccessToken(CustomAuthentication authentication, int expireTime) {
		return generateToken(authentication, expireTime);
	}

	public String generateRefreshToken(CustomAuthentication authentication, String accessToken) {
		String refreshToken = generateToken(authentication, REFRESH_TOKEN_EXPIRE_TIME);
		tokenService.saveOrUpdate(authentication.getSubject(), refreshToken, accessToken);
		return refreshToken;
	}

	public String generateRefreshToken(CustomAuthentication authentication, String accessToken, int expireTime) {
		String refreshToken = generateToken(authentication, expireTime);
		tokenService.saveOrUpdate(authentication.getSubject(), refreshToken, accessToken);
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

	public long getAccessTokenExpireTime() {
		return ACCESS_TOKEN_EXPIRE_TIME;
	}

	public long getRefreshTokenExpireTime() {
		return REFRESH_TOKEN_EXPIRE_TIME;
	}

	public String getGrantType() {
		return GRANT_TYPE;
	}

	public CustomAuthentication getAuthentication(String token) {
		Claims claims = forceParseClaim(token);
		String sub = claims.getSubject();
		String userId = (String)claims.get("user-id");
		String familyId = (String)claims.get("family-id");
		return new CustomAuthentication(sub, userId, familyId);
	}

	public String reissueAccessToken(String accessToken, String refreshToken) {
		if (!isExpired(accessToken))
			throw new AuthException(AuthError.ACCESS_TOKEN_NOT_EXPIRED, null);

		Token token = tokenService.findByAccessTokenOrThrow(accessToken);
		if (!token.getRefreshToken().equals(refreshToken))
			throw new AuthException(AuthError.INVALID_REFRESH_TOKEN, null);

		String reissueAccessToken = generateAccessToken(getAuthentication(refreshToken));
		tokenService.updateToken(reissueAccessToken, token);
		return reissueAccessToken;
	}

	public boolean isExpired(String token) {
		try {
			Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
			return false;
		} catch (ExpiredJwtException e) {
			return true;
		}
	}

	public boolean validateAccessToken(String accessToken) {
		Claims claims = parseClaim(accessToken);
		if (!claims.getExpiration().after(new Date()))
			throw new CommonException(AuthError.TOKEN_EXPIRED, accessToken);
		tokenService.findByAccessTokenOrThrow(accessToken);
		return true;
	}

	public boolean validateRefreshToken(String refreshToken) {
		Claims claims = parseClaim(refreshToken);
		if (!claims.getExpiration().after(new Date()))
			throw new CommonException(AuthError.TOKEN_EXPIRED, refreshToken);
		Token findToken = tokenService.findBySubOrThrow(claims.getSubject());
		return findToken.getRefreshToken().equals(refreshToken);
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
