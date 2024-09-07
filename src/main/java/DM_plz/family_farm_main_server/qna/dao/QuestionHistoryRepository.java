package DM_plz.family_farm_main_server.qna.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import DM_plz.family_farm_main_server.qna.domain.QuestionHistory;

@Repository
public interface QuestionHistoryRepository extends JpaRepository<QuestionHistory, Long> {

	Optional<QuestionHistory> findTopByFamilyIdOrderByIdDesc(Long familyId);
}
