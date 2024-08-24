package DM_plz.family_farm_main_server.qna.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DM_plz.family_farm_main_server.qna.application.QuestionService;
import DM_plz.family_farm_main_server.qna.dto.CurrentQuestion;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QnaController {

	private final QuestionService questionService;

	@GetMapping("/")
	public ResponseEntity<CurrentQuestion> getCurrentQuestion(@RequestParam Long familyId) {

		CurrentQuestion currentQuestion = questionService.getCurrentQuestion(familyId);

		return ResponseEntity.ok(currentQuestion);
	}
}
