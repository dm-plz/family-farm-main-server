package DM_plz.family_farm_main_server.auth.token.application;

import static org.assertj.core.api.Assertions.*;

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
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class TokenServiceTest {

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
	@Transactional
	@DisplayName("token 삭제 확인")
	void deleteToken() {
		//Given
		String subMockUp = "SUB_MOCKUP1";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		String accessToken = tokenProvider.generateAccessToken(authentication);
		String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken, refreshTokenExpireTime);

		//When
		Claims claims = tokenProvider.parseClaim(refreshToken);
		String sub = claims.getSubject();
		tokenService.deleteToken(sub);
		Throwable thrown = catchThrowable(() -> tokenService.findByAccessTokenOrThrow(accessToken));
		//Then

		assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	@Transactional
	@DisplayName("access token save 기능 테스트")
	void save() {
		//Given
		String subMockUp = "SUB_MOCKUP2";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		String accessToken = tokenProvider.generateAccessToken(authentication);
		String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken, refreshTokenExpireTime);

		//When
		Token token = tokenRepository.findByAccessToken(accessToken).orElseThrow(EntityNotFoundException::new);

		//Then
		assertThat(token.getAccessToken()).isEqualTo(accessToken);
	}

	@Test
	@Transactional
	@DisplayName("access token update 기능 테스트")
	void update() {
		//Given
		String subMockUp = "SUB_MOCKUP2";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		String accessToken = tokenProvider.generateAccessToken(authentication);
		String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken, refreshTokenExpireTime);

		//When
		Token token = tokenRepository.findByAccessToken(accessToken).orElseThrow(EntityNotFoundException::new);
		String oldAccessToken = token.getAccessToken();
		String updatedAccessToken = tokenProvider.generateAccessToken(authentication);
		String updatedRefreshToken = tokenProvider.generateRefreshToken(authentication, accessToken,
			refreshTokenExpireTime);
		Token updatedToken = tokenRepository.findByAccessToken(updatedAccessToken)
			.orElseThrow(EntityNotFoundException::new);

		//Then
		assertThat(oldAccessToken).isEqualTo(accessToken);
		assertThat(updatedToken.getAccessToken()).isEqualTo(updatedAccessToken);
	}

	@Test
	@DisplayName("sub로 token 찾기")
	void findBySubOrThrow() {
		String subMockUp = "SUB_MOCKUP3";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		String accessToken = tokenProvider.generateAccessToken(authentication);
		String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken, refreshTokenExpireTime);

		//When
		Token token = tokenRepository.findByAccessToken(accessToken).orElseThrow(EntityNotFoundException::new);

		//Then
		assertThat(token.getId()).isEqualTo(subMockUp);
	}

	@Test
	@DisplayName("access token find 기능 테스트")
	void findByAccessTokenOrThrow() {
		String subMockUp = "SUB_MOCKUP4";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		String accessToken = tokenProvider.generateAccessToken(authentication);
		String refreshToken = tokenProvider.generateRefreshToken(authentication, accessToken, refreshTokenExpireTime);

		//When
		Token token = tokenRepository.findByAccessToken(accessToken).orElseThrow(EntityNotFoundException::new);

		//Then
		assertThat(token.getAccessToken()).isEqualTo(accessToken);
	}
}