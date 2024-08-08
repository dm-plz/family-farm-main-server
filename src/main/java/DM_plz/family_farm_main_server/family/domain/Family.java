package DM_plz.family_farm_main_server.family.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import DM_plz.family_farm_main_server.member.domain.MemberInfo;
import DM_plz.family_farm_main_server.qna.domain.QuestionHistory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "family")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Family {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "family_id")
	private Long id;

	@OneToMany(mappedBy = "family")
	private List<MemberInfo> memberLists;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "invite_code_id")
	private InviteCode inviteCode;

	@OneToMany(mappedBy = "family")
	private List<QuestionHistory> questionHistories;

	@CreationTimestamp
	@Column(name = "create_at")
	private LocalDateTime createAt;

}
