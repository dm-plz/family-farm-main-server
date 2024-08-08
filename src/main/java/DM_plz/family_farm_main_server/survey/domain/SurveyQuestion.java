package DM_plz.family_farm_main_server.survey.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import DM_plz.family_farm_main_server.member.domain.SurveyNumber;
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
@Table(name = "survey_question")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SurveyQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "survey_question_id")
	private Long id;

	@OneToMany(mappedBy = "surveyQuestion")
	@Column(name = "survey_answer")
	private List<SurveyAnswer> surveyAnswer;

	@Column(name = "survey_number")
	private SurveyNumber surveyNumber;

	@Column(name = "survey_question_num")
	private Integer surveyQuestionNum;

	@Column(name = "survey_content")
	private String surveyContent;

	@CreationTimestamp
	@Column(name = "create_at")
	private LocalDateTime createAt;

}
