package DM_plz.family_farm_main_server.alert.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DM_plz.family_farm_main_server.alert.dao.FCMCodeRepository;
import DM_plz.family_farm_main_server.alert.domain.FCMCode;
import DM_plz.family_farm_main_server.common.exception.errorCode.CommonErrorCode;
import DM_plz.family_farm_main_server.common.exception.exception.CommonException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlertService {

	private final FireBaseService fireBaseService;

	private final FCMCodeRepository fcmCodeRepository;

	public void sendCheerUpNotification(Long memberId) {

		List<String> findFcmCodes = fcmCodeRepository.findByMemberDetailId(memberId).orElseThrow(
				() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
			).stream()
			.map(FCMCode::getCode)
			.toList();

		String title = "";
		String body = "";

		if (findFcmCodes.size() == 1) {

			fireBaseService.sendNotification(findFcmCodes.get(0), title, body);
		} else {

			fireBaseService.sendMulticastNotification(findFcmCodes, title, body);
		}

	}

	public List<String> getAllFcmCodeForNotification() {

		List<FCMCode> findFcmCodes = fcmCodeRepository.findByMemberDetailIsCurrentQuestionCompleted(true).orElseThrow(
			() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
		);

		return findFcmCodes.stream()
			.map(
				FCMCode::getCode
			).collect(Collectors.toList());
	}

}
