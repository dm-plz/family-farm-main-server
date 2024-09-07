package DM_plz.family_farm_main_server.alert.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorDTO {

	private String errorCode;

	private String message;
}
