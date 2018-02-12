package gov.hud.lrs.common.repository;

import java.util.List;

public interface LocationAssignmentTypeRepositoryCustom {

	public class ReviewLocationIdCode {
		public String reviewLocationId;
		public String code;
	}

	// Return All locations and associated Review Types
	List<ReviewLocationIdCode> getAllLocationsAndReviewTypes();

	// Return All locations and associated product Types
	List<ReviewLocationIdCode> getAllLocationsAndProductTypes();

	// Return All locations and associated selection reason Types
	List<ReviewLocationIdCode> getAllLocationsAndSelectionReasonTypes();

}
