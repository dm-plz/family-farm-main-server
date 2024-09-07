package DM_plz.family_farm_main_server.config;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

	@Value("${firebase.config.service-account-file}")
	private String serviceAccountKey;

	@Bean
	public FirebaseApp firebaseApp() throws IOException {
		FileInputStream serviceAccount = new FileInputStream(serviceAccountKey);

		FirebaseOptions options = FirebaseOptions.builder()
			.setCredentials(GoogleCredentials.fromStream(serviceAccount))
			.build();

		if (FirebaseApp.getApps().isEmpty()) {
			return FirebaseApp.initializeApp(options);
		} else {
			return FirebaseApp.getInstance();
		}
	}


}
