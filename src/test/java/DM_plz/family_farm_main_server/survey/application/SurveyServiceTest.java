package DM_plz.family_farm_main_server.survey.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
import DM_plz.family_farm_main_server.survey.dto.SurveyQuestionList;

@SpringBootTest
class SurveyServiceTest {

	@Autowired
	SurveyService surveyService;

	@Autowired
	MemberDetailRepository memberDetailRepository;

	@Autowired
	SurveyQuestionRepository surveyQuestionRepository;

	@Autowired
	SurveyAnswerRepository surveyAnswerRepository;

	@Test
	@DisplayName("설문 조사 노출 기능")
	void getSurvey() {

		// Given
		SurveyQuestion newSurveyQuestion = SurveyQuestion.builder()
			.surveyNumber(SurveyNumber.FIRST)
			.surveyContent("가족과의 관계가 완만한 편인가요?")
			.build();
		List<SurveyQuestionList.SurveyItem> expected = List.of(SurveyQuestionList.SurveyItem.builder()
			.id(1L)
			.content("가족과의 관계가 완만한 편인가요?")
			.build());

		surveyQuestionRepository.save(newSurveyQuestion);

		// When
		SurveyQuestionList findSurvey = surveyService.getSurvey(SurveyNumber.FIRST);

		// Then
		Assertions.assertIterableEquals(expected, findSurvey.getSurvey());
	}

	@Test
	@DisplayName("설문 조사 제출 기능")
	void submitSurvey() {

		// Given
		MemberDetail newMemberDetail = MemberDetail.builder()
			.build();

		SurveyQuestion newSurveyQuestion = SurveyQuestion.builder()
			.surveyNumber(SurveyNumber.FIRST)
			.surveyContent("가족과의 관계가 완만한 편인가요?")
			.build();

		memberDetailRepository.save(newMemberDetail);
		surveyQuestionRepository.save(newSurveyQuestion);

		SurveyAnswerList survey = SurveyAnswerList.builder()
			.survey(List.of(SurveyAnswerList.SurveyItem.builder()
				.id(1L)
				.answer("네 좋아요")
				.build()))
			.build();

		// When
		surveyService.submitSurvey(newMemberDetail.getId(), survey);

		List<SurveyAnswer> findAnswers = surveyAnswerRepository.findByMemberDetailId(newMemberDetail.getId()).orElseThrow(
			() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
		);

		// Then
		Assertions.assertEquals("네 좋아요", findAnswers.stream()
			.filter(answer -> answer.getId()== 1L)
			.collect(Collectors.toList())
			.get(0)
			.getSurveyAnswerContent()
		);
	}
}