package DM_plz.family_farm_main_server.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateFamilyCode {

	private Boolean isValidate;
}
