package DM_plz.family_farm_main_server.survey.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DM_plz.family_farm_main_server.member.domain.MemberDetail;
import DM_plz.family_farm_main_server.survey.domain.SurveyAnswer;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {

	Optional<List<SurveyAnswer>> findByMemberDetailId(Long memberId);
}
