package DM_plz.family_farm_main_server.auth.token.filter;

import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;
import java.util.Objects;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DM_plz.family_farm_main_server.auth.token.application.TokenProvider;
import DM_plz.family_farm_main_server.auth.token.constants.TokenKey;
import DM_plz.family_farm_main_server.common.exception.ErrorResponse;
import DM_plz.family_farm_main_server.common.exception.errorCode.TokenError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String accessToken = resolveAccessToken(request);

		// 로그인, 회원가입, 토큰 재발행 시
		// 이외의 경우는 Spring Security에서 차단됨
		if (accessToken == null || isReissueToken(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			tokenProvider.validateToken(accessToken);
		} catch (TokenExpiredException e) {
			handleException(response, TokenError.EXPIRED_ACCESS_TOKEN, accessToken);
			return;
		} catch (JWTVerificationException e) {
			handleException(response, TokenError.FAIL_VERIFY_JWT, accessToken);
			return;
		}
		setAuthentication(accessToken);
		filterChain.doFilter(request, response);
	}

	private static boolean isReissueToken(HttpServletRequest request) {
		// 토큰 재발급 요청 확인 (Http 메서드가 PATCH이고 요청 URI가 /auth/token/reissuance인지 확인)
		return Objects.equals(request.getMethod(), HttpMethod.PATCH.toString()) && Objects.equals(request.getRequestURI(), "\"/auth/token/reissuance\"");
	}

	private void handleException(HttpServletResponse response, TokenError tokenError, String accessToken) throws IOException {
		// GlobalExceptionHandler를 사용할 수 없기 때문에 필터 내부에서 자체적으로 예외 핸들링
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setStatus(tokenError.getHttpStatus().value());
		ErrorResponse errorResponse = ErrorResponse.builder()
			.code(tokenError.getCode())
			.message(tokenError.getMessage())
			.data(accessToken)
			.build();

		response.getOutputStream().write(objectMapper.writeValueAsString(errorResponse).getBytes());
	}

	private void setAuthentication(String accessToken) {
		Authentication authentication = tokenProvider.getAuthentication(accessToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String resolveAccessToken(HttpServletRequest request) {
		String token = request.getHeader(AUTHORIZATION);
		if (ObjectUtils.isEmpty(token) || !token.startsWith(TokenKey.TOKEN_PREFIX)) {
			return null;
		}
		return token.substring(TokenKey.TOKEN_PREFIX.length());
	}
}
