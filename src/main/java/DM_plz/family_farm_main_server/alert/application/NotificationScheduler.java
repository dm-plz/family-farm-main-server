package DM_plz.family_farm_main_server.alert.application;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

	private final FireBaseService fireBaseService;

	private final AlertService alertService;

	@Scheduled(cron = "0 0 19 * *", zone = "Asia/Seoul") // 오후 7시
	public void sendNotificationAtTen() {

		List<String> fcmCodeForNotification = alertService.getAllFcmCodeForNotification();
		String title = "";
		String body = "";

		fireBaseService.sendMulticastNotification(fcmCodeForNotification, title, body);
	}
}
