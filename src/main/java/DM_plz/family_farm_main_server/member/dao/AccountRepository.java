package DM_plz.family_farm_main_server.member.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import DM_plz.family_farm_main_server.member.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByEmail(String email);
}
