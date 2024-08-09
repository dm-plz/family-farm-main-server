package DM_plz.family_farm_main_server.member.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import DM_plz.family_farm_main_server.member.domain.MemberDetail;

public interface MemberInfoRepository extends JpaRepository<MemberDetail, Long> {
}
