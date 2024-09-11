package DM_plz.family_farm_main_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(
					"/",
					"/h2-console/**",
					"/emoji/**"
				)
				.permitAll()
				.anyRequest()
				.authenticated()
			)
			.headers(headersConfigurer -> headersConfigurer
				.frameOptions(
					HeadersConfigurer.FrameOptionsConfig::disable
				))
			.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}
}
