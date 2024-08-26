package DM_plz.family_farm_main_server.survey.application;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import DM_plz.family_farm_main_server.member.domain.SurveyNumber;
import DM_plz.family_farm_main_server.survey.dao.SurveyQuestionRepository;
import DM_plz.family_farm_main_server.survey.domain.SurveyQuestion;
import DM_plz.family_farm_main_server.survey.dto.SurveyQuestionList;

@SpringBootTest
class SurveyServiceTest {

	@Autowired
	SurveyService surveyService;

	@Autowired
	SurveyQuestionRepository surveyQuestionRepository;

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

		// When
		surveyQuestionRepository.save(newSurveyQuestion);
		SurveyQuestionList findSurvey = surveyService.getSurvey(SurveyNumber.FIRST);

		// Then
		Assertions.assertIterableEquals(expected, findSurvey.getSurvey());
	}

	@Test
	@DisplayName("설문 조사 제출 기능")
	void submitSurvey() {
		
	}

}