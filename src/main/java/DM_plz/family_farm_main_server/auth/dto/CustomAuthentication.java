package DM_plz.family_farm_main_server.auth.dto;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthentication extends AbstractAuthenticationToken {

	private final String subject;
	private final String userId;
	private final String familyId;

	public CustomAuthentication(String subject, String userId, String familyId) {
		super(null);
		this.subject = subject;
		this.userId = userId;
		this.familyId = familyId;
		setAuthenticated(true);
	}

	public CustomAuthentication(String email, Collection<? extends GrantedAuthority> authorities, String userId,
		String familyId) {
		super(authorities);
		this.subject = email;
		this.userId = userId;
		this.familyId = familyId;
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

	public String getUserId() {
		return userId;
	}

	public String getFamilyId() {
		return familyId;
	}
}
