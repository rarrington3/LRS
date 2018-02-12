package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {

	List<Email> findByEmailStatusRefCode(String emailStatusCode);
	
}
