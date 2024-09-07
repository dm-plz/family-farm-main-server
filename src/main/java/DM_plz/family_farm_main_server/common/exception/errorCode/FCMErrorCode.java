package DM_plz.family_farm_main_server.common.exception.errorCode;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FCMErrorCode implements ErrorCode {

	FIREBASE_MESSAGE_EXCEPTION(HttpStatus.FORBIDDEN, "FCM-0001", "Firebase 서버에서 해당 요청을 처리하지 않았습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public int getHttpStatusToInt() {
		return httpStatus.value();
	}

}
