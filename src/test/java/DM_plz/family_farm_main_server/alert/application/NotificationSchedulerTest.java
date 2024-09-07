package DM_plz.family_farm_main_server.alert.application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import DM_plz.family_farm_main_server.alert.dao.FCMCodeRepository;
import DM_plz.family_farm_main_server.member.dao.MemberDetailRepository;
import DM_plz.family_farm_main_server.member.dao.MemberRepository;

@SpringBootTest
class NotificationSchedulerTest {

	@Autowired
	NotificationScheduler notificationScheduler;

	@Autowired
	FireBaseService fireBaseService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberDetailRepository memberDetailRepository;

	@Autowired
	FCMCodeRepository fcmCodeRepository;

	@Test
	@DisplayName("전체 알림 기능 확인")
	@Disabled("프론트로부터 FCM code를 받아 진행합니다.")
	void sendToAll() {

	}

}