package gov.hud.lrs.common.enumeration;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class ScoringModelTypeCodes {

	public static final String ASSIGNMENT = "ASSIGNMENT";
	public static final String DISTRIBUTION = "DISTRIBUTION";
	public static final String EARLY_CLAIM_SELECTION = "EARLY_CLAIM_SELECTION";
	public static final String EARLY_PAYMENT_DEFAULT_SELECTION = "EARLY_PAYMENT_DEFAULT_SELECTION";
	public static final String ENDORSEMENT_SELECTION = "ENDORSEMENT_SELECTION";
	public static final String LENDER_MONITORING_SELECTION = "LENDER_MONITORING_SELECTION";
	public static final String NATIONAL_QC_SELECTION = "NATIONAL_QC_SELECTION";
	public static final String NONPERFORMING_SELECTION = "NONPERFORMING_SELECTION";
	public static final String FACTOR = "FACTOR";

	public static final List<String> SELECTION_CODES = ImmutableList.of(
		ENDORSEMENT_SELECTION,
		EARLY_CLAIM_SELECTION,
		EARLY_PAYMENT_DEFAULT_SELECTION,
		NATIONAL_QC_SELECTION,
		LENDER_MONITORING_SELECTION,
		NONPERFORMING_SELECTION,
		FACTOR
	);

}
