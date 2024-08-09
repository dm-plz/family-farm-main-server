package DM_plz.family_farm_main_server.member.domain;

import java.time.LocalDateTime;
import java.util.List;

import DM_plz.family_farm_main_server.family.domain.Family;
import DM_plz.family_farm_main_server.qna.domain.Answer;
import DM_plz.family_farm_main_server.qna.domain.Comment;
import DM_plz.family_farm_main_server.survey.domain.SurveyAnswer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_detail_id ")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "family_id")
	private Family family;

	@OneToMany(mappedBy = "memberDetail")
	@Column(name = "answers")
	private List<Answer> answers;

	@OneToMany(mappedBy = "memberDetail")
	@Column(name = "survey_answers")
	private List<SurveyAnswer> surveyAnswers;

	@OneToMany(mappedBy = "memberDetail")
	@Column(name = "comments")
	private List<Comment> comments;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "birth")
	private LocalDateTime birth;

	@Column(name = "birth_type")
	private BirthType birthType;

	@Column(name = "group_role")
	private GroupRole groupRole;

	@Column(name = "completed_survey_number")
	private SurveyNumber completedSurveyNumber;

}
