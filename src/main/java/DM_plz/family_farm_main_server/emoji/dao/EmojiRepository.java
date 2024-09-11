package DM_plz.family_farm_main_server.emoji.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import DM_plz.family_farm_main_server.emoji.domain.Emoji;

public interface EmojiRepository extends JpaRepository<Emoji, String> {
}
