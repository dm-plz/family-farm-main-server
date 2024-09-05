package DM_plz.family_farm_main_server.common.exception.errorCode;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthError implements ErrorCode {

	// [INVALID]
	INVALID_USER(HttpStatus.FORBIDDEN, "AUTH-0001", "가입하지 않은 회원입니다. 회원가입을 진행해주세요."),
	INVALID_SIGN_UP(HttpStatus.BAD_REQUEST, "AUTH-0002", "회원가입 정보가 잘못되었습니다."),
	INVALID_AUTHORIZATION_FORMAT(HttpStatus.BAD_REQUEST, "AUTH-0003", "authorization 포멧이 유효하지 않습니다."),

	// [NOT_EXIST]
	NOT_EXIST_AUTHORIZATION(HttpStatus.BAD_REQUEST, "AUTH-0004", "authorization 값이 존재하지 않습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public int getHttpStatusToInt() {
		return httpStatus.value();
	}
}
