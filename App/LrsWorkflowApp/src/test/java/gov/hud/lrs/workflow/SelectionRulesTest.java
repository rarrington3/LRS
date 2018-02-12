package gov.hud.lrs.workflow;

import static org.junit.Assert.assertTrue;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEnvironment;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;

import gov.hud.lrs.common.entity.LenderSuppression;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.workflow.service.SelectionRulesParameters;

public class SelectionRulesTest {

	private static KieSession kSession;
	private SelectionRulesParameters selectionRulesParameters = new SelectionRulesParameters();
	private LoanSelectionCaseSummary loanSelectionCaseSummary;
	private Map<String, Double> factorNameToWeight = new HashMap<String, Double>();
	private Map<String, LenderSuppression> lenderUdToSuppression = new HashMap<String, LenderSuppression>();
	private SecureRandom random = new SecureRandom();

	@BeforeClass
	public static void setUpBeforeClass() {
		RuntimeEnvironment runtimeEnvironment = RuntimeEnvironmentBuilder
			.Factory
			.get()
			.newEmptyBuilder()
			.addAsset(KieServices.Factory.get().getResources().newClassPathResource("processes/ReviewProcess.bpmn2"), ResourceType.BPMN2)
			.addAsset(KieServices.Factory.get().getResources().newClassPathResource("rules/SelectionRules.drl"), ResourceType.DRL)
			.addAsset(KieServices.Factory.get().getResources().newClassPathResource("rules/DistributionRules.drl"), ResourceType.DRL)
			.addAsset(KieServices.Factory.get().getResources().newClassPathResource("rules/AssignmentRules.drl"), ResourceType.DRL)
			.persistence(false)
			.get()
		;
		RuntimeManager runtimeManager = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(runtimeEnvironment);
		kSession = runtimeManager.getRuntimeEngine(null).getKieSession();
	}

	@AfterClass
	public static void tearDownAfterClass() {
	}

	@Before
	public void setUp() {
		loanSelectionCaseSummary = new LoanSelectionCaseSummary();
		selectionRulesParameters.setLoanSelectionCaseSummary(loanSelectionCaseSummary);
		selectionRulesParameters.setFactorNameToWeight(factorNameToWeight);
		selectionRulesParameters.setSecureRandom(random);
		selectionRulesParameters.setLenderIdToSuppression(lenderUdToSuppression);

	}

	@After
	public void tearDown() {
		factorNameToWeight.clear();
		lenderUdToSuppression.clear();
	}

	@Test
	public void testEndorsementAppraisalDateDifferenceIsLT39() {
		Calendar calendarEndrsmntDt = Calendar.getInstance();
		loanSelectionCaseSummary.setEndrsmntDt(calendarEndrsmntDt.getTime());

		calendarEndrsmntDt.add(Calendar.DATE, 39);
		loanSelectionCaseSummary.setAprslCmpltnDt(calendarEndrsmntDt.getTime());
		factorNameToWeight.put("Endorsement Appraisal Date Difference LT 39 Multiplier", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 1.0);
	}

	@Test
	public void testEndorsementAppraisalDateDifferenceIsGT39() {
		Calendar calendarEndrsmntDt = Calendar.getInstance();
		loanSelectionCaseSummary.setEndrsmntDt(calendarEndrsmntDt.getTime());

		calendarEndrsmntDt.add(Calendar.DATE, 49);
		loanSelectionCaseSummary.setAprslCmpltnDt(calendarEndrsmntDt.getTime());
		factorNameToWeight.put("Endorsement Appraisal Date Difference LT 39 Multiplier", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

	//Gift Funds in 10Ks Multiplier
	@Test
	public void testGiftFundsIn10KsMultiplier() {
		Calendar calendarEndrsmntDt = Calendar.getInstance();
		loanSelectionCaseSummary.setGiftLtrAmt(new Float(20000));

		calendarEndrsmntDt.add(Calendar.DATE, 39);
		loanSelectionCaseSummary.setAprslCmpltnDt(calendarEndrsmntDt.getTime());
		factorNameToWeight.put("Gift Funds in 10Ks Multiplier", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 2.0);
	}

	//Gift Funds in 10Ks Multiplier
	@Test
	public void testGiftFundsIn10KsMultiplier_null() {
		Calendar calendarEndrsmntDt = Calendar.getInstance();
		loanSelectionCaseSummary.setGiftLtrAmt(null);

		calendarEndrsmntDt.add(Calendar.DATE, 39);
		loanSelectionCaseSummary.setAprslCmpltnDt(calendarEndrsmntDt.getTime());
		factorNameToWeight.put("Gift Funds in 10Ks Multiplier", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

	//Fixed Payment GT 2888 Multiplier
	@Test
	public void testFixedPaymentGT2888Multiplier_FixedPayment_2889() {
		Calendar calendarEndrsmntDt = Calendar.getInstance();
		loanSelectionCaseSummary.setTotFixedPymtEndrs(new Float(2889));

		calendarEndrsmntDt.add(Calendar.DATE, 39);
		loanSelectionCaseSummary.setAprslCmpltnDt(calendarEndrsmntDt.getTime());
		factorNameToWeight.put("Fixed Payment GT 2888 Multiplier", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 1.0);
	}

	//Fixed Payment GT 2888 Multiplier
	@Test
	public void testFixedPaymentGT2888Multiplier_FixedPayment_2887() {
		Calendar calendarEndrsmntDt = Calendar.getInstance();
		loanSelectionCaseSummary.setTotFixedPymtEndrs(new Float(2887));

		calendarEndrsmntDt.add(Calendar.DATE, 39);
		loanSelectionCaseSummary.setAprslCmpltnDt(calendarEndrsmntDt.getTime());
		factorNameToWeight.put("Fixed Payment GT 2888 Multiplier", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

	//Fixed Payment GT 2888 Multiplier
	@Test
	public void testFixedPaymentGT2888Multiplier_FixedPayment_uw_2889() {
		loanSelectionCaseSummary.setTotFixedPymtUw(new Float(2889));

		factorNameToWeight.put("Fixed Payment GT 2888 Multiplier", 1.0);
		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 1.0);
	}

	//Fixed Payment GT 2888 Multiplier
	@Test
	public void testFixedPaymentGT2888Multiplier_FixedPayment_Uw_2887() {

		loanSelectionCaseSummary.setTotFixedPymtUw(new Float(2887));

		factorNameToWeight.put("Fixed Payment GT 2888 Multiplier", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

	//Non-Refinance with LTV above 96.5-rule
	@Test
	public void testNonRefinanceWithLTVAbove965() {
		// TODO: verify this rule is written correctly, if not change these values back to what they were to pass the negation of the current rule
		loanSelectionCaseSummary.setRefinanceInd('Y');	// 'N'
		loanSelectionCaseSummary.setRatioLoanToVlNew(new Float(96.4));	// 96.5
		factorNameToWeight.put("Non-Refinance with LTV above 96.5", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 1.0);
	}

	@Test
	public void testNonRefinanceWithLTVAbove965_LTV_965() {
		loanSelectionCaseSummary.setRefinanceInd('N');
		loanSelectionCaseSummary.setRatioLoanToVlNew(new Float(96.5));
		factorNameToWeight.put("Non-Refinance with LTV above 96.5", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 1.0);
	}

	//Non-Refinance with LTV above 96.5-rule
	@Test
	public void testNonRefinanceWithLTVAbove965_ref_Y() {
		loanSelectionCaseSummary.setRefinanceInd('Y');
		loanSelectionCaseSummary.setRatioLoanToVlNew(new Float(96.6));
		factorNameToWeight.put("Non-Refinance with LTV above 96.5", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 1.0);
	}

	//Neighborhood Price in 10K
	@Test
	public void testNeighborhoodPriceIn10K() {
		loanSelectionCaseSummary.setNbrhdPrice(new Float(100000.0));
		factorNameToWeight.put("Neighborhood Price in 10K", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 010.0);
	}

	//Neighborhood Price in 10K
	@Test
	public void testNeighborhoodPriceIn10K_Price_null() {
		loanSelectionCaseSummary.setNbrhdPrice(null);

		factorNameToWeight.put("Neighborhood Price in 10K", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}
	//AUS Count Total
	@Test
	public void testAUSCountTotal() {
		loanSelectionCaseSummary.setCountAus(new Integer(10));

		factorNameToWeight.put("AUS Count Total", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 10.0);
	}

	//AUS Count Total
	@Test
	public void testAUSCountTotal_null() {
		loanSelectionCaseSummary.setCountAus(null);

		factorNameToWeight.put("AUS Count Total", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

	//Coborrower Credit Count
	@Test
	public void testCoborrowerCreditCount() {
		loanSelectionCaseSummary.setCoborr1FicoCnt(new Short((short) 10));

		factorNameToWeight.put("Coborrower Credit Count", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 10.0);
	}

	//Coborrower Credit Count
	@Test
	public void testCoborrowerCreditCount_null() {
		loanSelectionCaseSummary.setCoborr1FicoCnt(null);

		factorNameToWeight.put("Coborrower Credit Count", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

	//rule "Neighborhood Differential Percent BW -0.1098 and 0"
	@Test
	public void testNeighborhoodDifferentialPercentBW_01098_and_0_eq_zero() {
		loanSelectionCaseSummary.setPrprtyAprslVl(new Float(100000));
		loanSelectionCaseSummary.setNbrhdPrice(new Float(100000));

		factorNameToWeight.put("Neighborhood Differential Percent BW -0.1098 and 0", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.1);
	}

	//rule "Neighborhood Differential Percent BW -0.1098 and 0"
	@Test
	public void testNeighborhoodDifferentialPercentBW_01098_and_0_nagative() {
		loanSelectionCaseSummary.setPrprtyAprslVl(new Float(100000.0));
		loanSelectionCaseSummary.setNbrhdPrice(new Float(110000.0));

		factorNameToWeight.put("Neighborhood Differential Percent BW -0.1098 and 0", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.1);
	}

	@Test
	public void testNeighborhoodDifferentialPercentBW_01098_and_0_gt_zero() {
		loanSelectionCaseSummary.setPrprtyAprslVl(new Float(110000));
		loanSelectionCaseSummary.setNbrhdPrice(new Float(100000));

		factorNameToWeight.put("Neighborhood Differential Percent BW -0.1098 and 0", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

	//rule "Neighborhood Differential Percent BW -0.1098 and 0"
	@Test
	public void testNeighborhoodDifferentialPercentBW_01098_and_0_lt_01098() {
		loanSelectionCaseSummary.setPrprtyAprslVl(new Float(100000));
		loanSelectionCaseSummary.setNbrhdPrice(new Float(200000));

		factorNameToWeight.put("Neighborhood Differential Percent BW -0.1098 and 0", 1.0);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

	//rule "Neighborhood Differential Percent BW 0 and 0.0832"
	@Test
	public void testNeighborhoodDifferentialPercentBW0And00832_eq_zero() {
		loanSelectionCaseSummary.setPrprtyAprslVl(new Float(100000));
		loanSelectionCaseSummary.setNbrhdPrice(new Float(100000));

		factorNameToWeight.put("Neighborhood Differential Percent BW 0 and 0.0832", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

	//rule "Neighborhood Differential Percent BW 0 and 0.0832"
	@Test
	public void testNeighborhoodDifferentialPercentBW0And00832_GT_zero() {
		loanSelectionCaseSummary.setPrprtyAprslVl(new Float(101000));
		loanSelectionCaseSummary.setNbrhdPrice(new Float(100000));

		factorNameToWeight.put("Neighborhood Differential Percent BW 0 and 0.0832", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.1);
	}

	//rule "Neighborhood Differential Percent BW 0 and 0.0832"
	@Test
	public void testNeighborhoodDifferentialPercentBW0And00832_GT_00832() {
		loanSelectionCaseSummary.setPrprtyAprslVl(new Float(200000));
		loanSelectionCaseSummary.setNbrhdPrice(new Float(100000));

		factorNameToWeight.put("Neighborhood Differential Percent BW 0 and 0.0832", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

	//rule "Neighborhood Differential Percent GT 0.0832"
	@Test
	public void testNeighborhoodDifferentialPercentGT00832() {
		loanSelectionCaseSummary.setPrprtyAprslVl(new Float(200000));
		loanSelectionCaseSummary.setNbrhdPrice(new Float(100000));

		factorNameToWeight.put("Neighborhood Differential Percent GT 0.0832", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.1);
	}

	//rule "Neighborhood Differential Percent GT 0.0832"
	@Test
	public void testNeighborhoodDifferentialPercentGT00832_LT() {
		loanSelectionCaseSummary.setPrprtyAprslVl(new Float(101000));
		loanSelectionCaseSummary.setNbrhdPrice(new Float(100000));

		factorNameToWeight.put("Neighborhood Differential Percent GT 0.0832", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

	//rule "Total Monthly Borrower Income below 3070"
	@Test
	public void testTotalMonthlyBorrowerIncomeBelow3070() {
		loanSelectionCaseSummary.setTotMnthlyEffIncm(new Float(3069));

		factorNameToWeight.put("Total Monthly Borrower Income below 3070", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.1);
	}
	//rule "Total Monthly Borrower Income below 3070"
	@Test
	public void testTotalMonthlyBorrowerIncomeBelow3070_GT() {
		loanSelectionCaseSummary.setMiTotalAmt(new Float(3079));

		factorNameToWeight.put("Total Monthly Borrower Income below 3070", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

	//New and Old Refinance Difference Multiplier
	@Test
	public void testNewAndOldRefinanceDifferenceMultiplier() {
		loanSelectionCaseSummary.setMaxClaimAmt(new Float(40000));
		loanSelectionCaseSummary.setPrrCaseMxmmClmAmt(new Float(20000));

		factorNameToWeight.put("New and Old Refinance Difference Multiplier", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.1);
	}

	//New and Old Refinance Difference Multiplier
	@Test
	public void testNewAndOldRefinanceDifferenceMultiplier_zero() {
		loanSelectionCaseSummary.setMaxClaimAmt(new Float(20000));
		loanSelectionCaseSummary.setPrrCaseMxmmClmAmt(new Float(20000));

		factorNameToWeight.put("Total Monthly Borrower Income below 3070", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}
	//New and Old Refinance Difference Multiplier
	@Test
	public void testNewAndOldRefinanceDifferenceMultiplier_GT_20000() {
		loanSelectionCaseSummary.setMaxClaimAmt(new Float(60000));
		loanSelectionCaseSummary.setPrrCaseMxmmClmAmt(new Float(20000));

		factorNameToWeight.put("Total Monthly Borrower Income below 3070", 0.1);

		kSession.insert(selectionRulesParameters);
		kSession.fireAllRules();
		assertTrue(selectionRulesParameters.getScore() == 0.0);
	}

}
