package gov.hud.lrs.common.repository;

import java.util.List;

public interface ReviewRepositoryCustom {

	public class PersonnelIdReviewCount {
		public String personnelId;
		public int reviewCount;
	}

	List<PersonnelIdReviewCount> findPersonnelIdReviewCounts();
	List<PersonnelIdReviewCount> findPersonnelIdReviewCountsByReviewLocationId(String reviewLocationId);
	// this handles arbitrarily large lists; will batch as needed
	List<PersonnelIdReviewCount> findPersonnelIdReviewCountsByPersonnelIdIn(List<String> personnelId);
}
