package gov.hud.lrs.common.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.JobExecutionStatusRef;

@Repository
public interface JobExecutionStatusRefRepository extends JpaRepository<JobExecutionStatusRef, String> {

	@Cacheable("JobExecutionStatusRef")
	public JobExecutionStatusRef findByCode(String code);

}
