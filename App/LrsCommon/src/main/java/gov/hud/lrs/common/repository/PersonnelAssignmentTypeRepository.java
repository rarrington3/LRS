package gov.hud.lrs.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.entity.PersonnelAssignmentType;

@Repository
public interface PersonnelAssignmentTypeRepository extends JpaRepository<PersonnelAssignmentType, String>, PersonnelAssignmentTypeRepositoryCustom {

	void deleteByPersonnel(Personnel personnel);

	void deleteByPersonnelAndAssignmentTypeAdminAssignmentTypeRefIdIn(Personnel personnel, List<String> assignmentTypeRefIds);

	PersonnelAssignmentType findByPersonnelAndAssignmentTypeAdminAssignmentTypeRefId(Personnel personnel, String assignmentTypeRefId);

	@Query(
		value =
			"SELECT COUNT(*) FROM PERSONNEL_ASSIGNMENT_TYPE PAT " +
			"INNER JOIN ASSIGNMENT_TYPE_ADMIN ATA ON (ATA.ASSIGNMENT_TYPE_CD = PAT.ASSIGNMENT_TYPE_CD) " +
			"INNER JOIN REVIEW_LEVEL_TYPE_REF RLTR ON (RLTR.REVIEW_LEVEL_TYPE_ID = ATA.ASSIGNMENT_TYPE_REF_ID) " +
			"WHERE " +
				"(PAT.PERSONNEL_ID = ?1) AND " +
				"(RLTR.REVIEW_LEVEL_CD = ?2) ",
		nativeQuery = true
	)
	int countByPersonnelIdAndReviewLevelType(String personnelId, String reviewLevelCode);
}
