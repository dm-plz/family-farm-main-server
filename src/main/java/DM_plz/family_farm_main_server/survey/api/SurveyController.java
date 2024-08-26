package DM_plz.family_farm_main_server.survey.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DM_plz.family_farm_main_server.member.domain.SurveyNumber;
import DM_plz.family_farm_main_server.survey.application.SurveyService;
import DM_plz.family_farm_main_server.survey.dto.SurveyAnswerList;
import DM_plz.family_farm_main_server.survey.dto.SurveyQuestionList;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
public class SurveyController {

	private final SurveyService surveyService;

	@Value("${baseUrl}")
	private String baseUrl;

	@GetMapping("/")
	public ResponseEntity<SurveyQuestionList> getSurvey(SurveyNumber surveyNumber) {

		SurveyQuestionList survey = surveyService.getSurvey(surveyNumber);

		return ResponseEntity.ok(survey);
	}

	@PostMapping("/")
	public ResponseEntity<Void> submitSurvey(Long memberId, SurveyAnswerList surveyAnswerList) {

		surveyService.submitSurvey(memberId, surveyAnswerList);
		String url = String.format("%s/survey/answers/%d", baseUrl, memberId);
		URI location = URI.create(url);

		return ResponseEntity.created(location).build();
	}
}
