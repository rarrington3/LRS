package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.LoanTypeRef;

@Repository
public interface LoanTypeRefRepository extends JpaRepository<LoanTypeRef, String> {

	@Cacheable("LoanTypeRef")
	LoanTypeRef findByCode(String loanTypeCd);
	
	@Override
	@Cacheable("LoanTypeRef")
	List<LoanTypeRef> findAll();

}
