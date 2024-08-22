package DM_plz.family_farm_main_server.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DM_plz.family_farm_main_server.member.domain.Member;
import DM_plz.family_farm_main_server.member.domain.MemberDetail;

public interface MemberDetailRepository extends JpaRepository<MemberDetail, Long> {

	Optional<MemberDetail> findByMember(Member member);
}
