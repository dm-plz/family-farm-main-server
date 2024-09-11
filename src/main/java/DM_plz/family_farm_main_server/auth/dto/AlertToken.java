package DM_plz.family_farm_main_server.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertToken {
	private String deviceId;
	private String type;
	private String fcmToken;
}
