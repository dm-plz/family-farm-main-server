package DM_plz.family_farm_main_server.auth.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import DM_plz.family_farm_main_server.family.dao.FamilyRepository;
import DM_plz.family_farm_main_server.family.domain.Family;

@SpringBootTest
class AuthServiceTest {

	@Autowired
	AuthService authService;

	@Autowired
	FamilyRepository familyRepository;

	@Test
	@DisplayName("가족코드 유효 테스트")
	void familyCodeValid() {

		// Given
		Family newFamily = Family.builder()
			.inviteCode("aaaaaaaa")
			.build();

		familyRepository.save(newFamily);

		// When
		Boolean isValidA = authService.isValidFamilyCode("aaaaaaaa");
		Boolean isValidB = authService.isValidFamilyCode("ddsdddsd");

		// Then
		Assertions.assertEquals(true, isValidA);
		Assertions.assertEquals(false, isValidB);
	}

}