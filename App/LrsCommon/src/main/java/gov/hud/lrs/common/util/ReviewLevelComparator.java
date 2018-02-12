package gov.hud.lrs.common.util;

import java.util.Comparator;

import gov.hud.lrs.common.entity.ReviewLevel;

public class ReviewLevelComparator implements Comparator<ReviewLevel> {

	public int compare(ReviewLevel rl1, ReviewLevel rl2) {
		int ordinalCompare = rl1.getReviewLevelTypeRef().getOrdinal() - rl2.getReviewLevelTypeRef().getOrdinal();
		if (ordinalCompare != 0) {
			return ordinalCompare;
		}
		return rl1.getIterationNumber() - rl2.getIterationNumber();
	}

}
