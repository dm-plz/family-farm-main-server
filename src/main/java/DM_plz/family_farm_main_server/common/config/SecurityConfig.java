package DM_plz.family_farm_main_server.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import DM_plz.family_farm_main_server.auth.CustomOAuth2UserService;
import DM_plz.family_farm_main_server.auth.handler.AuthenticationSuccessHandler;
import DM_plz.family_farm_main_server.auth.handler.CustomAccessDeniedHandler;
import DM_plz.family_farm_main_server.auth.handler.CustomAuthenticationEntryPoint;
import DM_plz.family_farm_main_server.auth.handler.OAuth2FailureHandler;
import DM_plz.family_farm_main_server.auth.jwt.TokenAuthenticationFilter;
import DM_plz.family_farm_main_server.auth.jwt.TokenExceptionFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final TokenAuthenticationFilter tokenAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.headers(c -> c.frameOptions(
				HeadersConfigurer.FrameOptionsConfig::disable).disable()
			)
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)

			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(
					"/",
					"/error",
					"/favicon.ico",
					"/resources/**",
					"/h2-console/**",
					"/auth/success",
					"/test/**",
					"/login"
				)
				.permitAll()
				.anyRequest()
				.authenticated()
			)

			.oauth2Login(oauth ->
				oauth.userInfoEndpoint(c -> c.userService(customOAuth2UserService))
					.successHandler(authenticationSuccessHandler)
					.failureHandler(new OAuth2FailureHandler())
			)

			.addFilterBefore(tokenAuthenticationFilter,
				UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new TokenExceptionFilter(), tokenAuthenticationFilter.getClass())

			.exceptionHandling(exceptions -> exceptions
				.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
				.accessDeniedHandler(new CustomAccessDeniedHandler()));

		return http.build();
	}
}
