package DM_plz.family_farm_main_server.auth;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DM_plz.family_farm_main_server.auth.domain.OAuth2UserInfo;
import DM_plz.family_farm_main_server.auth.domain.PrincipalDetails;
import DM_plz.family_farm_main_server.member.dao.AccountRepository;
import DM_plz.family_farm_main_server.member.domain.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final AccountRepository accountRepository;

	@Transactional
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// 1. 유저 정보(attributes) 가져오기
		Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

		// 2. resistrationId 가져오기 (third-party id)
		String registrationId = userRequest.getClientRegistration().getRegistrationId();

		// 3. userNameAttributeName 가져오기
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
			.getUserInfoEndpoint().getUserNameAttributeName();

		// 4. 유저 정보 dto 생성
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2UserAttributes);

		// 5. 회원가입 및 로그인
		Account account = getOrSave(oAuth2UserInfo);

		// 6. OAuth2User로 반환
		return new PrincipalDetails(account, oAuth2UserAttributes, userNameAttributeName);
	}

	private Account getOrSave(OAuth2UserInfo oAuth2UserInfo) {
		Account account = accountRepository.findByEmail(oAuth2UserInfo.getEmail())
			.orElseGet(oAuth2UserInfo::toEntity);
		return accountRepository.save(account);
	}
}
