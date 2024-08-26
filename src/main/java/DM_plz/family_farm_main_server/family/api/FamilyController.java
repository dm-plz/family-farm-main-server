package DM_plz.family_farm_main_server.family.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DM_plz.family_farm_main_server.family.application.FamilyService;
import DM_plz.family_farm_main_server.family.dto.FamilyCode;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/family")
public class FamilyController {

	private final FamilyService familyService;

	@PostMapping("/code")
	public ResponseEntity<FamilyCode> issueFamilyCode(@RequestParam Long familyId) {
		FamilyCode familyCode = familyService.issueFamilyCode(familyId);

		return ResponseEntity.ok(familyCode);
	}

	@PatchMapping("/code")
	public ResponseEntity<FamilyCode> reissueFamilyCode(@RequestParam Long familyId) {
		FamilyCode familyCode = familyService.reissueFamilyCode(familyId);

		return ResponseEntity.ok(familyCode);
	}
}
