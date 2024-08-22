package DM_plz.family_farm_main_server.auth.dto;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class EmailAuthentication extends AbstractAuthenticationToken {

	private final String email;

	public EmailAuthentication(String email) {
		super(null);
		this.email = email;
		setAuthenticated(false);
	}

	public EmailAuthentication(String email, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.email = email;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null; // 비밀번호나 다른 자격증명을 사용하지 않으므로 null
	}

	@Override
	public Object getPrincipal() {
		return this.email;
	}

	public String getEmail() {
		return this.email;
	}
}
