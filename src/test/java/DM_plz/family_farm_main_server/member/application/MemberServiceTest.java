package DM_plz.family_farm_main_server.member.application;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import DM_plz.family_farm_main_server.common.exception.errorCode.CommonErrorCode;
import DM_plz.family_farm_main_server.common.exception.exception.CommonException;
import DM_plz.family_farm_main_server.member.dao.MemberDetailRepository;
import DM_plz.family_farm_main_server.member.dao.MemberRepository;
import DM_plz.family_farm_main_server.member.domain.AuthProvider;
import DM_plz.family_farm_main_server.member.domain.BirthType;
import DM_plz.family_farm_main_server.member.domain.GroupRole;
import DM_plz.family_farm_main_server.member.domain.Member;
import DM_plz.family_farm_main_server.member.domain.MemberDetail;
import DM_plz.family_farm_main_server.member.dto.SignUpDTO;
import DM_plz.family_farm_main_server.member.dto.SignUpMapper;

@SpringBootTest
class MemberServiceTest {

	@Autowired
	MemberService memberService;

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	MemberDetailRepository memberDetailRepository;

	@Autowired
	SignUpMapper signUpMapper;

	@Test
	@DisplayName("멤버 생성 테스트")
	void memberCreate() {

		// Given
		SignUpDTO signUpDTO = SignUpDTO.builder()
			.nickname("jiwon")
			.OAuthProvider(AuthProvider.KAKAO)
			.birth(LocalDateTime.now())
			.birthType(BirthType.SOLAR)
			.email("jrimit@gmail.com")
			.groupRole(GroupRole.SON)
			.familyCode(null)
			.build();

		// When
		Member newMember = memberService.signUp(signUpDTO);
		Member findMember = memberRepository.findByEmail(signUpDTO.getEmail())
			.orElseThrow(
				() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
			);

		MemberDetail findMemberDetail = memberDetailRepository.findByMember(findMember)
			.orElseThrow(
				() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
			);

		// Then
		Assertions.assertEquals(newMember.getEmail(), findMember.getEmail());
		Assertions.assertEquals("jiwon", findMember.getMemberDetail().getNickname());
	}

	/**
	 * Family 생성 로직이 완료되면, 테스트할 것.
	 */
	@Test()
	@DisplayName("가족이 있는 경우 맴버 생성 테스트")
	@Disabled("이 테스트는 임시로 비활성화되었습니다.")
	void familyMemberCreate() {

		// Given
		SignUpDTO signUpDTO = SignUpDTO.builder()
			.nickname("jiwon")
			.OAuthProvider(AuthProvider.KAKAO)
			.birth(LocalDateTime.now())
			.birthType(BirthType.SOLAR)
			.email("jrimit@gmail.com")
			.groupRole(GroupRole.SON)
			.familyCode("asdjnaoojjkd")
			.build();

		// When
		Member newMember = memberService.signUp(signUpDTO);
		Member findMember = memberRepository.findByEmail(signUpDTO.getEmail())
			.orElseThrow(
				() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
			);

		// Then
		Assertions.assertEquals(newMember.getEmail(), findMember.getEmail());
		Assertions.assertEquals("jiwon", findMember.getMemberDetail().getNickname());
		Assertions.assertEquals("familycode", findMember.getMemberDetail().getFamily().getInviteCode());
	}
}