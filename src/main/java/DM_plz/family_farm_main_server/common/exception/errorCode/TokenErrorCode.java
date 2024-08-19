package DM_plz.family_farm_main_server.common.exception.errorCode;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {

	USER_NOT_EXISTS(HttpStatus.FORBIDDEN, "AUTH-0001", "User not exists"),
	INVALID_TOKEN(HttpStatus.FORBIDDEN, "AUTH-0002", "Invalid token"),
	INVALID_JWT_SIGNATURE(HttpStatus.NOT_FOUND, "AUTH-0003", "Resource not exists");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public int getHttpStatusToInt() {
		return httpStatus.value();
	}
}
