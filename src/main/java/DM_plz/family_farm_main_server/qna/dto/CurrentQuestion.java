package DM_plz.family_farm_main_server.qna.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentQuestion {

	private Long questionHistoryId;

	private String question;

	private Boolean isAnswered;
}
