package DM_plz.family_farm_main_server.survey.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SurveyAnswerList {

	private List<SurveyItem> survey;

	@Data
	public static class SurveyItem {

		private Long id;

		private String answer;
	}
}
