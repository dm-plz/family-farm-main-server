package DM_plz.family_farm_main_server.family.application;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DM_plz.family_farm_main_server.common.exception.errorCode.CommonErrorCode;
import DM_plz.family_farm_main_server.common.exception.errorCode.FamilyErrorCode;
import DM_plz.family_farm_main_server.common.exception.exception.CommonException;
import DM_plz.family_farm_main_server.common.exception.exception.FamilyException;
import DM_plz.family_farm_main_server.family.dao.FamilyRepository;
import DM_plz.family_farm_main_server.family.domain.Family;
import DM_plz.family_farm_main_server.family.dto.FamilyCode;
import DM_plz.family_farm_main_server.family.dto.FamilyCodeMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FamilyService {

	private final FamilyRepository familyRepository;

	private final FamilyCodeMapper familyCodeMapper;

	public Boolean isValidFamilyCode(String inviteCode) {

		return familyRepository.findByInviteCode(inviteCode).isPresent();

	}

	public FamilyCode getFamilyCode(Long familyId) {

		Family findFamily = findFamilyById(familyId);

		return familyCodeMapper.toFamilyCode(findFamily);
	}

	@Transactional
	public FamilyCode issueFamilyCode(Long familyId) {

		Family findFamily = findFamilyById(familyId);

		if (!findFamily.getInviteCode().isEmpty()) {
			throw new FamilyException(FamilyErrorCode.FORBIDDEN_ACCESS);
		}

		String code;

		while (true) {

			code = getNewCode(8);

			if (isUniqueCode(code)) {
				break;
			}
		}

		findFamily.issueFamilyCode(code);

		return familyCodeMapper.toFamilyCode(findFamily);
	}

	@Transactional
	public FamilyCode reissueFamilyCode(Long familyId) {

		Family findFamily = findFamilyById(familyId);

		String newCode;

		while (true) {

			newCode = getNewCode(8);

			if (isUniqueCode(newCode)) {
				break;
			}
		}

		findFamily.reissueFamilyCode(newCode);

		return familyCodeMapper.toFamilyCode(findFamily);
	}

	public Family findFamilyById(Long familyId) {
		return familyRepository.findById(familyId)
			.orElseThrow(
				() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
			);
	}

	public Boolean isUniqueCode(String code) {
		return familyRepository.findByInviteCode(code).isEmpty();
	}

	private String getNewCode(int length) {

		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}
}
