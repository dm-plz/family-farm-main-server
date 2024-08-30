package DM_plz.family_farm_main_server.auth.token.application;

import static org.assertj.core.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import DM_plz.family_farm_main_server.auth.api.AuthController;
import DM_plz.family_farm_main_server.auth.application.IdTokenService;
import DM_plz.family_farm_main_server.auth.application.JwkService;
import DM_plz.family_farm_main_server.auth.dto.CustomAuthentication;
import DM_plz.family_farm_main_server.auth.token.dao.TokenRepository;
import DM_plz.family_farm_main_server.auth.token.domain.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class TokenProviderTest {

	@Autowired
	TokenProvider tokenProvider;

	@Autowired
	TokenRepository tokenRepository;

	@Autowired
	TokenService tokenService;

	@MockBean
	AuthController authController;

	@MockBean
	IdTokenService idTokenService;

	@MockBean
	JwkService jwkService;

	private String userId = "1";
	private String familyId = "2";
	private int accessTokenExpireTime = 1000 * 3;

	private int refreshTokenExpireTime = 1000 * 6;
	private int accessTokenExpireSecond = 3;
	private int refreshTokenExpireSecond = 6;

	@Test
	@DisplayName("authorization에서 access token 가져오기")
	void getAccessToken() {
		//Given
		String authorization = "Bearer JWT_MOCKUP";
		String expectResult = "JWT_MOCKUP";

		//When
		String result = tokenProvider.getAccessToken(authorization);

		//Then
		assertThat(result).isEqualTo(expectResult);
	}

	@Test
	@Transactional
	@DisplayName("access token 발급하기")
	void generateAccessToken() {
		//Given
		String subMockUp = "SUB_MOCKUP1";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.SECOND, accessTokenExpireSecond);
		Date expectExpireTime = calendar.getTime();

		//When
		String accessToken = tokenProvider.generateAccessToken(authentication, accessTokenExpireTime);
		String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken, refreshTokenExpireTime);
		boolean validateResult = tokenProvider.validateAccessToken(accessToken);
		Claims claims = tokenProvider.parseClaim(accessToken);

		//Then
		assertThat(validateResult).isEqualTo(true);
		assertThat(claims.getSubject()).isEqualTo(subMockUp);
		assertThat(claims.getExpiration()).isEqualTo(expectExpireTime);
		assertThat(claims.get("user-id")).isEqualTo(userId);
		assertThat(claims.get("family-id")).isEqualTo(familyId);
	}

	@Test
	@Transactional
	@DisplayName("refresh token 발급하기")
	void generateRefreshToken() {
		//Given
		String subMockUp = "SUB_MOCKUP2";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		String accessToken = tokenProvider.generateAccessToken(authentication, accessTokenExpireTime);

		Calendar calender = Calendar.getInstance();
		calender.setTime(new Date());
		calender.set(Calendar.MILLISECOND, 0);
		calender.add(Calendar.SECOND, refreshTokenExpireSecond);
		Date expectRefreshTokenExpireTime = calender.getTime();

		//When
		String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken, refreshTokenExpireTime);
		boolean validateResult = tokenProvider.validateRefreshToken(refreshToken);
		Claims claims = tokenProvider.parseClaim(refreshToken);

		//Then
		assertThat(validateResult).isEqualTo(true);
		assertThat(claims.getSubject()).isEqualTo(subMockUp);
		assertThat(claims.getExpiration()).isEqualTo(expectRefreshTokenExpireTime);
		assertThat(claims.get("user-id")).isEqualTo(userId);
		assertThat(claims.get("family-id")).isEqualTo(familyId);

	}

	@Test
	@Transactional
	@DisplayName("만료된 access token 검증 시 오류 발생 확인")
	void validateNoMatchMemberToken() throws InterruptedException {
		//Given
		String subMockUp = "SUB_MOCKUP3";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		String accessToken = tokenProvider.generateAccessToken(authentication, accessTokenExpireTime);
		Thread.sleep(accessTokenExpireTime);

		//When
		Throwable thrown = catchThrowable(() -> tokenProvider.validateAccessToken(accessToken));

		//Then
		assertThat(thrown).isInstanceOf(ExpiredJwtException.class);
	}

	@Test
	@DisplayName("유효하지 않은 사용자의 access token 검증 시 오류 발생 확인")
	void validateExpiredToken() throws InterruptedException {
		//Given
		String subMockUp = "SUB_MOCKUP4";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		String accessToken = tokenProvider.generateAccessToken(authentication, accessTokenExpireTime);
		//When
		Throwable thrown = catchThrowable(() -> tokenProvider.validateAccessToken(accessToken));

		//Then
		assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	@Transactional
	@DisplayName("access token이 만료되어 refresh token으로 access token reissue하기")
	void reissueToken() throws InterruptedException {
		//Given
		String subMockUp = "SUB_MOCKUP5";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		String accessToken = tokenProvider.generateAccessToken(authentication, accessTokenExpireTime);
		String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken,
			accessTokenExpireTime * 10);

		//When
		Thread.sleep(accessTokenExpireTime);
		String reissueAccessToken = tokenProvider.reissueAccessToken(accessToken, refreshToken);
		boolean validateResult = tokenProvider.validateAccessToken(reissueAccessToken);

		//Then
		Claims claims = tokenProvider.parseClaim(reissueAccessToken);
		assertThat(validateResult).isEqualTo(true);
		assertThat(claims.getSubject()).isEqualTo(subMockUp);
		assertThat(claims.get("user-id")).isEqualTo(userId);
		assertThat(claims.get("family-id")).isEqualTo(familyId);

		Token token = tokenRepository.findByAccessToken(reissueAccessToken).orElseThrow(EntityNotFoundException::new);
		assertThat(token.getAccessToken()).isEqualTo(reissueAccessToken);
	}

	@Test
	@Transactional
	@DisplayName("access token도 만료되고 refresh token도 만료된 경우 오류 확인")
	void expiredRefreshToken() throws InterruptedException {
		String subMockUp = "SUB_MOCKUP6";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		String accessToken = tokenProvider.generateAccessToken(authentication, accessTokenExpireTime);
		String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken, refreshTokenExpireTime);

		//When
		Thread.sleep(accessTokenExpireTime);
		String reissueAccessToken = tokenProvider.reissueAccessToken(accessToken, refreshToken);
		boolean validateResult = tokenProvider.validateAccessToken(reissueAccessToken);

		//Then
		Claims claims = tokenProvider.parseClaim(reissueAccessToken);
		assertThat(validateResult).isEqualTo(true);
		assertThat(claims.getSubject()).isEqualTo(subMockUp);
		assertThat(claims.get("user-id")).isEqualTo(userId);
		assertThat(claims.get("family-id")).isEqualTo(familyId);

		Token token = tokenRepository.findByAccessToken(reissueAccessToken).orElseThrow(EntityNotFoundException::new);
		assertThat(token.getAccessToken()).isEqualTo(reissueAccessToken);
	}

}