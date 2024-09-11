package DM_plz.family_farm_main_server.survey.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DM_plz.family_farm_main_server.common.exception.errorCode.CommonErrorCode;
import DM_plz.family_farm_main_server.common.exception.exception.CommonException;
import DM_plz.family_farm_main_server.member.dao.MemberDetailRepository;
import DM_plz.family_farm_main_server.member.domain.MemberDetail;
import DM_plz.family_farm_main_server.member.domain.SurveyNumber;
import DM_plz.family_farm_main_server.survey.dao.SurveyAnswerRepository;
import DM_plz.family_farm_main_server.survey.dao.SurveyQuestionRepository;
import DM_plz.family_farm_main_server.survey.domain.SurveyAnswer;
import DM_plz.family_farm_main_server.survey.domain.SurveyQuestion;
import DM_plz.family_farm_main_server.survey.dto.SurveyAnswerList;
import DM_plz.family_farm_main_server.survey.dto.SurveyMapper;
import DM_plz.family_farm_main_server.survey.dto.SurveyQuestionList;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {

	private final SurveyQuestionRepository surveyQuestionRepository;

	private final SurveyAnswerRepository surveyAnswerRepository;

	private final MemberDetailRepository memberDetailRepository;

	private final SurveyMapper surveyMapper;

	public SurveyQuestionList getSurvey(SurveyNumber questionNumber) {

		List<SurveyQuestionList.SurveyItem> surveyItems = surveyQuestionRepository.findAllBySurveyNumber(questionNumber)
			.orElseThrow(
				() ->
					new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
			).stream()
			.map(surveyMapper::toSurveyItem)
			.collect(Collectors.toList());

		return SurveyQuestionList.builder()
			.survey(surveyItems)
			.build();
	}

	public void submitSurvey(Long memberDetailId, SurveyAnswerList surveyList) {

		MemberDetail findMemberDetail = memberDetailRepository.findById(memberDetailId).orElseThrow(
			() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
		);

		surveyList.getSurvey().stream()
			.forEach(answer -> {

				SurveyQuestion findSurveyQuestion = surveyQuestionRepository.findById(answer.getId()).orElseThrow(
					() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
				);

				SurveyAnswer newSurveyAnswer = surveyMapper.toSurveyAnswer(answer);

				newSurveyAnswer.relationWithMemberDetail(findMemberDetail);
				newSurveyAnswer.relationWithSurveyQuestion(findSurveyQuestion);

				surveyAnswerRepository.save(newSurveyAnswer);
			});
	}

}
