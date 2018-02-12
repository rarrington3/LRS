package gov.hud.lrs.common.repository;

import java.util.List;

public interface PersonnelAssignmentTypeRepositoryCustom {

	public class ReviewLocationIdCode {
		public String reviewLocationId;
		public String code;		// contains either ProductTypeRef.productTypeCd, ReviewTypeRef.reviewTypeCd, or SelectionReason.code
	}

	List<ReviewLocationIdCode> findReviewLocationReviewTypeCodes();
	List<ReviewLocationIdCode> findReviewLocationProductTypeCodes();
	List<ReviewLocationIdCode> findReviewLocationSelectionReasons();

	public class PersonnelIdCode {
		public String personnelId;
		public String code;		// contains either ProductTypeRef.productTypeCd, ReviewTypeRef.reviewTypeCd, or SelectionReason.code
	}

	List<PersonnelIdCode> findPersonnelReviewTypeCodesByPersonnelIdIn(List<String> personnelIds);
	List<PersonnelIdCode> findPersonnelReviewLevelTypeCodesByPersonnelIdIn(List<String> personnelIds);
	List<PersonnelIdCode> findPersonnelProductTypeCodesByPersonnelIdIn(List<String> personnelIds);
	List<PersonnelIdCode> findPersonnelSelectionReasonCodesByPersonnelIdIn(List<String> personnelIds);

}
