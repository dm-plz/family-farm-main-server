package DM_plz.family_farm_main_server.auth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DM_plz.family_farm_main_server.auth.dto.ValidateFamilyCode;
import DM_plz.family_farm_main_server.family.application.FamilyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final FamilyService familyService;

	@GetMapping("/validate/family-code")
	public ResponseEntity<ValidateFamilyCode> validateFamilyCode(@RequestParam String familyCode) {

		Boolean isValid = familyService.isValidFamilyCode(familyCode);

		ValidateFamilyCode validateFamilyCode = ValidateFamilyCode.builder()
			.isValidate(isValid)
			.build();

		return ResponseEntity.ok(validateFamilyCode);
	}
}
