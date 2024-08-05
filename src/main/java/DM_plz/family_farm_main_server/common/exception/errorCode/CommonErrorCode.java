package DM_plz.family_farm_main_server.common.exception.errorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode{

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "GLOBAL-0001", "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "GLOBAL-0002", "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GLOBAL-0003", "Internal server error"),
    NULL_POINTER_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "GLOBAL-0004", "Data Object has null pointer") // Null pointer 일 때 사용한다.
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public int getHttpStatusToInt() {
        return httpStatus.value();
    }
}
