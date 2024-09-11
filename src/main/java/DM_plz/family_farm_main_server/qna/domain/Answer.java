package DM_plz.family_farm_main_server.qna.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import DM_plz.family_farm_main_server.member.domain.MemberDetail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_detail_id")
	private MemberDetail memberDetail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_history_id")
	private QuestionHistory questionHistory;

	@OneToMany(mappedBy = "answer")
	@Column(name = "comments")
	private List<Comment> comments;

	@Column(name = "emoji_text")
	private String emojiText;

	@Column(name = "answer_content")
	private String answerContent;

	@Column(name = "answer_type")
	private AnswerType answerType;

	@CreationTimestamp
	@Column(name = "create_at")
	private LocalDateTime createAt;
}
