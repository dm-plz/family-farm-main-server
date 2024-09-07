package DM_plz.family_farm_main_server.alert.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DM_plz.family_farm_main_server.alert.domain.FCMCode;

public interface FCMCodeRepository extends JpaRepository<FCMCode, Long> {

	Optional<List<FCMCode>> findByMemberDetailIsCurrentQuestionCompleted(Boolean isCompleted);

	Optional<List<FCMCode>> findByMemberDetailId(Long memberDetailId);

}
