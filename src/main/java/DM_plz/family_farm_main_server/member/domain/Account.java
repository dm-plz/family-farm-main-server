package DM_plz.family_farm_main_server.member.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long id;

	@OneToOne(mappedBy = "account")
	private MemberDetail memberDetail;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "auth_provider")
	private AuthProvider authProvider;

	@CreationTimestamp
	@Column(name = "create_at")
	private LocalDateTime createAt;

}
