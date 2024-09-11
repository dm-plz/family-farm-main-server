package DM_plz.family_farm_main_server.common.exception.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import DM_plz.family_farm_main_server.common.exception.ErrorResponse;
import DM_plz.family_farm_main_server.common.exception.errorCode.CommonErrorCode;
import DM_plz.family_farm_main_server.common.exception.exception.CommonException;

class GlobalExceptionHandlerTest {

	@Test
	@DisplayName("예외 핸들러 기능 테스트")
	void globalExceptionHandlerTest() {

		// Given
		GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

		// When
		ResponseEntity<ErrorResponse> errorResponse = globalExceptionHandler.commonError(new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null));

		// Then
		Assertions.assertEquals("GLOBAL-0004", errorResponse.getBody().getCode());
		Assertions.assertEquals("Data Object has null pointer", errorResponse.getBody().getMessage());
		Assertions.assertEquals(null, errorResponse.getBody().getData());
	}
}