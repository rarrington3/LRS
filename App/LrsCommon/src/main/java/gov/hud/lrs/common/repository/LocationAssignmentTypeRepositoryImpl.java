package gov.hud.lrs.common.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class LocationAssignmentTypeRepositoryImpl implements LocationAssignmentTypeRepositoryCustom {
	
	@PersistenceContext(unitName = "lrs")
	private EntityManager entityManager;
	
	public List<ReviewLocationIdCode> convertResultListToreviewLocationIdCodeList(List<Object[]> resultList) {
		List<ReviewLocationIdCode> reviewLocationIdCodes = new ArrayList<ReviewLocationIdCode>();
		for (Object[] result : resultList) {
			ReviewLocationIdCode reviewLocationIdCode = new ReviewLocationIdCode();
			reviewLocationIdCode.reviewLocationId = (String)result[0];
			reviewLocationIdCode.code = result[1].toString();	// can be a character
			reviewLocationIdCodes.add(reviewLocationIdCode);
		}

		return reviewLocationIdCodes;
	}
	
	// Return All locations and associated Review Types
	@Override
	public List<ReviewLocationIdCode> getAllLocationsAndReviewTypes() {
		Query query = entityManager.createNativeQuery(
				"SELECT DISTINCT LAT.REVIEW_LOCATION_ID, R.REVIEW_TYPE_CD FROM  ASSIGNMENT_TYPE_ADMIN A "
						+ "INNER JOIN LOCATION_ASSIGNMENT_TYPE LAT ON LAT.ASSIGNMENT_TYPE_CD = A.ASSIGNMENT_TYPE_CD "
						+ "INNER JOIN REVIEW_LOCATION LOC ON LOC.REVIEW_LOCATION_ID = LAT.REVIEW_LOCATION_ID "
						+ "INNER JOIN REVIEW_TYPE_REF R ON A.ASSIGNMENT_TYPE_REF_ID = R.REVIEW_TYPE_ID"
				);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return convertResultListToreviewLocationIdCodeList(resultList);
	}
		
	// Return All locations and associated product Types
	@Override
	public List<ReviewLocationIdCode> getAllLocationsAndProductTypes() {
		Query query = entityManager.createNativeQuery(
				"SELECT DISTINCT LAT.REVIEW_LOCATION_ID, P.PRODUCT_TYPE_CD FROM  ASSIGNMENT_TYPE_ADMIN A "
						+ "INNER JOIN LOCATION_ASSIGNMENT_TYPE LAT ON LAT.ASSIGNMENT_TYPE_CD = A.ASSIGNMENT_TYPE_CD "
						+ "INNER JOIN REVIEW_LOCATION LOC ON LOC.REVIEW_LOCATION_ID = LAT.REVIEW_LOCATION_ID "
						+ "INNER JOIN PRODUCT_TYPE_REF P ON A.ASSIGNMENT_TYPE_REF_ID = P.PRODUCT_TYPE_ID"
				);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return convertResultListToreviewLocationIdCodeList(resultList);
	}

	// Return All locations and associated selection reason Types
	@Override
	public List<ReviewLocationIdCode> getAllLocationsAndSelectionReasonTypes() {
		Query query = entityManager.createNativeQuery(
				"SELECT DISTINCT LAT.REVIEW_LOCATION_ID,  S.CODE FROM ASSIGNMENT_TYPE_ADMIN A "
				+ "INNER JOIN LOCATION_ASSIGNMENT_TYPE LAT ON LAT.ASSIGNMENT_TYPE_CD = A.ASSIGNMENT_TYPE_CD "
				+ "INNER JOIN REVIEW_LOCATION LOC ON LOC.REVIEW_LOCATION_ID = LAT.REVIEW_LOCATION_ID "
				+ "INNER JOIN SELECTION_REASON S ON A.ASSIGNMENT_TYPE_REF_ID = S.SELECTION_REASON_ID"
				);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return convertResultListToreviewLocationIdCodeList(resultList);
	}
}
