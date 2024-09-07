package DM_plz.family_farm_main_server.auth.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import DM_plz.family_farm_main_server.family.application.FamilyService;
import DM_plz.family_farm_main_server.family.dao.FamilyRepository;
import DM_plz.family_farm_main_server.family.domain.Family;

@SpringBootTest
class AuthServiceTest {

	@Autowired
	FamilyService familyService;

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
		Boolean isValidA = familyService.isValidFamilyCode("aaaaaaaa");
		Boolean isValidB = familyService.isValidFamilyCode("ddsdddsd");

		// Then
		Assertions.assertEquals(true, isValidA);
		Assertions.assertEquals(false, isValidB);
	}

}