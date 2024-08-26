package DM_plz.family_farm_main_server.qna.application;

import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import DM_plz.family_farm_main_server.family.dao.FamilyRepository;
import DM_plz.family_farm_main_server.family.domain.Family;
import DM_plz.family_farm_main_server.qna.dao.QuestionHistoryRepository;
import DM_plz.family_farm_main_server.qna.dao.QuestionRepository;
import DM_plz.family_farm_main_server.qna.domain.Question;
import DM_plz.family_farm_main_server.qna.domain.QuestionHistory;
import DM_plz.family_farm_main_server.qna.dto.CurrentQuestion;

@SpringBootTest
class QuestionServiceTest {

	@Autowired
	QuestionService questionService;

	@Autowired
	FamilyRepository familyRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	QuestionHistoryRepository questionHistoryRepository;

	@Test
	@DisplayName("질문이 완료되면, 설정한 시간에 새로운 질문이 나오는가")
	void newQuestionAtTime() {

		// Given
		Family newFamily = Family.builder()
			.build();
		Question firstQuestion = Question.builder()
			.questionContent("당신이 가장 좋아하는 것은?")
			.build();
		Question secondQuestion = Question.builder()
			.questionContent("사랑한다고 말해보세요!")
			.build();

		QuestionHistory newQuestionHistory = QuestionHistory.builder()
			.isComplete(true)
			.question(firstQuestion)
			.family(newFamily)
			.build();

		familyRepository.save(newFamily);
		questionRepository.save(firstQuestion);
		questionRepository.save(secondQuestion);
		questionHistoryRepository.save(newQuestionHistory);

		// When
		CurrentQuestion currentQuestion = questionService.getCurrentQuestion(newFamily.getId());

		// Then
		if (LocalTime.now().isAfter(LocalTime.of(10, 00, 00))) {
			Assertions.assertEquals("사랑한다고 말해보세요!", currentQuestion.getQuestion());
		} else {
			Assertions.assertEquals("당신이 가장 좋아하는 것은?", currentQuestion.getQuestion());
		}

	}

	@Test
	@DisplayName("질문이 완료되지 않으면, 새로운 질문이 나오지 않는가")
	void noNewQuestionIfNotCompleted() {

		// Given
		Family newFamily = Family.builder()
			.build();
		Question firstQuestion = Question.builder()
			.questionContent("당신이 가장 좋아하는 것은?")
			.build();
		Question secondQuestion = Question.builder()
			.questionContent("사랑한다고 말해보세요!")
			.build();

		QuestionHistory newQuestionHistory = QuestionHistory.builder()
			.isComplete(false)
			.question(firstQuestion)
			.family(newFamily)
			.build();

		familyRepository.save(newFamily);
		questionRepository.save(firstQuestion);
		questionRepository.save(secondQuestion);
		questionHistoryRepository.save(newQuestionHistory);

		// When
		CurrentQuestion currentQuestion = questionService.getCurrentQuestion(newFamily.getId());

		// Then
		Assertions.assertEquals("당신이 가장 좋아하는 것은?", currentQuestion.getQuestion());
	}

	@Test
	@DisplayName("같은 가족들끼리만 같은 질문을 공유해야 한다.")
	void shareSameQuestionWithSameFamily() {

		// Given
		Family firstFamily = Family.builder()
			.build();
		Family secondFamily = Family.builder()
			.build();

		Question firstQuestion = Question.builder()
			.questionContent("당신이 가장 좋아하는 것은?")
			.build();
		Question secondQuestion = Question.builder()
			.questionContent("사랑한다고 말해보세요!")
			.build();
		Question thirdQuestion = Question.builder()
			.questionContent("지금 먹고 싶은 것은?")
			.build();
		Question fourthQuestion = Question.builder()
			.questionContent("지금 듣고 있는 음악은?")
			.build();

		QuestionHistory newQuestionHistoryA = QuestionHistory.builder()
			.isComplete(false)
			.question(firstQuestion)
			.family(firstFamily)
			.build();
		QuestionHistory newQuestionHistoryB = QuestionHistory.builder()
			.isComplete(false)
			.question(thirdQuestion)
			.family(secondFamily)
			.build();

		familyRepository.save(firstFamily);
		familyRepository.save(secondFamily);
		questionRepository.save(firstQuestion);
		questionRepository.save(secondQuestion);
		questionRepository.save(thirdQuestion);
		questionRepository.save(fourthQuestion);
		questionHistoryRepository.save(newQuestionHistoryA);
		questionHistoryRepository.save(newQuestionHistoryB);

		// When
		CurrentQuestion currentQuestionWithFirstFamily = questionService.getCurrentQuestion(firstFamily.getId());
		CurrentQuestion currentQuestionWithSecondFamily = questionService.getCurrentQuestion(secondFamily.getId());

		// Then
		Assertions.assertEquals("당신이 가장 좋아하는 것은?", currentQuestionWithFirstFamily.getQuestion());
		Assertions.assertEquals("지금 먹고 싶은 것은?", currentQuestionWithSecondFamily.getQuestion());
	}
}