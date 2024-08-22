package DM_plz.family_farm_main_server.qna.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private Long id;

	@OneToMany(mappedBy = "question")
	private List<QuestionHistory> questionHistories;

	@Column(name = "question_content")
	private String questionContent;

	@Column(name = "allowed_answer_type")
	private AllowedAnswerType allowedAnswerType;

	@CreationTimestamp
	@Column(name = "create_at")
	private LocalDateTime createAt;

}
