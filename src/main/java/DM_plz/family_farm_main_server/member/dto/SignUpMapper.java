package DM_plz.family_farm_main_server.member.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import DM_plz.family_farm_main_server.member.domain.Member;
import DM_plz.family_farm_main_server.member.domain.MemberDetail;

@Mapper(componentModel = "spring")
public interface SignUpMapper {

	@Mapping(source = "OAuthProvider", target = "authProvider")
	Member toMember(SignUpDTO signUpDTO);

	MemberDetail toMemberDetail(SignUpDTO signUpDTO);

}
