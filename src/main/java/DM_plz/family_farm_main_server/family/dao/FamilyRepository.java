package DM_plz.family_farm_main_server.family.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DM_plz.family_farm_main_server.family.domain.Family;

public interface FamilyRepository extends JpaRepository<Family, Long> {

	Optional<Family> findById(Long id);

	Optional<Family> findByInviteCode(String inviteCode);

}
