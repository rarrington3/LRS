package gov.hud.lrs.common.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class PersonnelAssignmentTypeRepositoryImpl implements PersonnelAssignmentTypeRepositoryCustom {

	@PersistenceContext(unitName = "lrs")
	private EntityManager entityManager;

	public List<ReviewLocationIdCode> convertResultListToReviewLocationIdCodeList(List<Object[]> resultList) {
		List<ReviewLocationIdCode> reviewLocationIdCodes = new ArrayList<ReviewLocationIdCode>();
		for (Object[] result : resultList) {
			ReviewLocationIdCode reviewLocationIdCode = new ReviewLocationIdCode();
			reviewLocationIdCode.reviewLocationId = (String)result[0];	// should always be a string so cast is correct
			reviewLocationIdCode.code = result[1].toString();	// can be a character so toString() is needed
			reviewLocationIdCodes.add(reviewLocationIdCode);
		}

		return reviewLocationIdCodes;
	}

	@Override
	public List<ReviewLocationIdCode> findReviewLocationReviewTypeCodes() {
		Query query = entityManager.createNativeQuery(
			"SELECT DISTINCT P.REVIEW_LOCATION_ID, RT.REVIEW_TYPE_CD AS TYPE " +
			"FROM ASSIGNMENT_TYPE_ADMIN A " +
			"INNER JOIN PERSONNEL_ASSIGNMENT_TYPE PA ON (PA.ASSIGNMENT_TYPE_CD = A.ASSIGNMENT_TYPE_CD) " +
			"INNER JOIN PERSONNEL P ON (P.PERSONNEL_ID = PA.PERSONNEL_ID) " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON (PS.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID) " +
			"INNER JOIN REVIEW_TYPE_REF RT ON (RT.REVIEW_TYPE_ID = A.ASSIGNMENT_TYPE_REF_ID) " +
			"WHERE (PS.CODE = 'A') AND (PA.ACTIVE_IND = 'Y') "
		);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return convertResultListToReviewLocationIdCodeList(resultList);
	}
	@Override
	public List<ReviewLocationIdCode> findReviewLocationProductTypeCodes() {
		Query query = entityManager.createNativeQuery(
			"SELECT DISTINCT P.REVIEW_LOCATION_ID, PT.PRODUCT_TYPE_CD FROM ASSIGNMENT_TYPE_ADMIN A " +
			"INNER JOIN PERSONNEL_ASSIGNMENT_TYPE PA ON (PA.ASSIGNMENT_TYPE_CD = A.ASSIGNMENT_TYPE_CD) " +
			"INNER JOIN PERSONNEL P ON (P.PERSONNEL_ID = PA.PERSONNEL_ID) " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON (PS.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID) " +
			"INNER JOIN PRODUCT_TYPE_REF PT ON (PT.PRODUCT_TYPE_ID = A.ASSIGNMENT_TYPE_REF_ID) " +
			"WHERE (PS.CODE = 'A') AND (PA.ACTIVE_IND = 'Y') "
		);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return convertResultListToReviewLocationIdCodeList(resultList);
	}

	@Override
	public List<ReviewLocationIdCode> findReviewLocationSelectionReasons() {
		Query query = entityManager.createNativeQuery(
			"SELECT DISTINCT P.REVIEW_LOCATION_ID, SR.CODE FROM ASSIGNMENT_TYPE_ADMIN A " +
			"INNER JOIN PERSONNEL_ASSIGNMENT_TYPE PA ON (PA.ASSIGNMENT_TYPE_CD = A.ASSIGNMENT_TYPE_CD) " +
			"INNER JOIN PERSONNEL P ON (P.PERSONNEL_ID = PA.PERSONNEL_ID) " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON (PS.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID) " +
			"INNER JOIN SELECTION_REASON SR ON (SR.SELECTION_REASON_ID = A.ASSIGNMENT_TYPE_REF_ID) " +
			"WHERE (PS.CODE = 'A') AND (PA.ACTIVE_IND = 'Y') "
		);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return convertResultListToReviewLocationIdCodeList(resultList);
	}

	public List<PersonnelIdCode> convertResultListToPersonnelIdCodeList(List<Object[]> resultList) {
		List<PersonnelIdCode> personnelIdCodes = new ArrayList<PersonnelIdCode>();
		for (Object[] result : resultList) {
			PersonnelIdCode personnelIdCode = new PersonnelIdCode();
			personnelIdCode.personnelId = (String)result[0];	// should always be a string so cast is correct
			personnelIdCode.code = result[1].toString();	// can be a character so toString() is needed
			personnelIdCodes.add(personnelIdCode);
		}

		return personnelIdCodes;
	}

	@Override
	public List<PersonnelIdCode> findPersonnelReviewTypeCodesByPersonnelIdIn(List<String> personnelIds) {
		Query query = entityManager.createNativeQuery(
			"SELECT DISTINCT P.PERSONNEL_ID, RT.REVIEW_TYPE_CD AS TYPE " +
			"FROM ASSIGNMENT_TYPE_ADMIN A " +
			"INNER JOIN PERSONNEL_ASSIGNMENT_TYPE PA ON (PA.ASSIGNMENT_TYPE_CD = A.ASSIGNMENT_TYPE_CD) " +
			"INNER JOIN PERSONNEL P ON (P.PERSONNEL_ID = PA.PERSONNEL_ID) " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON (PS.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID) " +
			"INNER JOIN REVIEW_TYPE_REF RT ON (RT.REVIEW_TYPE_ID = A.ASSIGNMENT_TYPE_REF_ID) " +
			"WHERE (PS.CODE = 'A') AND (PA.ACTIVE_IND = 'Y') AND (P.PERSONNEL_ID IN (:personnelIds)) "
		);
		query.setParameter("personnelIds", personnelIds);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return convertResultListToPersonnelIdCodeList(resultList);
	}

	@Override
	public List<PersonnelIdCode> findPersonnelReviewLevelTypeCodesByPersonnelIdIn(List<String> personnelIds) {
		Query query = entityManager.createNativeQuery(
			"SELECT DISTINCT P.PERSONNEL_ID, RLT.REVIEW_LEVEL_CD AS TYPE " +
			"FROM ASSIGNMENT_TYPE_ADMIN A " +
			"INNER JOIN PERSONNEL_ASSIGNMENT_TYPE PA ON (PA.ASSIGNMENT_TYPE_CD = A.ASSIGNMENT_TYPE_CD) " +
			"INNER JOIN PERSONNEL P ON (P.PERSONNEL_ID = PA.PERSONNEL_ID) " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON (PS.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID) " +
			"INNER JOIN REVIEW_LEVEL_TYPE_REF RLT ON (RLT.REVIEW_LEVEL_TYPE_ID = A.ASSIGNMENT_TYPE_REF_ID) " +
			"WHERE (PS.CODE = 'A') AND (PA.ACTIVE_IND = 'Y') AND (P.PERSONNEL_ID IN (:personnelIds)) "
		);
		query.setParameter("personnelIds", personnelIds);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return convertResultListToPersonnelIdCodeList(resultList);
	}

	@Override
	public List<PersonnelIdCode> findPersonnelProductTypeCodesByPersonnelIdIn(List<String> personnelIds) {
		Query query = entityManager.createNativeQuery(
			"SELECT DISTINCT P.PERSONNEL_ID, PT.PRODUCT_TYPE_CD FROM ASSIGNMENT_TYPE_ADMIN A " +
			"INNER JOIN PERSONNEL_ASSIGNMENT_TYPE PA ON (PA.ASSIGNMENT_TYPE_CD = A.ASSIGNMENT_TYPE_CD) " +
			"INNER JOIN PERSONNEL P ON (P.PERSONNEL_ID = PA.PERSONNEL_ID) " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON (PS.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID) " +
			"INNER JOIN PRODUCT_TYPE_REF PT ON (PT.PRODUCT_TYPE_ID = A.ASSIGNMENT_TYPE_REF_ID) " +
			"WHERE (PS.CODE = 'A') AND (PA.ACTIVE_IND = 'Y') AND (P.PERSONNEL_ID IN (:personnelIds)) "
		);
		query.setParameter("personnelIds", personnelIds);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return convertResultListToPersonnelIdCodeList(resultList);
	}

	@Override
	public List<PersonnelIdCode> findPersonnelSelectionReasonCodesByPersonnelIdIn(List<String> personnelIds) {
		Query query = entityManager.createNativeQuery(
			"SELECT DISTINCT P.PERSONNEL_ID, SR.CODE FROM ASSIGNMENT_TYPE_ADMIN A " +
			"INNER JOIN PERSONNEL_ASSIGNMENT_TYPE PA ON (PA.ASSIGNMENT_TYPE_CD = A.ASSIGNMENT_TYPE_CD) " +
			"INNER JOIN PERSONNEL P ON (P.PERSONNEL_ID = PA.PERSONNEL_ID) " +
			"INNER JOIN PERSONNEL_STATUS_REF PS ON (PS.PERSONNEL_STATUS_ID = P.PERSONNEL_STATUS_ID) " +
			"INNER JOIN SELECTION_REASON SR ON (SR.CONSOLIDATED_SELECTION_REASON_ID = A.ASSIGNMENT_TYPE_REF_ID) " +
			"WHERE (PS.CODE = 'A') AND (PA.ACTIVE_IND = 'Y') AND (P.PERSONNEL_ID IN (:personnelIds)) "
		);
		query.setParameter("personnelIds", personnelIds);
		@SuppressWarnings("unchecked")
		List<Object[]> resultList = query.getResultList();

		return convertResultListToPersonnelIdCodeList(resultList);
	}

}
