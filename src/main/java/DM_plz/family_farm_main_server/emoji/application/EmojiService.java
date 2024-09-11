package DM_plz.family_farm_main_server.emoji.application;

import java.util.List;

import org.springframework.stereotype.Service;

import DM_plz.family_farm_main_server.common.exception.errorCode.CommonErrorCode;
import DM_plz.family_farm_main_server.common.exception.exception.CommonException;
import DM_plz.family_farm_main_server.emoji.dao.EmojiRepository;
import DM_plz.family_farm_main_server.emoji.domain.Emoji;
import DM_plz.family_farm_main_server.emoji.dto.EmojiDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmojiService {

	private final EmojiRepository emojiRepository;

	public Emoji findEmoji(String id) {
		return emojiRepository.findById(id)
			.orElseThrow(() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null));
	}

	public List<Emoji> findEmojiAll() {
		return emojiRepository.findAll();
	}

	public Emoji saveEmoji(EmojiDTO emojiDTO) {
		Emoji emoji = Emoji.builder()
			.emoji(emojiDTO.getEmoji())
			.build();
		return emojiRepository.save(emoji);
	}
}
