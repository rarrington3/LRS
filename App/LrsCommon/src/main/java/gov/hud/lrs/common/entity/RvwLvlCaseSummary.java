package gov.hud.lrs.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="RVW_LVL_CASE_SUMMARY")
@SuppressWarnings("serial")
public class RvwLvlCaseSummary implements Serializable {

	private String reviewLevelId;
	private ReviewLevel reviewLevel;

	private Character addtnl10pctIplUsageInd;
	private String adpCode;
	private String amortTypCd;
	private Date applicationDate;
	private String appraiserName;
	private Date aprslCmpltnDt;
	private String armInd;
	private Float armIndxExpctdRt;
	private Float assetsAfterClosingUw;
	private Character assumedLoanInd;
	private Character backToWorkInd;
	private Character bldgTyp;
	private Character bnkrptcyAnyInd;
	private String bnkrptcyCd;
	private Character bnkrptcyChptr13Ind;
	private Character bnkrptcyChptr7Ind;
	private Date bnkrptcyDt;
	private Character borr1FirstTimeBuyerInd;
	private String borr1Name;
	private Character borr1RentingInd;
	private Character borr1SelfEmplInd;
	private String borr1Ssn;
	private String borr2Name;
	private String borr2Ssn;
	private Date borrBrthDt;
	private Float borrHsngExpEndrs;
	private Float borrPaidClosingCosts;
	private Float borrReqdInvestToClose;
	private String borrTyp;
	private Character buildingOnOwnLandInd;
	private String caseNumber;
	private Character cashoutRefiInd;
	private String claimType;
	private Date clsngDt;
	private Float combinedLoanToValuePct;
	private String constCd;
	private Character currentAtEndorseInd;
	private Date dateOfPriorSale;
	private String dcsnCdEndrs;
	private Character defaultEpisodeExistsInd;
	private Character disasterInd;
	private Date effDateAprslUpdate;
	private Character endrsBynd60DaysCloseInd;
	private Date endrsmntDt;
	private Float escrowAmount;
	private Float expectedRate;
	private Character fctryFbrct;
	private Integer ficoDecisionScoreEndrs;
	private Character flippingCategory2Ind;
	private Character frclsrInd;
	private Date frclsrStrtDt;
	private Date ftInEps3mnthDelqDt;
	private Float giftLtr2Amt;
	private String giftLtr2Source;
	private String giftLtr2Tin;
	private Float giftLtrAmt;
	private String giftLtrSrc;
	private String giftLtrTin;
	private String hecmCounselCertNo;
	private Date hecmCounselDt;
	private Float hecmPrncplLmt;
	private Float hudReoRepairAmt;
	private Float initDisbursementLimit;
	private Character insurAppInTimeInd;
	private Float intRt;
	private Character invest2ndResidInd;
	private Date lastServicingMrtgXferDt;
	private String loanOfficer;
	private String loanOfficerNmls;
	private String loanPrps;
	private String loanType;
	private String lossmitCd;
	private Float mandatoryObligBorrAmt;
	private Float mandatoryObligLendAmt;
	private Character manUwStretchRatioInd;
	private Character marriedToNbsInd;
	private Float maxClaimAmt;
	private Float maxRate;
	private String miscAusDcsnCd;
	private Float mndtryOblgtnsAmt;
	private Character mnfctrdHusngInd;
	private Float mortExcldFncdMip;
	private Character nonOccupyingBorrInd;
	private Short numLivingUnits;
	private String ocpncyStsCd;
	private Date oldstUnpdDt;
	private String orgntngMtgeeNmlsId;
	private Float originationFee;
	private Float origMrtgAmt;
	private String paymentPlan;
	private String pdStrmlnFlg;
	private Float priceOfPriorSale;
	private Character priorSaleWithinLast3yrInd;
	private String productTypeCd;
	private String propAddr1;
	private String propAddr2;
	private String propAddrCity;
	private String propAddrSt;
	private String propAddrZip;
	private Float prprtyAprslVl;
	private Float qualifiedMrtgPointsAndFees;
	private Float ratioFixTeiEndrs;
	private Float ratioLoanToVlNew;
	private Float ratioTotPmtToTotIncEndrs;
	private Date rcvSaleDt;
	private Character refinanceInd;
	private Character reo100DownPmtProgInd;
	private Date repairCompletionDate;
	private Character reviewTypeCd;
	private String rfncCd;
	private Character salePriceGtrAcqCostInd;
	private Float salesPrice;
	private Float secondaryFinanceAmt;
	private Character secondaryFinanceExistsInd;
	private Float sellerCntrbtn;
	private Float sellerCntrbtnPcnt;
	private String siteType;
	private String soaCd;
	private String specialProgram;
	private Float taxesInsrncFrstYrAmt;
	private Character tenYrRateLockInd;
	private String termTypCd;
	private Float totalRequiredFundsToClose;
	private Float totAssetsEndrs;
	private Float totClsngCstsEndrs;
	private Float totFixedPymtEndrs;
	private Float totMnthlyEffIncm;
	private Float totMnthlyMtgPymtEndrs;
	private String underwritingMethod;
	private String yearBuilt;

	private String createdBy;
	private Date createdTs;

	private String updatedBy;
	private Date updatedTs;

	public RvwLvlCaseSummary() {
	}

	@Id
    @GenericGenerator(name="generator", strategy="foreign", parameters=@Parameter(name="property", value="reviewLevel"))
    @GeneratedValue(generator="generator")
    @Column(name="REVIEW_LEVEL_ID", unique=true, nullable=false, length=36)
	public String getReviewLevelId() {
		return this.reviewLevelId;
	}

	public void setReviewLevelId(String reviewLevelId) {
		this.reviewLevelId = reviewLevelId;
	}

	@OneToOne(fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public ReviewLevel getReviewLevel() {
		return this.reviewLevel;
	}

	public void setReviewLevel(ReviewLevel reviewLevel) {
		this.reviewLevel = reviewLevel;
	}

	@Column(name="ADDTNL_10PCT_IPL_USAGE_IND", length=1)
	public Character getAddtnl10pctIplUsageInd() {
		return this.addtnl10pctIplUsageInd;
	}

	public void setAddtnl10pctIplUsageInd(Character addtnl10pctIplUsageInd) {
		this.addtnl10pctIplUsageInd = addtnl10pctIplUsageInd;
	}

	@Column(name="ADP_CODE", length=16)
	public String getAdpCode() {
		return this.adpCode;
	}

	public void setAdpCode(String adpCode) {
		this.adpCode = adpCode;
	}

	@Column(name="AMORT_TYP_CD", length=16)
	public String getAmortTypCd() {
		return this.amortTypCd;
	}

	public void setAmortTypCd(String amortTypCd) {
		this.amortTypCd = amortTypCd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="APPLICATION_DATE", length=23)
	public Date getApplicationDate() {
		return this.applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	@Column(name="APPRAISER_NAME", length=100)
	public String getAppraiserName() {
		return this.appraiserName;
	}

	public void setAppraiserName(String appraiserName) {
		this.appraiserName = appraiserName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="APRSL_CMPLTN_DT", length=23)
	public Date getAprslCmpltnDt() {
		return this.aprslCmpltnDt;
	}

	public void setAprslCmpltnDt(Date aprslCmpltnDt) {
		this.aprslCmpltnDt = aprslCmpltnDt;
	}

	@Column(name="ARM_IND", length=3)
	public String getArmInd() {
		return this.armInd;
	}

	public void setArmInd(String armInd) {
		this.armInd = armInd;
	}

	@Column(name="ARM_INDX_EXPCTD_RT")
	public Float getArmIndxExpctdRt() {
		return this.armIndxExpctdRt;
	}

	public void setArmIndxExpctdRt(Float armIndxExpctdRt) {
		this.armIndxExpctdRt = armIndxExpctdRt;
	}

	@Column(name="ASSETS_AFTER_CLOSING_UW")
	public Float getAssetsAfterClosingUw() {
		return this.assetsAfterClosingUw;
	}

	public void setAssetsAfterClosingUw(Float assetsAfterClosingUw) {
		this.assetsAfterClosingUw = assetsAfterClosingUw;
	}

	@Column(name="ASSUMED_LOAN_IND", length=1)
	public Character getAssumedLoanInd() {
		return this.assumedLoanInd;
	}

	public void setAssumedLoanInd(Character assumedLoanInd) {
		this.assumedLoanInd = assumedLoanInd;
	}

	@Column(name="BACK_TO_WORK_IND", length=1)
	public Character getBackToWorkInd() {
		return this.backToWorkInd;
	}

	public void setBackToWorkInd(Character backToWorkInd) {
		this.backToWorkInd = backToWorkInd;
	}

	@Column(name="BLDG_TYP", length=1)
	public Character getBldgTyp() {
		return this.bldgTyp;
	}

	public void setBldgTyp(Character bldgTyp) {
		this.bldgTyp = bldgTyp;
	}

	@Column(name="BNKRPTCY_ANY_IND", length=1)
	public Character getBnkrptcyAnyInd() {
		return this.bnkrptcyAnyInd;
	}

	public void setBnkrptcyAnyInd(Character bnkrptcyAnyInd) {
		this.bnkrptcyAnyInd = bnkrptcyAnyInd;
	}

	@Column(name="BNKRPTCY_CD", length=16)
	public String getBnkrptcyCd() {
		return this.bnkrptcyCd;
	}

	public void setBnkrptcyCd(String bnkrptcyCd) {
		this.bnkrptcyCd = bnkrptcyCd;
	}

	@Column(name="BNKRPTCY_CHPTR13_IND", length=1)
	public Character getBnkrptcyChptr13Ind() {
		return this.bnkrptcyChptr13Ind;
	}

	public void setBnkrptcyChptr13Ind(Character bnkrptcyChptr13Ind) {
		this.bnkrptcyChptr13Ind = bnkrptcyChptr13Ind;
	}

	@Column(name="BNKRPTCY_CHPTR7_IND", length=1)
	public Character getBnkrptcyChptr7Ind() {
		return this.bnkrptcyChptr7Ind;
	}

	public void setBnkrptcyChptr7Ind(Character bnkrptcyChptr7Ind) {
		this.bnkrptcyChptr7Ind = bnkrptcyChptr7Ind;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="BNKRPTCY_DT", length=23)
	public Date getBnkrptcyDt() {
		return this.bnkrptcyDt;
	}

	public void setBnkrptcyDt(Date bnkrptcyDt) {
		this.bnkrptcyDt = bnkrptcyDt;
	}

	@Column(name="BORR_1_FIRST_TIME_BUYER_IND", length=1)
	public Character getBorr1FirstTimeBuyerInd() {
		return this.borr1FirstTimeBuyerInd;
	}

	public void setBorr1FirstTimeBuyerInd(Character borr1FirstTimeBuyerInd) {
		this.borr1FirstTimeBuyerInd = borr1FirstTimeBuyerInd;
	}

	@Column(name="BORR_1_NAME", length=100)
	public String getBorr1Name() {
		return this.borr1Name;
	}

	public void setBorr1Name(String borr1Name) {
		this.borr1Name = borr1Name;
	}

	@Column(name="BORR_1_RENTING_IND", length=1)
	public Character getBorr1RentingInd() {
		return this.borr1RentingInd;
	}

	public void setBorr1RentingInd(Character borr1RentingInd) {
		this.borr1RentingInd = borr1RentingInd;
	}

	@Column(name="BORR_1_SELF_EMPL_IND", length=1)
	public Character getBorr1SelfEmplInd() {
		return this.borr1SelfEmplInd;
	}

	public void setBorr1SelfEmplInd(Character borr1SelfEmplInd) {
		this.borr1SelfEmplInd = borr1SelfEmplInd;
	}

	@Column(name="BORR_1_SSN", length=11)
	public String getBorr1Ssn() {
		return this.borr1Ssn;
	}

	public void setBorr1Ssn(String borr1Ssn) {
		this.borr1Ssn = borr1Ssn;
	}

	@Column(name="BORR_2_NAME", length=100)
	public String getBorr2Name() {
		return this.borr2Name;
	}

	public void setBorr2Name(String borr2Name) {
		this.borr2Name = borr2Name;
	}

	@Column(name="BORR_2_SSN", length=11)
	public String getBorr2Ssn() {
		return this.borr2Ssn;
	}

	public void setBorr2Ssn(String borr2Ssn) {
		this.borr2Ssn = borr2Ssn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="BORR_BRTH_DT", length=23)
	public Date getBorrBrthDt() {
		return this.borrBrthDt;
	}

	public void setBorrBrthDt(Date borrBrthDt) {
		this.borrBrthDt = borrBrthDt;
	}

	@Column(name="BORR_HSNG_EXP_ENDRS")
	public Float getBorrHsngExpEndrs() {
		return this.borrHsngExpEndrs;
	}

	public void setBorrHsngExpEndrs(Float borrHsngExpEndrs) {
		this.borrHsngExpEndrs = borrHsngExpEndrs;
	}

	@Column(name="BORR_PAID_CLOSING_COSTS")
	public Float getBorrPaidClosingCosts() {
		return this.borrPaidClosingCosts;
	}

	public void setBorrPaidClosingCosts(Float borrPaidClosingCosts) {
		this.borrPaidClosingCosts = borrPaidClosingCosts;
	}

	@Column(name="BORR_REQD_INVEST_TO_CLOSE")
	public Float getBorrReqdInvestToClose() {
		return this.borrReqdInvestToClose;
	}

	public void setBorrReqdInvestToClose(Float borrReqdInvestToClose) {
		this.borrReqdInvestToClose = borrReqdInvestToClose;
	}

	@Column(name="BORR_TYP", length=16)
	public String getBorrTyp() {
		return this.borrTyp;
	}

	public void setBorrTyp(String borrTyp) {
		this.borrTyp = borrTyp;
	}

	@Column(name="BUILDING_ON_OWN_LAND_IND", length=1)
	public Character getBuildingOnOwnLandInd() {
		return this.buildingOnOwnLandInd;
	}

	public void setBuildingOnOwnLandInd(Character buildingOnOwnLandInd) {
		this.buildingOnOwnLandInd = buildingOnOwnLandInd;
	}

	@Column(name="CASE_NUMBER", length=11)
	public String getCaseNumber() {
		return this.caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	@Column(name="CASHOUT_REFI_IND", length=1)
	public Character getCashoutRefiInd() {
		return this.cashoutRefiInd;
	}

	public void setCashoutRefiInd(Character cashoutRefiInd) {
		this.cashoutRefiInd = cashoutRefiInd;
	}

	@Column(name="CLAIM_TYPE", length=16)
	public String getClaimType() {
		return this.claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CLSNG_DT", length=23)
	public Date getClsngDt() {
		return this.clsngDt;
	}

	public void setClsngDt(Date clsngDt) {
		this.clsngDt = clsngDt;
	}

	@Column(name="COMBINED_LOAN_TO_VALUE_PCT")
	public Float getCombinedLoanToValuePct() {
		return this.combinedLoanToValuePct;
	}

	public void setCombinedLoanToValuePct(Float combinedLoanToValuePct) {
		this.combinedLoanToValuePct = combinedLoanToValuePct;
	}

	@Column(name="CONST_CD", length=16)
	public String getConstCd() {
		return this.constCd;
	}

	public void setConstCd(String constCd) {
		this.constCd = constCd;
	}

	@Column(name="CURRENT_AT_ENDORSE_IND", length=1)
	public Character getCurrentAtEndorseInd() {
		return this.currentAtEndorseInd;
	}

	public void setCurrentAtEndorseInd(Character currentAtEndorseInd) {
		this.currentAtEndorseInd = currentAtEndorseInd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_OF_PRIOR_SALE", length=23)
	public Date getDateOfPriorSale() {
		return this.dateOfPriorSale;
	}

	public void setDateOfPriorSale(Date dateOfPriorSale) {
		this.dateOfPriorSale = dateOfPriorSale;
	}

	@Column(name="DCSN_CD_ENDRS", length=16)
	public String getDcsnCdEndrs() {
		return this.dcsnCdEndrs;
	}

	public void setDcsnCdEndrs(String dcsnCdEndrs) {
		this.dcsnCdEndrs = dcsnCdEndrs;
	}

	@Column(name="DEFAULT_EPISODE_EXISTS_IND", length=1)
	public Character getDefaultEpisodeExistsInd() {
		return this.defaultEpisodeExistsInd;
	}

	public void setDefaultEpisodeExistsInd(Character defaultEpisodeExistsInd) {
		this.defaultEpisodeExistsInd = defaultEpisodeExistsInd;
	}

	@Column(name="DISASTER_IND", length=1)
	public Character getDisasterInd() {
		return this.disasterInd;
	}

	public void setDisasterInd(Character disasterInd) {
		this.disasterInd = disasterInd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EFF_DATE_APRSL_UPDATE", length=23)
	public Date getEffDateAprslUpdate() {
		return this.effDateAprslUpdate;
	}

	public void setEffDateAprslUpdate(Date effDateAprslUpdate) {
		this.effDateAprslUpdate = effDateAprslUpdate;
	}

	@Column(name="ENDRS_BYND_60_DAYS_CLOSE_IND", length=1)
	public Character getEndrsBynd60DaysCloseInd() {
		return this.endrsBynd60DaysCloseInd;
	}

	public void setEndrsBynd60DaysCloseInd(Character endrsBynd60DaysCloseInd) {
		this.endrsBynd60DaysCloseInd = endrsBynd60DaysCloseInd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ENDRSMNT_DT", length=23)
	public Date getEndrsmntDt() {
		return this.endrsmntDt;
	}

	public void setEndrsmntDt(Date endrsmntDt) {
		this.endrsmntDt = endrsmntDt;
	}

	@Column(name="ESCROW_AMOUNT")
	public Float getEscrowAmount() {
		return this.escrowAmount;
	}

	public void setEscrowAmount(Float escrowAmount) {
		this.escrowAmount = escrowAmount;
	}

	@Column(name="EXPECTED_RATE")
	public Float getExpectedRate() {
		return this.expectedRate;
	}

	public void setExpectedRate(Float expectedRate) {
		this.expectedRate = expectedRate;
	}

	@Column(name="FCTRY_FBRCT", length=1)
	public Character getFctryFbrct() {
		return this.fctryFbrct;
	}

	public void setFctryFbrct(Character fctryFbrct) {
		this.fctryFbrct = fctryFbrct;
	}

	@Column(name="FICO_DECISION_SCORE_ENDRS")
	public Integer getFicoDecisionScoreEndrs() {
		return this.ficoDecisionScoreEndrs;
	}

	public void setFicoDecisionScoreEndrs(Integer ficoDecisionScoreEndrs) {
		this.ficoDecisionScoreEndrs = ficoDecisionScoreEndrs;
	}

	@Column(name="FLIPPING_CATEGORY_2_IND", length=1)
	public Character getFlippingCategory2Ind() {
		return this.flippingCategory2Ind;
	}

	public void setFlippingCategory2Ind(Character flippingCategory2Ind) {
		this.flippingCategory2Ind = flippingCategory2Ind;
	}

	@Column(name="FRCLSR_IND", length=1)
	public Character getFrclsrInd() {
		return this.frclsrInd;
	}

	public void setFrclsrInd(Character frclsrInd) {
		this.frclsrInd = frclsrInd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FRCLSR_STRT_DT", length=23)
	public Date getFrclsrStrtDt() {
		return this.frclsrStrtDt;
	}

	public void setFrclsrStrtDt(Date frclsrStrtDt) {
		this.frclsrStrtDt = frclsrStrtDt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FT_IN_EPS_3MNTH_DELQ_DT", length=23)
	public Date getFtInEps3mnthDelqDt() {
		return this.ftInEps3mnthDelqDt;
	}

	public void setFtInEps3mnthDelqDt(Date ftInEps3mnthDelqDt) {
		this.ftInEps3mnthDelqDt = ftInEps3mnthDelqDt;
	}

	@Column(name="GIFT_LTR_2_AMT")
	public Float getGiftLtr2Amt() {
		return this.giftLtr2Amt;
	}

	public void setGiftLtr2Amt(Float giftLtr2Amt) {
		this.giftLtr2Amt = giftLtr2Amt;
	}

	@Column(name="GIFT_LTR_2_SOURCE", length=32)
	public String getGiftLtr2Source() {
		return this.giftLtr2Source;
	}

	public void setGiftLtr2Source(String giftLtr2Source) {
		this.giftLtr2Source = giftLtr2Source;
	}

	@Column(name="GIFT_LTR_2_TIN", length=11)
	public String getGiftLtr2Tin() {
		return this.giftLtr2Tin;
	}

	public void setGiftLtr2Tin(String giftLtr2Tin) {
		this.giftLtr2Tin = giftLtr2Tin;
	}

	@Column(name="GIFT_LTR_AMT")
	public Float getGiftLtrAmt() {
		return this.giftLtrAmt;
	}

	public void setGiftLtrAmt(Float giftLtrAmt) {
		this.giftLtrAmt = giftLtrAmt;
	}

	@Column(name="GIFT_LTR_SRC", length=32)
	public String getGiftLtrSrc() {
		return this.giftLtrSrc;
	}

	public void setGiftLtrSrc(String giftLtrSrc) {
		this.giftLtrSrc = giftLtrSrc;
	}

	@Column(name="GIFT_LTR_TIN", length=13)
	public String getGiftLtrTin() {
		return this.giftLtrTin;
	}

	public void setGiftLtrTin(String giftLtrTin) {
		this.giftLtrTin = giftLtrTin;
	}

	@Column(name="HECM_COUNSEL_CERT_NO", length=36)
	public String getHecmCounselCertNo() {
		return this.hecmCounselCertNo;
	}

	public void setHecmCounselCertNo(String hecmCounselCertNo) {
		this.hecmCounselCertNo = hecmCounselCertNo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="HECM_COUNSEL_DT", length=23)
	public Date getHecmCounselDt() {
		return this.hecmCounselDt;
	}

	public void setHecmCounselDt(Date hecmCounselDt) {
		this.hecmCounselDt = hecmCounselDt;
	}

	@Column(name="HECM_PRNCPL_LMT")
	public Float getHecmPrncplLmt() {
		return this.hecmPrncplLmt;
	}

	public void setHecmPrncplLmt(Float hecmPrncplLmt) {
		this.hecmPrncplLmt = hecmPrncplLmt;
	}

	@Column(name="HUD_REO_REPAIR_AMT")
	public Float getHudReoRepairAmt() {
		return this.hudReoRepairAmt;
	}

	public void setHudReoRepairAmt(Float hudReoRepairAmt) {
		this.hudReoRepairAmt = hudReoRepairAmt;
	}

	@Column(name="INIT_DISBURSEMENT_LIMIT")
	public Float getInitDisbursementLimit() {
		return this.initDisbursementLimit;
	}

	public void setInitDisbursementLimit(Float initDisbursementLimit) {
		this.initDisbursementLimit = initDisbursementLimit;
	}

	@Column(name="INSUR_APP_IN_TIME_IND", length=1)
	public Character getInsurAppInTimeInd() {
		return this.insurAppInTimeInd;
	}

	public void setInsurAppInTimeInd(Character insurAppInTimeInd) {
		this.insurAppInTimeInd = insurAppInTimeInd;
	}

	@Column(name="INT_RT")
	public Float getIntRt() {
		return this.intRt;
	}

	public void setIntRt(Float intRt) {
		this.intRt = intRt;
	}

	@Column(name="INVEST_2ND_RESID_IND", length=1)
	public Character getInvest2ndResidInd() {
		return this.invest2ndResidInd;
	}

	public void setInvest2ndResidInd(Character invest2ndResidInd) {
		this.invest2ndResidInd = invest2ndResidInd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_SERVICING_MRTG_XFER_DT", length=23)
	public Date getLastServicingMrtgXferDt() {
		return this.lastServicingMrtgXferDt;
	}

	public void setLastServicingMrtgXferDt(Date lastServicingMrtgXferDt) {
		this.lastServicingMrtgXferDt = lastServicingMrtgXferDt;
	}

	@Column(name="LOAN_OFFICER", length=100)
	public String getLoanOfficer() {
		return this.loanOfficer;
	}

	public void setLoanOfficer(String loanOfficer) {
		this.loanOfficer = loanOfficer;
	}

	@Column(name="LOAN_OFFICER_NMLS", length=12)
	public String getLoanOfficerNmls() {
		return this.loanOfficerNmls;
	}

	public void setLoanOfficerNmls(String loanOfficerNmls) {
		this.loanOfficerNmls = loanOfficerNmls;
	}

	@Column(name="LOAN_PRPS", length=3)
	public String getLoanPrps() {
		return this.loanPrps;
	}

	public void setLoanPrps(String loanPrps) {
		this.loanPrps = loanPrps;
	}

	@Column(name="LOAN_TYPE", length=16)
	public String getLoanType() {
		return this.loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	@Column(name="LOSSMIT_CD", length=16)
	public String getLossmitCd() {
		return this.lossmitCd;
	}

	public void setLossmitCd(String lossmitCd) {
		this.lossmitCd = lossmitCd;
	}

	@Column(name="MANDATORY_OBLIG_BORR_AMT")
	public Float getMandatoryObligBorrAmt() {
		return this.mandatoryObligBorrAmt;
	}

	public void setMandatoryObligBorrAmt(Float mandatoryObligBorrAmt) {
		this.mandatoryObligBorrAmt = mandatoryObligBorrAmt;
	}

	@Column(name="MANDATORY_OBLIG_LEND_AMT")
	public Float getMandatoryObligLendAmt() {
		return this.mandatoryObligLendAmt;
	}

	public void setMandatoryObligLendAmt(Float mandatoryObligLendAmt) {
		this.mandatoryObligLendAmt = mandatoryObligLendAmt;
	}

	@Column(name="MAN_UW_STRETCH_RATIO_IND", length=1)
	public Character getManUwStretchRatioInd() {
		return this.manUwStretchRatioInd;
	}

	public void setManUwStretchRatioInd(Character manUwStretchRatioInd) {
		this.manUwStretchRatioInd = manUwStretchRatioInd;
	}

	@Column(name="MARRIED_TO_NBS_IND", length=1)
	public Character getMarriedToNbsInd() {
		return this.marriedToNbsInd;
	}

	public void setMarriedToNbsInd(Character marriedToNbsInd) {
		this.marriedToNbsInd = marriedToNbsInd;
	}

	@Column(name="MAX_CLAIM_AMT")
	public Float getMaxClaimAmt() {
		return this.maxClaimAmt;
	}

	public void setMaxClaimAmt(Float maxClaimAmt) {
		this.maxClaimAmt = maxClaimAmt;
	}

	@Column(name="MAX_RATE")
	public Float getMaxRate() {
		return this.maxRate;
	}

	public void setMaxRate(Float maxRate) {
		this.maxRate = maxRate;
	}

	@Column(name="MISC_AUS_DCSN_CD", length=16)
	public String getMiscAusDcsnCd() {
		return this.miscAusDcsnCd;
	}

	public void setMiscAusDcsnCd(String miscAusDcsnCd) {
		this.miscAusDcsnCd = miscAusDcsnCd;
	}

	@Column(name="MNDTRY_OBLGTNS_AMT")
	public Float getMndtryOblgtnsAmt() {
		return this.mndtryOblgtnsAmt;
	}

	public void setMndtryOblgtnsAmt(Float mndtryOblgtnsAmt) {
		this.mndtryOblgtnsAmt = mndtryOblgtnsAmt;
	}

	@Column(name="MNFCTRD_HUSNG_IND", length=1)
	public Character getMnfctrdHusngInd() {
		return this.mnfctrdHusngInd;
	}

	public void setMnfctrdHusngInd(Character mnfctrdHusngInd) {
		this.mnfctrdHusngInd = mnfctrdHusngInd;
	}

	@Column(name="MORT_EXCLD_FNCD_MIP")
	public Float getMortExcldFncdMip() {
		return this.mortExcldFncdMip;
	}

	public void setMortExcldFncdMip(Float mortExcldFncdMip) {
		this.mortExcldFncdMip = mortExcldFncdMip;
	}

	@Column(name="NON_OCCUPYING_BORR_IND", length=1)
	public Character getNonOccupyingBorrInd() {
		return this.nonOccupyingBorrInd;
	}

	public void setNonOccupyingBorrInd(Character nonOccupyingBorrInd) {
		this.nonOccupyingBorrInd = nonOccupyingBorrInd;
	}

	@Column(name="NUM_LIVING_UNITS")
	public Short getNumLivingUnits() {
		return this.numLivingUnits;
	}

	public void setNumLivingUnits(Short numLivingUnits) {
		this.numLivingUnits = numLivingUnits;
	}

	@Column(name="OCPNCY_STS_CD", length=16)
	public String getOcpncyStsCd() {
		return this.ocpncyStsCd;
	}

	public void setOcpncyStsCd(String ocpncyStsCd) {
		this.ocpncyStsCd = ocpncyStsCd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="OLDST_UNPD_DT", length=23)
	public Date getOldstUnpdDt() {
		return this.oldstUnpdDt;
	}

	public void setOldstUnpdDt(Date oldstUnpdDt) {
		this.oldstUnpdDt = oldstUnpdDt;
	}

	@Column(name="ORGNTNG_MTGEE_NMLS_ID", length=12)
	public String getOrgntngMtgeeNmlsId() {
		return this.orgntngMtgeeNmlsId;
	}

	public void setOrgntngMtgeeNmlsId(String orgntngMtgeeNmlsId) {
		this.orgntngMtgeeNmlsId = orgntngMtgeeNmlsId;
	}

	@Column(name="ORIGINATION_FEE")
	public Float getOriginationFee() {
		return this.originationFee;
	}

	public void setOriginationFee(Float originationFee) {
		this.originationFee = originationFee;
	}

	@Column(name="ORIG_MRTG_AMT")
	public Float getOrigMrtgAmt() {
		return this.origMrtgAmt;
	}

	public void setOrigMrtgAmt(Float origMrtgAmt) {
		this.origMrtgAmt = origMrtgAmt;
	}

	@Column(name="PAYMENT_PLAN", length=16)
	public String getPaymentPlan() {
		return this.paymentPlan;
	}

	public void setPaymentPlan(String paymentPlan) {
		this.paymentPlan = paymentPlan;
	}

	@Column(name="PD_STRMLN_FLG", length=3)
	public String getPdStrmlnFlg() {
		return this.pdStrmlnFlg;
	}

	public void setPdStrmlnFlg(String pdStrmlnFlg) {
		this.pdStrmlnFlg = pdStrmlnFlg;
	}

	@Column(name="PRICE_OF_PRIOR_SALE")
	public Float getPriceOfPriorSale() {
		return this.priceOfPriorSale;
	}

	public void setPriceOfPriorSale(Float priceOfPriorSale) {
		this.priceOfPriorSale = priceOfPriorSale;
	}

	@Column(name="PRIOR_SALE_WITHIN_LAST_3YR_IND", length=1)
	public Character getPriorSaleWithinLast3yrInd() {
		return this.priorSaleWithinLast3yrInd;
	}

	public void setPriorSaleWithinLast3yrInd(Character priorSaleWithinLast3yrInd) {
		this.priorSaleWithinLast3yrInd = priorSaleWithinLast3yrInd;
	}

	@Column(name="PRODUCT_TYPE_CD", length=5)
	public String getProductTypeCd() {
		return this.productTypeCd;
	}

	public void setProductTypeCd(String productTypeCd) {
		this.productTypeCd = productTypeCd;
	}

	@Column(name="PROP_ADDR_1", length=100)
	public String getPropAddr1() {
		return this.propAddr1;
	}

	public void setPropAddr1(String propAddr1) {
		this.propAddr1 = propAddr1;
	}

	@Column(name="PROP_ADDR_2", length=100)
	public String getPropAddr2() {
		return this.propAddr2;
	}

	public void setPropAddr2(String propAddr2) {
		this.propAddr2 = propAddr2;
	}

	@Column(name="PROP_ADDR_CITY", length=100)
	public String getPropAddrCity() {
		return this.propAddrCity;
	}

	public void setPropAddrCity(String propAddrCity) {
		this.propAddrCity = propAddrCity;
	}

	@Column(name="PROP_ADDR_ST", length=2)
	public String getPropAddrSt() {
		return this.propAddrSt;
	}

	public void setPropAddrSt(String propAddrSt) {
		this.propAddrSt = propAddrSt;
	}

	@Column(name="PROP_ADDR_ZIP", length=5)
	public String getPropAddrZip() {
		return this.propAddrZip;
	}

	public void setPropAddrZip(String propAddrZip) {
		this.propAddrZip = propAddrZip;
	}

	@Column(name="PRPRTY_APRSL_VL")
	public Float getPrprtyAprslVl() {
		return this.prprtyAprslVl;
	}

	public void setPrprtyAprslVl(Float prprtyAprslVl) {
		this.prprtyAprslVl = prprtyAprslVl;
	}

	@Column(name="QUALIFIED_MRTG_POINTS_AND_FEES")
	public Float getQualifiedMrtgPointsAndFees() {
		return this.qualifiedMrtgPointsAndFees;
	}

	public void setQualifiedMrtgPointsAndFees(Float qualifiedMrtgPointsAndFees) {
		this.qualifiedMrtgPointsAndFees = qualifiedMrtgPointsAndFees;
	}

	@Column(name="RATIO_FIX_TEI_ENDRS")
	public Float getRatioFixTeiEndrs() {
		return this.ratioFixTeiEndrs;
	}

	public void setRatioFixTeiEndrs(Float ratioFixTeiEndrs) {
		this.ratioFixTeiEndrs = ratioFixTeiEndrs;
	}

	@Column(name="RATIO_LOAN_TO_VL_NEW")
	public Float getRatioLoanToVlNew() {
		return this.ratioLoanToVlNew;
	}

	public void setRatioLoanToVlNew(Float ratioLoanToVlNew) {
		this.ratioLoanToVlNew = ratioLoanToVlNew;
	}

	@Column(name="RATIO_TOT_PMT_TO_TOT_INC_ENDRS")
	public Float getRatioTotPmtToTotIncEndrs() {
		return this.ratioTotPmtToTotIncEndrs;
	}

	public void setRatioTotPmtToTotIncEndrs(Float ratioTotPmtToTotIncEndrs) {
		this.ratioTotPmtToTotIncEndrs = ratioTotPmtToTotIncEndrs;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RCV_SALE_DT", length=23)
	public Date getRcvSaleDt() {
		return this.rcvSaleDt;
	}

	public void setRcvSaleDt(Date rcvSaleDt) {
		this.rcvSaleDt = rcvSaleDt;
	}

	@Column(name="REFINANCE_IND", length=1)
	public Character getRefinanceInd() {
		return this.refinanceInd;
	}

	public void setRefinanceInd(Character refinanceInd) {
		this.refinanceInd = refinanceInd;
	}

	@Column(name="REO_100_DOWN_PMT_PROG_IND", length=1)
	public Character getReo100DownPmtProgInd() {
		return this.reo100DownPmtProgInd;
	}

	public void setReo100DownPmtProgInd(Character reo100DownPmtProgInd) {
		this.reo100DownPmtProgInd = reo100DownPmtProgInd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REPAIR_COMPLETION_DATE", length=23)
	public Date getRepairCompletionDate() {
		return this.repairCompletionDate;
	}

	public void setRepairCompletionDate(Date repairCompletionDate) {
		this.repairCompletionDate = repairCompletionDate;
	}

	@Column(name="REVIEW_TYPE_CD", length=1)
	public Character getReviewTypeCd() {
		return this.reviewTypeCd;
	}

	public void setReviewTypeCd(Character reviewTypeCd) {
		this.reviewTypeCd = reviewTypeCd;
	}

	@Column(name="RFNC_CD", length=16)
	public String getRfncCd() {
		return this.rfncCd;
	}

	public void setRfncCd(String rfncCd) {
		this.rfncCd = rfncCd;
	}

	@Column(name="SALE_PRICE_GTR_ACQ_COST_IND", length=1)
	public Character getSalePriceGtrAcqCostInd() {
		return this.salePriceGtrAcqCostInd;
	}

	public void setSalePriceGtrAcqCostInd(Character salePriceGtrAcqCostInd) {
		this.salePriceGtrAcqCostInd = salePriceGtrAcqCostInd;
	}

	@Column(name="SALES_PRICE")
	public Float getSalesPrice() {
		return this.salesPrice;
	}

	public void setSalesPrice(Float salesPrice) {
		this.salesPrice = salesPrice;
	}

	@Column(name="SECONDARY_FINANCE_AMT")
	public Float getSecondaryFinanceAmt() {
		return this.secondaryFinanceAmt;
	}

	public void setSecondaryFinanceAmt(Float secondaryFinanceAmt) {
		this.secondaryFinanceAmt = secondaryFinanceAmt;
	}

	@Column(name="SECONDARY_FINANCE_EXISTS_IND", length=1)
	public Character getSecondaryFinanceExistsInd() {
		return this.secondaryFinanceExistsInd;
	}

	public void setSecondaryFinanceExistsInd(Character secondaryFinanceExistsInd) {
		this.secondaryFinanceExistsInd = secondaryFinanceExistsInd;
	}

	@Column(name="SELLER_CNTRBTN")
	public Float getSellerCntrbtn() {
		return this.sellerCntrbtn;
	}

	public void setSellerCntrbtn(Float sellerCntrbtn) {
		this.sellerCntrbtn = sellerCntrbtn;
	}

	@Column(name="SELLER_CNTRBTN_PCNT")
	public Float getSellerCntrbtnPcnt() {
		return this.sellerCntrbtnPcnt;
	}

	public void setSellerCntrbtnPcnt(Float sellerCntrbtnPcnt) {
		this.sellerCntrbtnPcnt = sellerCntrbtnPcnt;
	}

	@Column(name="SITE_TYPE", length=30)
	public String getSiteType() {
		return this.siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	@Column(name="SOA_CD", length=16)
	public String getSoaCd() {
		return this.soaCd;
	}

	public void setSoaCd(String soaCd) {
		this.soaCd = soaCd;
	}

	@Column(name="SPECIAL_PROGRAM", length=30)
	public String getSpecialProgram() {
		return this.specialProgram;
	}

	public void setSpecialProgram(String specialProgram) {
		this.specialProgram = specialProgram;
	}

	@Column(name="TAXES_INSRNC_FRST_YR_AMT")
	public Float getTaxesInsrncFrstYrAmt() {
		return this.taxesInsrncFrstYrAmt;
	}

	public void setTaxesInsrncFrstYrAmt(Float taxesInsrncFrstYrAmt) {
		this.taxesInsrncFrstYrAmt = taxesInsrncFrstYrAmt;
	}

	@Column(name="TEN_YR_RATE_LOCK_IND", length=1)
	public Character getTenYrRateLockInd() {
		return this.tenYrRateLockInd;
	}

	public void setTenYrRateLockInd(Character tenYrRateLockInd) {
		this.tenYrRateLockInd = tenYrRateLockInd;
	}

	@Column(name="TERM_TYP_CD", length=16)
	public String getTermTypCd() {
		return this.termTypCd;
	}

	public void setTermTypCd(String termTypCd) {
		this.termTypCd = termTypCd;
	}

	@Column(name="TOTAL_REQUIRED_FUNDS_TO_CLOSE")
	public Float getTotalRequiredFundsToClose() {
		return this.totalRequiredFundsToClose;
	}

	public void setTotalRequiredFundsToClose(Float totalRequiredFundsToClose) {
		this.totalRequiredFundsToClose = totalRequiredFundsToClose;
	}

	@Column(name="TOT_ASSETS_ENDRS")
	public Float getTotAssetsEndrs() {
		return this.totAssetsEndrs;
	}

	public void setTotAssetsEndrs(Float totAssetsEndrs) {
		this.totAssetsEndrs = totAssetsEndrs;
	}

	@Column(name="TOT_CLSNG_CSTS_ENDRS")
	public Float getTotClsngCstsEndrs() {
		return this.totClsngCstsEndrs;
	}

	public void setTotClsngCstsEndrs(Float totClsngCstsEndrs) {
		this.totClsngCstsEndrs = totClsngCstsEndrs;
	}

	@Column(name="TOT_FIXED_PYMT_ENDRS")
	public Float getTotFixedPymtEndrs() {
		return this.totFixedPymtEndrs;
	}

	public void setTotFixedPymtEndrs(Float totFixedPymtEndrs) {
		this.totFixedPymtEndrs = totFixedPymtEndrs;
	}

	@Column(name="TOT_MNTHLY_EFF_INCM")
	public Float getTotMnthlyEffIncm() {
		return this.totMnthlyEffIncm;
	}

	public void setTotMnthlyEffIncm(Float totMnthlyEffIncm) {
		this.totMnthlyEffIncm = totMnthlyEffIncm;
	}

	@Column(name="TOT_MNTHLY_MTG_PYMT_ENDRS")
	public Float getTotMnthlyMtgPymtEndrs() {
		return this.totMnthlyMtgPymtEndrs;
	}

	public void setTotMnthlyMtgPymtEndrs(Float totMnthlyMtgPymtEndrs) {
		this.totMnthlyMtgPymtEndrs = totMnthlyMtgPymtEndrs;
	}

	@Column(name="UNDERWRITING_METHOD", length=16)
	public String getUnderwritingMethod() {
		return this.underwritingMethod;
	}

	public void setUnderwritingMethod(String underwritingMethod) {
		this.underwritingMethod = underwritingMethod;
	}

	@Column(name="YEAR_BUILT", length=4)
	public String getYearBuilt() {
		return this.yearBuilt;
	}

	public void setYearBuilt(String yearBuilt) {
		this.yearBuilt = yearBuilt;
	}

	@Column(name="CREATED_BY", length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_TS", length=23)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	@Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_TS", length=23)
	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

}
