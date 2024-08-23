package DM_plz.family_farm_main_server.qna.application;

import java.time.LocalTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DM_plz.family_farm_main_server.common.exception.errorCode.CommonErrorCode;
import DM_plz.family_farm_main_server.common.exception.exception.CommonException;
import DM_plz.family_farm_main_server.qna.dao.QuestionHistoryRepository;
import DM_plz.family_farm_main_server.qna.dao.QuestionRepository;
import DM_plz.family_farm_main_server.qna.domain.Question;
import DM_plz.family_farm_main_server.qna.domain.QuestionHistory;
import DM_plz.family_farm_main_server.qna.dto.CurrentQuestion;
import DM_plz.family_farm_main_server.qna.dto.CurrentQuestionMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

	private final QuestionHistoryRepository questionHistoryRepository;

	private final QuestionRepository questionRepository;

	private final CurrentQuestionMapper currentQuestionMapper;

	private final static LocalTime localTime = LocalTime.of(10, 00, 00);

	public CurrentQuestion getCurrentQuestion(Long familyId) {

		QuestionHistory findQuestionHistory = questionHistoryRepository.findTopByFamilyIdOrderByIdDesc(familyId)
			.orElseThrow(
				() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
			);

		Question findQuestion = findQuestionHistory.getQuestion();

		// 새로운 질문이 생성하여 반환해야 하는 경우
		if (findQuestionHistory.getIsComplete() && LocalTime.now().isAfter(localTime)) {
			QuestionHistory nextQuestionHistory = getNextQuestion(findQuestionHistory);

			return currentQuestionMapper.toCurrentQuestion(nextQuestionHistory, nextQuestionHistory.getQuestion());

			// 기존의 질문을 반환해야 하는 경우
		} else {

			return currentQuestionMapper.toCurrentQuestion(findQuestionHistory, findQuestion);
		}
	}

	@Transactional
	public QuestionHistory getNextQuestion(QuestionHistory questionHistory) {

		Question findQuestion = questionRepository.findById(questionHistory.getQuestion().getId() + 1)
			.orElseThrow(
				() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
			);

		QuestionHistory newQuestionHistory = QuestionHistory.builder()
			.question(findQuestion)
			.family(questionHistory.getFamily())
			.isComplete(false)
			.build();

		questionHistoryRepository.save(newQuestionHistory);

		return newQuestionHistory;
	}
}
