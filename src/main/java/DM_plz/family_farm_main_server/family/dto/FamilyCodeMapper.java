package DM_plz.family_farm_main_server.family.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import DM_plz.family_farm_main_server.family.domain.Family;

@Mapper(componentModel = "spring")
public interface FamilyCodeMapper {

	@Mapping(source = "inviteCode", target = "code")
	FamilyCode toFamilyCode(Family family);
}
