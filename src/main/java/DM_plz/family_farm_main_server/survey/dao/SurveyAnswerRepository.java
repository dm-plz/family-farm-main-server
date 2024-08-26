package DM_plz.family_farm_main_server.survey.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import DM_plz.family_farm_main_server.survey.domain.SurveyAnswer;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {

}
