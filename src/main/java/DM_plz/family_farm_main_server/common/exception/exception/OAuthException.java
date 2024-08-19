package DM_plz.family_farm_main_server.common.exception.exception;

import DM_plz.family_farm_main_server.common.exception.errorCode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OAuthException extends RuntimeException {
	private final ErrorCode errorCode;
	private final Object data;
}
