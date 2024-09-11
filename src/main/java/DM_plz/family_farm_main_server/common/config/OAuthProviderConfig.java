package DM_plz.family_farm_main_server.common.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthProviderConfig {

	public List<String> supportOAuthProvider() {
		return new ArrayList<>(Arrays.asList("kakao", "apple", "google"));
	}
}
