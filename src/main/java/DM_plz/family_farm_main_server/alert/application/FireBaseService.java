package DM_plz.family_farm_main_server.alert.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import DM_plz.family_farm_main_server.alert.dto.ErrorDTO;
import DM_plz.family_farm_main_server.common.exception.errorCode.FCMErrorCode;
import DM_plz.family_farm_main_server.common.exception.exception.FCMException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FireBaseService {

	public void sendMulticastNotification(List<String> tokens, String title, String body) {

		MulticastMessage message = MulticastMessage.builder()
			.addAllTokens(tokens)
			.setNotification(Notification.builder()
				.setTitle(title)
				.setBody(body)
				.build())
			.build();

		try {
			BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
			log.info(response.toString());
		} catch (FirebaseMessagingException e) {
			log.error("firebase-admin 에러 코드와 메시지를 확인하세요. 에라코드 : %s, 에러메시지 : %s", e.getErrorCode().toString(), e.getMessage());
			throw new FCMException(FCMErrorCode.FIREBASE_MESSAGE_EXCEPTION, ErrorDTO.builder()
				.errorCode(e.getErrorCode().toString())
				.message(e.getMessage())
				.build());
		}
	}

	public void sendNotification(String token, String title, String body) {

		Message message = Message.builder()
			.setToken(token)
			.setNotification(Notification.builder()
				.setTitle(title)
				.setBody(body)
				.build())
			.build();

		try {
			String response = FirebaseMessaging.getInstance().send(message);
			log.info(response);
		} catch (FirebaseMessagingException e) {
			log.error("firebase-admin 에러 코드와 메시지를 확인하세요. 에라코드 : %s, 에러메시지 : %s", e.getErrorCode().toString(), e.getMessage());
			throw new FCMException(FCMErrorCode.FIREBASE_MESSAGE_EXCEPTION, ErrorDTO.builder()
				.errorCode(e.getErrorCode().toString())
				.message(e.getMessage())
				.build());
		}
	}
}
