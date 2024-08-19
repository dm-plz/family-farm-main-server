package DM_plz.family_farm_main_server.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DM_plz.family_farm_main_server.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);
}
