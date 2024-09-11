package DM_plz.family_farm_main_server.survey.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import DM_plz.family_farm_main_server.survey.domain.SurveyAnswer;
import DM_plz.family_farm_main_server.survey.domain.SurveyQuestion;

@Mapper(componentModel = "spring")
public interface SurveyMapper {

	@Mapping(source = "surveyContent", target = "content")
	SurveyQuestionList.SurveyItem toSurveyItem(SurveyQuestion surveyQuestion);

	@Mapping(target = "id", ignore = true)
	@Mapping(source = "answer", target = "surveyAnswerContent")
	SurveyAnswer toSurveyAnswer(SurveyAnswerList.SurveyItem surveyItem);

}
