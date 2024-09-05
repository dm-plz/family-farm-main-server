package DM_plz.family_farm_main_server.common.exception.errorCode;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenError implements ErrorCode {

	// [EXPIRED]
	EXPIRED_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN-0005", "만료된 access token 입니다. token을 재발급 하세요."),
	EXPIRED_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN-0011", "만료된 refresh token 입니다. 다시 로그인하세요."),
	NOT_EXPIRED_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN-017", "access token이 만료되지 않았습니다."),

	// [INVALID]
	INVALID_JWT_SIGNATURE(HttpStatus.BAD_REQUEST, "TOKEN-013", "jwt 서명이 올바르지 않습니다."),
	INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "TOKEN-016", "access token이 유효하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public int getHttpStatusToInt() {
		return 0;
	}
}
