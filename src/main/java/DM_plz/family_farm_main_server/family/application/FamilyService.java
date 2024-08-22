package DM_plz.family_farm_main_server.family.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DM_plz.family_farm_main_server.family.dao.FamilyRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FamilyService {

	private final FamilyRepository familyRepository;

	public Boolean isValidFamilyCode(String inviteCode) {

		return familyRepository.findByInviteCode(inviteCode).isPresent();

	}
}
