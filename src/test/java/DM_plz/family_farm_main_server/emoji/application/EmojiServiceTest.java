package DM_plz.family_farm_main_server.emoji.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import DM_plz.family_farm_main_server.emoji.domain.Emoji;
import DM_plz.family_farm_main_server.emoji.dto.EmojiDTO;

@SpringBootTest
class EmojiServiceTest {

	@Autowired
	private EmojiService emojiService;

	private final String testEmoji = "\uD83E\uDD70";

	@Test
	void emojiTest() {
		//Given
		EmojiDTO emojiDTO = new EmojiDTO(testEmoji);

		//When
		Emoji emoji = emojiService.saveEmoji(emojiDTO);

		//Then
		Assertions.assertThat(emoji.getEmoji()).isEqualTo(testEmoji);

	}
}