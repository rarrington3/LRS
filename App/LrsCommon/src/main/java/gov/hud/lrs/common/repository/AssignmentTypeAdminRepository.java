package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.AssignmentTypeAdmin;

@Repository
public interface AssignmentTypeAdminRepository extends JpaRepository<AssignmentTypeAdmin, String> {

	AssignmentTypeAdmin findByAssignmentTypeRefId(String assignmentTypeRefId);

	AssignmentTypeAdmin findByAssignmentTypeCd(String assignmentTypeCd);
	
	@Query(
		value =
			"SELECT AT.* FROM ASSIGNMENT_TYPE_ADMIN AT " +
			"INNER JOIN PERSONNEL_ASSIGNMENT_TYPE PAT ON (PAT.ASSIGNMENT_TYPE_CD = AT.ASSIGNMENT_TYPE_CD) " +
			"WHERE (PAT.PERSONNEL_ID = ?1) ",
		nativeQuery = true
	)
	List<AssignmentTypeAdmin> findByPersonnelId(String personnelId);

}
