package DM_plz.family_farm_main_server.common.exception.handler;

import DM_plz.family_farm_main_server.common.exception.ErrorResponse;
import DM_plz.family_farm_main_server.common.exception.errorCode.ErrorCode;
import DM_plz.family_farm_main_server.common.exception.exception.CommonException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> commonError(CommonException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode, errorCode.getMessage(), e.getData());
    }

    /**
     * Exception에서 data를 사용하지 않는 경우에 사용한다.
     */
    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();
    }

    /**
     * Exception에서 data를 사용하는 경우에 사용한다.
     */
    private ResponseEntity<ErrorResponse> handleExceptionInternal(ErrorCode errorCode, String message, Object data) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message, data));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode, String message, Object data) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .data(data)
                .build();
    }
}
