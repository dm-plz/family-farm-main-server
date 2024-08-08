package DM_plz.family_farm_main_server.qna.domain;

import java.util.Arrays;
import java.util.List;

public enum AllowedAnswerType {

	TYPE1(Arrays.asList(AnswerType.TEXT, AnswerType.VOICE)),
	TYPE2(Arrays.asList(AnswerType.IMAGE));

	private final List<AnswerType> answerTypes;

	AllowedAnswerType(List<AnswerType> answerTypes) {
		this.answerTypes = answerTypes;
	}

	public List<AnswerType> getAnswerTypes() {
		return answerTypes;
	}
}
