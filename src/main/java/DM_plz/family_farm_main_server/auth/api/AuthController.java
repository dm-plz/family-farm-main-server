package DM_plz.family_farm_main_server.auth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DM_plz.family_farm_main_server.auth.application.IdTokenService;
import DM_plz.family_farm_main_server.auth.dto.AccessTokenDTO;
import DM_plz.family_farm_main_server.auth.dto.CustomAuthentication;
import DM_plz.family_farm_main_server.auth.dto.JwtSetDTO;
import DM_plz.family_farm_main_server.auth.dto.OidcSignIn;
import DM_plz.family_farm_main_server.auth.dto.SubDTO;
import DM_plz.family_farm_main_server.auth.dto.ValidateFamilyCode;
import DM_plz.family_farm_main_server.auth.token.application.TokenProvider;
import DM_plz.family_farm_main_server.common.exception.errorCode.AuthError;
import DM_plz.family_farm_main_server.common.exception.exception.AuthException;
import DM_plz.family_farm_main_server.family.application.FamilyService;
import DM_plz.family_farm_main_server.member.application.MemberService;
import DM_plz.family_farm_main_server.member.domain.Member;
import DM_plz.family_farm_main_server.member.dto.SignUpDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final FamilyService familyService;
	private final MemberService memberService;
	private final IdTokenService idTokenService;
	private final TokenProvider tokenProvider;

	@PostMapping("/oauth/sign-in/oidc")
	public ResponseEntity<JwtSet> signIn(@RequestBody OidcSignIn oidcSignIn) {
		String customSub = idTokenService.getCustomSub(oidcSignIn);
		Member findMember = memberService.findMember(customSub);
		CustomAuthentication authentication = new CustomAuthentication(customSub, findMember);
		JwtSet jwtSet = tokenProvider.generateSuccessToken(authentication);
		return ResponseEntity.ok(jwtSet);
	}

	@PostMapping("/oauth/sign-up/oidc")
	public ResponseEntity<JwtSet> signUpWithOidc(@Valid @RequestBody SignUpDTO signUpDTO) {
		Member signUpMember = memberService.signUp(signUpDTO);
		CustomAuthentication authentication = new CustomAuthentication(signUpMember.getSub(), signUpMember);
		JwtSet jwtSet = tokenProvider.generateSuccessToken(authentication);
		return ResponseEntity.ok(jwtSet);
	}

	@PatchMapping("/token/reissuance")
	public ResponseEntity<AccessTokenDTO> reissueToken(@RequestBody JwtSetDTO jwtSetDTO) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO(
			tokenProvider.reissueAccessToken(jwtSetDTO.getAccessToken(), jwtSetDTO.getRefreshToken()));
		return ResponseEntity.ok(accessTokenDTO);
	}

	@GetMapping("/validate/family-code")
	public ResponseEntity<ValidateFamilyCode> validateFamilyCode(@RequestParam String familyCode) {

		Boolean isValid = familyService.isValidFamilyCode(familyCode);

		ValidateFamilyCode validateFamilyCode = ValidateFamilyCode.builder()
			.isValidate(isValid)
			.build();

		return ResponseEntity.ok(validateFamilyCode);
	}
}

