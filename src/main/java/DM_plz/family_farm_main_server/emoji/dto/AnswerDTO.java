package DM_plz.family_farm_main_server.emoji.dto;

import java.util.List;

import DM_plz.family_farm_main_server.qna.domain.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {

	@OneToMany(mappedBy = "answer")
	@Column(name = "comments")
	private List<Comment> comments;

	@Column(name = "emoji_text")
	private String emojiText;

	@Column(name = "answer_content")
	private String answerContent;

}
