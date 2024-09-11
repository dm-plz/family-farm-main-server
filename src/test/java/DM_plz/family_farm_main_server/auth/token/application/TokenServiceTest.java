package DM_plz.family_farm_main_server.auth.token.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

	private final Long userId = 1L;
	private final Long familyId = 2L;
	private final int accessTokenExpireTime = 1000 * 3;
	private final int refreshTokenExpireTime = 1000 * 6;

	@Test
	@DisplayName("token 삭제 확인")
	void deleteToken() {
		//Given
		String subMockUp = "SUB_MOCKUP1";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		String accessToken = tokenProvider.generateAccessToken(authentication, accessTokenExpireTime);
		String refreshToken = tokenProvider.generateRefreshToken(authentication, refreshTokenExpireTime);

		//When
		Claims claims = tokenProvider.parseClaim(refreshToken);
		String sub = claims.getSubject();
		tokenService.deleteToken(sub);
		Throwable thrown = catchThrowable(() -> tokenService.findBySub(sub));
		//Then

		assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	@DisplayName("sub로 token 찾기")
	void findBySubOrThrow() {
		//Given
		String subMockUp = "SUB_MOCKUP3";

		//When
		Token token = tokenRepository.findById(subMockUp).orElseThrow(EntityNotFoundException::new);

		//Then
		assertThat(token.getSub()).isEqualTo(subMockUp);
	}

	@Test
	@DisplayName("refresh token find 기능 테스트")
	void findByAccessTokenOrThrow() {
		String subMockUp = "SUB_MOCKUP4";
		CustomAuthentication authentication = new CustomAuthentication(subMockUp, userId, familyId);
		String refreshToken = tokenProvider.generateRefreshToken(authentication, refreshTokenExpireTime);

		//When
		Token token = tokenRepository.findByRefreshToken(refreshToken).orElseThrow(EntityNotFoundException::new);

		//Then
		assertThat(token.getRefreshToken()).isEqualTo(refreshToken);
	}
}