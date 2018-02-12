package gov.hud.lrs.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.JobParameter;

@Repository
public interface JobParameterRepository extends JpaRepository<JobParameter, String> {

}
