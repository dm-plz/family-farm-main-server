package DM_plz.family_farm_main_server.qna.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DM_plz.family_farm_main_server.qna.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	Optional<Question> findById(Long id);
}
