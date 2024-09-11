package DM_plz.family_farm_main_server.common.config;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;

@Component
public class JwkConfig {

	// 7일간 최대 10개 캐시
	@Bean
	public JwkProvider kakaoJwkProvider() {
		return new JwkProviderBuilder("https://kauth.kakao.com")
			.cached(10, 7, TimeUnit.DAYS)
			.build();
	}

	@Bean
	public JwkProvider googleJwkProvider() throws URISyntaxException, MalformedURLException {
		URL url = new URI("https://www.googleapis.com/oauth2/v3/certs").toURL();
		return new JwkProviderBuilder(url)
			.cached(10, 7, TimeUnit.DAYS)
			.build();
	}

	@Bean
	public JwkProvider appleJwkProvider() throws URISyntaxException, MalformedURLException {
		URL url = new URI("https://appleid.apple.com/auth/keys").toURL();
		return new JwkProviderBuilder(url)
			.cached(10, 7, TimeUnit.DAYS)
			.build();
	}
}
