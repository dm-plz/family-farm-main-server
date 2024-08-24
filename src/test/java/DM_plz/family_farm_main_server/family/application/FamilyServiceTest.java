package DM_plz.family_farm_main_server.family.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import DM_plz.family_farm_main_server.common.exception.exception.FamilyException;
import DM_plz.family_farm_main_server.family.dao.FamilyRepository;
import DM_plz.family_farm_main_server.family.domain.Family;
import DM_plz.family_farm_main_server.family.dto.FamilyCode;

@SpringBootTest
class FamilyServiceTest {

	@Autowired
	FamilyRepository familyRepository;

	@Autowired
	FamilyService familyService;

	@Test
	@DisplayName("가족코드 변경 여부 확인")
	void reissueFamilyCode() {

		// Given
		String firstCode = "ddddoank";
		Family newFamilyA = Family.builder()
			.inviteCode(firstCode)
			.build();
		Family newFamilyB = Family.builder()
			.inviteCode(firstCode)
			.build();

		familyRepository.save(newFamilyA);
		familyRepository.save(newFamilyB);

		// When
		FamilyCode familyCodeA = familyService.reissueFamilyCode(newFamilyA.getId());

		// Then
		Assertions.assertNotEquals(firstCode, familyCodeA.getCode());
		Assertions.assertEquals(firstCode, newFamilyB.getInviteCode());
	}

	@Test
	@DisplayName("가족코드 가져오기 확인")
	void getFamilyCode() {

		// Given
		String firstCode = "ddddoank";
		Family newFamilyA = Family.builder()
			.inviteCode(firstCode)
			.build();
		Family newFamilyB = Family.builder()
			.inviteCode(firstCode)
			.build();

		familyRepository.save(newFamilyA);
		familyRepository.save(newFamilyB);

		// When
		FamilyCode familyCodeA = familyService.getFamilyCode(newFamilyA.getId());
		FamilyCode familyCodeB = familyService.getFamilyCode(newFamilyB.getId());

		// Then
		Assertions.assertEquals(firstCode, newFamilyA.getInviteCode());
		Assertions.assertEquals(firstCode, newFamilyB.getInviteCode());
	}

	@Test
	@DisplayName("가족코드 보유 유저의 가족코드 첫 생성 거부 에러 확인")
	void forbiddenFirstIssueCode() {

		// Given
		String firstCode = "ddddoank";
		Family newFamilyA = Family.builder()
			.inviteCode(firstCode)
			.build();
		Family newFamilyB = Family.builder()
			.inviteCode(firstCode)
			.build();

		familyRepository.save(newFamilyA);
		familyRepository.save(newFamilyB);

		// When
		Assertions.assertThrows(FamilyException.class, () -> familyService.issueFamilyCode(newFamilyA.getId()));
	}

}