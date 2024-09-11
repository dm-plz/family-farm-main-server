package DM_plz.family_farm_main_server.emoji.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "emoji")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Emoji {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emoji_id")
	private Long id;

	@Column(name = "emoji")
	private String emoji;
}
