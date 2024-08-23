package DM_plz.family_farm_main_server.qna.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import DM_plz.family_farm_main_server.qna.domain.Question;
import DM_plz.family_farm_main_server.qna.domain.QuestionHistory;

@Mapper(componentModel = "spring")
public interface CurrentQuestionMapper {

	@Mapping(source = "questionHistory.isComplete", target = "isAnswered")
	@Mapping(source = "question.questionContent", target = "question")
	CurrentQuestion toCurrentQuestion(QuestionHistory questionHistory, Question question);

}
