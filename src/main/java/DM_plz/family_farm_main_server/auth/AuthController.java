package DM_plz.family_farm_main_server.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import DM_plz.family_farm_main_server.auth.dto.LoginResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {

	private final TokenService tokenService;

	@GetMapping("/auth/success")
	public ResponseEntity<LoginResponse> loginSuccess(LoginResponse loginResponse) {
		return ResponseEntity.ok(loginResponse);
	}
}
