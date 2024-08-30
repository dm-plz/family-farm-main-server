package DM_plz.family_farm_main_server.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import DM_plz.family_farm_main_server.auth.token.application.TokenProvider;
import DM_plz.family_farm_main_server.auth.token.filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final TokenProvider tokenProvider;

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
					"/auth/**"
				)
				.permitAll()
				.anyRequest()
				.authenticated()
			)

			.addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

		// .addFilterBefore(tokenAuthenticationFilter,
		// 	UsernamePasswordAuthenticationFilter.class)
		// .addFilterBefore(new TokenExceptionFilter(), tokenAuthenticationFilter.getClass())
		//
		// .exceptionHandling(exceptions -> exceptions
		// 	.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
		// 	.accessDeniedHandler(new CustomAccessDeniedHandler()));

		return http.build();
	}
}
