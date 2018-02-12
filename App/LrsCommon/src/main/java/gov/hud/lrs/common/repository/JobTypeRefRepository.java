package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.JobTypeRef;

@Repository
public interface JobTypeRefRepository extends JpaRepository<JobTypeRef, String> {

	@Cacheable("JobTypeRef")
	public JobTypeRef findByCode(String code);

}
