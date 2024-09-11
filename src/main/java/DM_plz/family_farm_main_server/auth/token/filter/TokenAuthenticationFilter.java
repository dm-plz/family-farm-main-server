package DM_plz.family_farm_main_server.auth.token.filter;

import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;

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

		if (accessToken == null) {
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("accessToken = " + accessToken);
		System.out.println("authentication = " + authentication.toString());

		filterChain.doFilter(request, response);

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
