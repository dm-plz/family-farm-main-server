package DM_plz.family_farm_main_server.common.exception.errorCode;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FamilyErrorCode implements ErrorCode {

	FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "FAMILY-0001", "이미 가족코드를 가지고 있는 회원입니다. 재발급 경로로 진입해주세요.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public int getHttpStatusToInt() {
		return httpStatus.value();
	}
}
