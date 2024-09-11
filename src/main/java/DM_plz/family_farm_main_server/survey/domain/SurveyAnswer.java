package DM_plz.family_farm_main_server.survey.domain;

import java.time.LocalDateTime;

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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "survey_answer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SurveyAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "survey_answer_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_info_id")
	private MemberDetail memberDetail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "survey_question_id")
	private SurveyQuestion surveyQuestion;

	@Column(name = "survey_answer_content")
	private String surveyAnswerContent;

	@CreationTimestamp
	@Column(name = "create_at")
	private LocalDateTime createAt;

	public void relationWithSurveyQuestion(SurveyQuestion surveyQuestion) {
		this.surveyQuestion = surveyQuestion;
	}

	public void relationWithMemberDetail(MemberDetail memberDetail) {
		this.memberDetail = memberDetail;
	}
}
