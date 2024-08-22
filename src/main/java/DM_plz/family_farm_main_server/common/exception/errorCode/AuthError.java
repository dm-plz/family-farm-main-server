package DM_plz.family_farm_main_server.common.exception.errorCode;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthError implements ErrorCode {

	UNKNOWN_USER(HttpStatus.FORBIDDEN, "AUTH-0001", "가입하지 않은 회원입니다. 회원가입을 진행해주세요."),
	BAD_AUTHORIZATION_CODE(HttpStatus.BAD_REQUEST, "AUTH-0002", "Authorization code가 유효하지 않습니다."),
	EMAIL_PARSING_ERROR(HttpStatus.FORBIDDEN, "AUTH-0003", "제공된 사용자 정보로부터 이메일을 추출하는데 실패했습니다."),
	INVALID_JWT_SIGNATURE(HttpStatus.NOT_FOUND, "AUTH-0004", "Resource not exists"),
	INVALID_PROVIDER(HttpStatus.BAD_REQUEST, "AUTH-005", "Provider가 올바르지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public int getHttpStatusToInt() {
		return httpStatus.value();
	}
}
