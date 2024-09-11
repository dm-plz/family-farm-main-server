package DM_plz.family_farm_main_server.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DM_plz.family_farm_main_server.common.exception.errorCode.CommonErrorCode;
import DM_plz.family_farm_main_server.common.exception.exception.CommonException;
import DM_plz.family_farm_main_server.family.dao.FamilyRepository;
import DM_plz.family_farm_main_server.family.domain.Family;
import DM_plz.family_farm_main_server.member.dao.MemberDetailRepository;
import DM_plz.family_farm_main_server.member.dao.MemberRepository;
import DM_plz.family_farm_main_server.member.domain.Member;
import DM_plz.family_farm_main_server.member.domain.MemberDetail;
import DM_plz.family_farm_main_server.member.dto.SignUpDTO;
import DM_plz.family_farm_main_server.member.dto.SignUpMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final MemberDetailRepository memberDetailRepository;
	private final FamilyRepository familyRepository;
	private final SignUpMapper signUpMapper;

	@Transactional
	public Member signUp(SignUpDTO signUpDTO) {

		Member newMember = signUpMapper.toMember(signUpDTO);
		MemberDetail newMemberDetail = signUpMapper.toMemberDetail(signUpDTO);

		newMemberDetail.relationMember(newMember);
		if (signUpDTO.getFamilyCode() != null) {
			Family family = familyRepository.findByInviteCode(signUpDTO.getFamilyCode())
				.orElseThrow(
					() -> new CommonException(CommonErrorCode.NULL_POINTER_EXCEPTION, null)
				);
			newMemberDetail.relationFamily(family);
		}

		memberRepository.save(newMember);
		memberDetailRepository.save(newMemberDetail);

		return newMember;
	}
}
