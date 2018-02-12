package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.JobStatusRef;

@Repository
public interface JobStatusRefRepository extends JpaRepository<JobStatusRef, String> {

	@Cacheable("JobStatusRef")
	public JobStatusRef findByCode(String code);

}
