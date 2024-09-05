package DM_plz.family_farm_main_server.common.exception.errorCode;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OidcErrorCode implements ErrorCode {

	// [INVALID]
	INVALID_PROVIDER(HttpStatus.BAD_REQUEST, "OIDC-0001", "지원하지 않는 provider 입니다."),
	INVALID_PUBLIC_KEY(HttpStatus.INTERNAL_SERVER_ERROR, "OIDC-0003", "public 키가 유효하지 않습니다."),

	// [FAIL]
	FAIL_TO_GET_JWK(HttpStatus.INTERNAL_SERVER_ERROR, "OIDC-0002", "jwk를 가져오는데 실패했습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public int getHttpStatusToInt() {
		return httpStatus.value();
	}
}
