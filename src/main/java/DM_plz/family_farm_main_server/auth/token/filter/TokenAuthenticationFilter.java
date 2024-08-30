package DM_plz.family_farm_main_server.auth.token.filter;

import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import DM_plz.family_farm_main_server.auth.token.application.TokenProvider;
import DM_plz.family_farm_main_server.auth.token.constants.TokenKey;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String accessToken = resolveAccessToken(request);

		if (accessToken == null) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			tokenProvider.validateAccessToken(accessToken);
		} catch (ExpiredJwtException e) {
			handleException(response, "jwt이 만료되었습니다.", HttpServletResponse.SC_BAD_REQUEST);
			return;
		} catch (MalformedJwtException e) {
			handleException(response, "jwt 형식이 올바르지 않습니다.", HttpServletResponse.SC_BAD_REQUEST);
			return;
		} catch (SecurityException e) {
			handleException(response, "jwt 서명이 올바르지 않습니다.", HttpServletResponse.SC_BAD_REQUEST);
		} catch (EntityNotFoundException e) {
			handleException(response, "access token이 만료되었거나 유효하지 않은 사용자 입니다.", HttpServletResponse.SC_BAD_REQUEST);
		}

		setAuthentication(accessToken);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("accessToken = " + accessToken);
		System.out.println("authentication = " + authentication.toString());

		filterChain.doFilter(request, response);

	}

	private void handleException(HttpServletResponse response, String message, int status) throws IOException {
		response.setStatus(status);
		response.setContentType("application/json");
		response.getWriter().write(message);
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
