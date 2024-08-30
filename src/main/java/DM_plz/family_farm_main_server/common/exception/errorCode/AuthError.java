package DM_plz.family_farm_main_server.common.exception.errorCode;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthError implements ErrorCode {

	UNKNOWN_USER(HttpStatus.FORBIDDEN, "AUTH-0001", "가입하지 않은 회원입니다. 회원가입을 진행해주세요."),
	BAD_SIGN_UP(HttpStatus.BAD_REQUEST, "AUTH-0002", "회원가입 정보가 잘못되었습니다."),
	EMAIL_PARSING_ERROR(HttpStatus.FORBIDDEN, "AUTH-0003", "제공된 사용자 정보로부터 이메일을 추출하는데 실패했습니다."),
	INVALID_JWT_FORM(HttpStatus.NOT_FOUND, "AUTH-0004", "jwt 형식이 올바르지 않습니다."),
	INVALID_PROVIDER(HttpStatus.BAD_REQUEST, "AUTH-005", "Provider가 올바르지 않습니다."),
	GET_PUBLIC_KEY_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH-006", "public 키를 가져오는데 실패했습니다."),
	INVALID_PUBLIC_KEY(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH-007", "public 키가 유효하지 않습니다."),
	INVALID_AUTHORIZATION_FORMAT(HttpStatus.BAD_REQUEST, "AUTH-008", "authorization 포멧이 유효하지 않습니다."),
	AUTHORIZATION_NOT_EXIST(HttpStatus.BAD_REQUEST, "AUTH-009", "authorization 값이 존재하지 않습니다."),
	REFRESH_TOKEN_NOT_EXIST(HttpStatus.BAD_REQUEST, "AUTH-010", "refresh token이 존재하지 않습니다."),
	INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "AUTH-011", "refresh token이 만료되었습니다. 다시 로그인하세요."),
	TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "AUTH-012", "토큰이 만료되었습니다."),
	INVALID_JWT_SIGNATURE(HttpStatus.BAD_REQUEST, "AUTH-013", "jwt 서명이 올바르지 않습니다."),
	TOKEN_NOT_EXIST(HttpStatus.BAD_REQUEST, "AUTH-014", "요청한 토큰을 찾을 수 없습니다."),
	SUB_NOT_MATCH(HttpStatus.BAD_REQUEST, "AUTH-015", "access token과 refresh token의 sub가 일치하지 않습니다."),
	INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "AUTH-016", "access token이 유효하지 않습니다."),
	ACCESS_TOKEN_NOT_EXPIRED(HttpStatus.BAD_REQUEST, "AUTH-017", "access token이 만료되지 않았습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public int getHttpStatusToInt() {
		return httpStatus.value();
	}
}
