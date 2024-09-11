package DM_plz.family_farm_main_server.auth.application;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.auth0.jwk.JwkProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwkService {

	private final Map<String, JwkProvider> jwkProviders;

	public JwkProvider getJwkProvider(String provider) {
		return jwkProviders.get(provider + "JwkProvider");
	}
}
