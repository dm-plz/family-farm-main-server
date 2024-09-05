package DM_plz.family_farm_main_server.auth.dto;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import DM_plz.family_farm_main_server.member.domain.Member;

public class CustomAuthentication extends AbstractAuthenticationToken {

	private final String subject;
	private final Long userId;
	private final Long familyId;

	public CustomAuthentication(String subject, Long userId, Long familyId) {
		super(null);
		this.subject = subject;
		this.userId = userId;
		this.familyId = familyId;
		setAuthenticated(true);
	}

	public CustomAuthentication(String subject, Member member) {
		super(null);
		this.subject = subject;
		this.userId = member.getId();
		this.familyId = member.getMemberDetail().getFamily().getId();
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null; // 비밀번호나 다른 자격증명을 사용하지 않으므로 null
	}

	@Override
	public Object getPrincipal() {
		return this.subject;
	}

	public String getSubject() {
		return this.subject;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getFamilyId() {
		return familyId;
	}
}
