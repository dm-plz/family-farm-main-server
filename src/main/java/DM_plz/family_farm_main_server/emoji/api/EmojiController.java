package DM_plz.family_farm_main_server.emoji.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DM_plz.family_farm_main_server.emoji.application.EmojiService;
import DM_plz.family_farm_main_server.emoji.domain.Emoji;
import DM_plz.family_farm_main_server.emoji.dto.EmojiDTO;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emoji")
public class EmojiController {

	private final EmojiService emojiService;

	@GetMapping("/")
	public ResponseEntity<List<Emoji>> MultiEmoji() {
		List<Emoji> emojiAll = emojiService.findEmojiAll();
		return ResponseEntity.ok(emojiAll);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Emoji> SingleEmoji(@PathVariable("id") String id) {
		return ResponseEntity.ok(emojiService.findEmoji(id));
	}

	@PostMapping("/submit")
	public ResponseEntity<Emoji> submitEmoji(@RequestBody EmojiDTO emojiDTO) {
		return ResponseEntity.ok(emojiService.saveEmoji(emojiDTO));
	}

}
