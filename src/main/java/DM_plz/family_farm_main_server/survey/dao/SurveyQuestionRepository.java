package DM_plz.family_farm_main_server.survey.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DM_plz.family_farm_main_server.member.domain.SurveyNumber;
import DM_plz.family_farm_main_server.survey.domain.SurveyQuestion;

public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, Long> {

	Optional<SurveyQuestion> findById(Long id);

	Optional<List<SurveyQuestion>> findAllBySurveyNumber(SurveyNumber surveyNumber);
}
