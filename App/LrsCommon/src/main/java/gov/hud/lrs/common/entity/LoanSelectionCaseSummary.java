package gov.hud.lrs.common.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="LOAN_SELECTION_CASE_SUMMARY")
@SuppressWarnings("serial")
public class LoanSelectionCaseSummary implements Serializable {

	private String selectionId;
	private SelectionRequest selectionRequest;
	private String caseNumber;

	private Float addtnl10pctIplUsageAmt;
	private Character addtnl10pctIplUsageInd;
	private String adpCode;
	private Float allowClsgCost;
	private String amortTypCd;
	private String aplctnMthd;
	private Date applicationDate;
	private String appraiserName;
	private Date aprslCmpltnDt;
	private String armAdjPrd;
	private Date armDt;
	private String armInd;
	private Float armIndxExpctdRt;
	private String armIndxInd;
	private Float armMrgnAmt;
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
	private Integer borr1YrsAtCurrJob;
	private String borr2Name;
	private String borr2Ssn;
	private Date borrBrthDt;
	private String borrCnslTyp;
	private String borrGender;
	private Float borrHsngExpEndrs;
	private Float borrHsngExpUw;
	private Float borrPaidClosingCosts;
	private Float borrReqdInvestToClose;
	private String borrTyp;
	private String bsmtCd;
	private Character buildingOnOwnLandInd;
	private String buyDwnInd;
	private Character cashoutRefiInd;
	private Date cctReinsDt;
	private Character cfEquivicalAssetsInd;
	private Character cfExpectedSsiInd;
	private Character cfHecmSufficientInd;
	private Character cfImputedIncomeInd;
	private Character cfNbsIncomeInd;
	private Character cfOtherIncomeInd;
	private Character cfOtIncomeInd;
	private Character cfPropPmtHistInd;
	private String claimType;
	private Date clsngDt;
	private Character cndInd;
	private String cndtnlCmmtmntRvwr;
	private Short coborr1FicoCnt;
	private Float combinedLoanToValuePct;
	private Character condoFeeCurrInd;
	private Character condoFeeDelinqInd;
	private String constCd;
	private String constStsCd;
	private Float corpAdvncAmt;
	private Integer countAus;
	private Date csEstabDt;
	private String csTyp;
	private Character currDflt90dayInd;
	private Date currDfltCycDt;
	private Integer currDfltEpisodeNbr;
	private String currDfltRsnCd;
	private String currDfltStsCd;
	private Date currDfltStsDt;
	private String currDfltStsSummaryCd;
	private Short currDlnqncyMnthsDlnqnt;
	private Character currentAtEndorseInd;
	private Float currMnthlyMip;
	private Date dateOfPriorSale;
	private String dcsnCdEndrs;
	private String dcsnCdUw;
	private Character defaultEpisodeExistsInd;
	private String dirLendingBranchInd;
	private Character disasterInd;
	private Short dpndntCnt;
	private Date dtAcq;
	private Character earlyClaimInd;
	private Character earlyDefaultInd;
	private Date effDateAprslUpdate;
	private Short endrsAprslDayDiff;
	private Character endrsBynd60DaysCloseInd;
	private Date endrsmntDt;
	private String endrsmntRvwPrsnnlId;
	private Date endrsPrcsngDt;
	private String energyEffMrtg;
	private Float escrowAmount;
	private Character escrwFlag;
	private Float expectedRate;
	private Character fctryFbrct;
	private Character femaFloodAreaInd;
	private String fhacAddrChg;
	private Integer ficoDecisionScoreEndrs;
	private Integer ficoDecisionScoreUw;
	private Character flippingCategory2Ind;
	private String fncngTyp;
	private Character frclsrInd;
	private Date frclsrStrtDt;
	private Date ftInEps3mnthDelqDt;
	private String geocdFlag;
	private Float giftLtr2Amt;
	private String giftLtr2Source;
	private String giftLtr2Tin;
	private Float giftLtrAmt;
	private String giftLtrSrc;
	private String giftLtrTin;
	private Float hecmAssets;
	private String hecmCounselCertNo;
	private Date hecmCounselDt;
	private Float hecmLiens;
	private Float hecmPrncplLmt;
	private String hldrMtgee5A43;
	private String hsngPgmCd;
	private Float hudReoRepairAmt;
	private Date indemDt;
	private Character indemStat;
	private Short indemTerm;
	private Character indemType;
	private Float initDisbursementLimit;
	private Float initFee;
	private String insrncStatusCd;
	private Character insurAppInTimeInd;
	private Float intRt;
	private Character invest2ndResidInd;
	private Date lastCaseQlfctnEvntDt;
	private Date lastServicingMrtgXferDt;
	private Float leCompoundRate;
	private Float leExpectedRate;
	private Float leProjectedAmt;
	private Float lesaFundingAmt;
	private String lesaFundingType;
	private Float leSetasideAmt;
	private Integer leTalcMonths;
	private String loanNbr;
	private String loanOfficer;
	private String loanOfficerNmls;
	private String loanPrps;
	private Character loanPrpsFrwdPymtInd;
	private Character loanPrpsImprvmntInd;
	private Character loanPrpsIncmInd;
	private Character loanPrpsInsrncInd;
	private Character loanPrpsLeisureInd;
	private Character loanPrpsMedclInd;
	private Character loanPrpsOthrInd;
	private Character loanPrpsTaxesInd;
	private String loanPrpsText;
	private String loanType;
	private String lossmitCd;
	private String ltvCat;
	private Float mandatoryObligBorrAmt;
	private Float mandatoryObligLendAmt;
	private Character manUwStretchRatioInd;
	private Character marriedToNbsInd;
	private Float maxClaimAmt;
	private Float maxRate;
	private Float meDebtAmt;
	private Float meOtherAmt;
	private Float meReAmt;
	private Float meTotalAmt;
	private Float miImputedAmt;
	private Float miOtherAmt;
	private Character mipFinancedInd;
	private String miscAusDcsnCd;
	private String miscAusInd;
	private Character miscEbndrInd;
	private String miscLndrInsrncInd;
	private Float miTotalAmt;
	private Float mndtryOblgtnsAmt;
	private Character mnfctrdHusngInd;
	private Float mnthlyPrncplAndIntrst;
	private Float mnthlySetAside;
	private Float mortExcldFncdMip;
	private Short nbrBdrm;
	private Float nbrBthrms;
	private String nbrhdCd;
	private Float nbrhdPctOwned;
	private Float nbrhdPrice;
	private Short nbrRms;
	private String nbsFirstMiddleLast;
	private Character nonOccupyingBorrInd;
	private Character nonperfLoanInd;
	private Date norIssRptDt;
	private Date norIssueDt;
	private Short numLivingUnits;
	private String ocpncyStsCd;
	private Date ocpncyStsDt;
	private String oldSrvcrMtgee;
	private Date oldstUnpdDt;
	private String orgntngMrtggeeInstStat;
	private String orgntngMtgee10Id;
	private String orgntngMtgee5;
	private String orgntngMtgeeNmlsId;
	private Float originationFee;
	private Float origMrtgAmt;
	private Character otherDebtCurrInd;
	private Character otherDebtLatePmtInd;
	private String paymentPlan;
	private Float pcCondoFeeAmt;
	private Float pcFloodInsAmt;
	private Float pcHazInsAmt;
	private Float pcOtherAmt;
	private Float pcReTaxAmt;
	private Float pcSetasideTotAmt;
	private Float pct1Fmly;
	private Float pcTotalAmt;
	private String pdStrmlnFlg;
	private Short pmtsBfrFrstMissdPmt;
	private String prcsngTyp;
	private String preClsngInd;
	private Short prequalCnt;
	private Date prequalDt;
	private String preRvwDcsn;
	private Character prevCompltSbdvsnInd;
	private Float priceExcldngClsngCosts;
	private Float priceIncldngClsngCosts;
	private Float priceOfPriorSale;
	private Character priorSaleRqrdInd;
	private Character priorSaleWithinLast3yrInd;
	private Float prncplRdctnAmt;
	private String prodType;
	private String progIdF17;
	private String propAddr1;
	private String propAddr2;
	private String propAddrCity;
	private String propAddrSt;
	private String propAddrZip;
	private String propTyp;
	private Float prprtyAprslVl;
	private String prprtyCnvrsnTyp;
	private Float prrCaseMxmmClmAmt;
	private String ptiCat;
	private Float qualifiedMrtgPointsAndFees;
	private Float ratioFixTeiEndrs;
	private Float ratioFixTeiUw;
	private Float ratioLoanToVlNew;
	private Float ratioOreTei;
	private Float ratioTotPmtToTotIncEndrs;
	private Float ratioTotPmtToTotIncUw;
	private Date rcvSaleDt;
	private Character reDebtCurrInd;
	private Character reDebtLatePmtInd;
	private Character refinanceInd;
	private String refiPrrCaseNbr;
	private Character reo100DownPmtProgInd;
	private Date repairCompletionDate;
	private Character reTaxCurrInd;
	private Character reTaxDelinqInd;
	private Character revolveDebtCurrInd;
	private Character revolveDebtLatePmtInd;
	private String rfncCd;
	private String rfncNextCaseNbr;
	private Short riFamilySize;
	private Float riShortfallAmt;
	private Float riStandardAmt;
	private Float riTotalAmt;
	private Float rprSetAside;
	private Character salePriceGtrAcqCostInd;
	private Float salesPrice;
	private String sbdvsnSpotLot;
	private String scndryFncSrc;
	private Float secondaryFinanceAmt;
	private Character secondaryFinanceExistsInd;
	private Float sellerCntrbtn;
	private Float sellerCntrbtnPcnt;
	private Character sendMicInd;
	private String siteType;
	private Character snglDsbrseLmpSumPmtOpt;
	private String soaCd;
	private String specialProgram;
	private String spnsrMrtggeeIdInst;
	private String spnsrMtgee10Id;
	private String srvcrMtgee5A43;
	private Character strmnlnRefiType;
	private Float taxesInsrncFrstYrAmt;
	private Character tenYrRateLockInd;
	private Integer term;
	private Character term15YrInd;
	private String termTypCd;
	private Float totAcqCosts;
	private Float totalRequiredFundsToClose;
	private Float totAnnEffIncm;
	private Float totAssetsEndrs;
	private Float totAssetsUw;
	private Float totClsngCstsEndrs;
	private Float totClsngCstsUw;
	private Float totFixedPymtEndrs;
	private Float totFixedPymtUw;
	private Float totMnthlyEffIncm;
	private Float totMnthlyMtgPymtEndrs;
	private Float totMnthlyMtgPymtUw;
	private String trnsmsnTyp;
	private String typOrgntngMtgee;
	private Float ufmipEarnedCurrMm;
	private Float ufmipFactor;
	private Float ufmipPdAmt;
	private Float ufmipPdCash;
	private String underwriterId;
	private String underwritingMethod;
	private Character undrsrvdInd;
	private String undrwrtingMtgee5;
	private Float unpdBal;
	private Float valPlusClsng;
	private String yearBuilt;
	private Short yrMoConstCmpltd;

	private String createdBy;
	private Date createdTs;

	private String updatedBy;
	private Date updatedTs;

	public LoanSelectionCaseSummary() {
	}

	@Id
    @GenericGenerator(name="generator", strategy="gov.hud.lrs.common.util.UuidStringIdentifierGenerator")
    @GeneratedValue(generator="generator")
    @Column(name="SELECTION_ID", unique=true, nullable=false, length=36)
	public String getSelectionId() {
		return this.selectionId;
	}

	public void setSelectionId(String selectionId) {
		this.selectionId = selectionId;
	}

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SELECTION_REQUEST_ID", nullable=false)
	public SelectionRequest getSelectionRequest() {
		return this.selectionRequest;
	}

	public void setSelectionRequest(SelectionRequest selectionRequest) {
		this.selectionRequest = selectionRequest;
	}

    @Column(name="CASE_NUMBER", nullable=false, length=11)
	public String getCaseNumber() {
		return this.caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

    @Column(name="ADDTNL_10PCT_IPL_USAGE_AMT")
	public Float getAddtnl10pctIplUsageAmt() {
		return this.addtnl10pctIplUsageAmt;
	}

	public void setAddtnl10pctIplUsageAmt(Float addtnl10pctIplUsageAmt) {
		this.addtnl10pctIplUsageAmt = addtnl10pctIplUsageAmt;
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

    @Column(name="ALLOW_CLSG_COST")
	public Float getAllowClsgCost() {
		return this.allowClsgCost;
	}

	public void setAllowClsgCost(Float allowClsgCost) {
		this.allowClsgCost = allowClsgCost;
	}

	@Column(name="AMORT_TYP_CD", length=16)
	public String getAmortTypCd() {
		return this.amortTypCd;
	}

	public void setAmortTypCd(String amortTypCd) {
		this.amortTypCd = amortTypCd;
	}

	@Column(name="APLCTN_MTHD", length=10)
	public String getAplctnMthd() {
		return this.aplctnMthd;
	}

	public void setAplctnMthd(String aplctnMthd) {
		this.aplctnMthd = aplctnMthd;
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

	@Column(name="ARM_ADJ_PRD", length=16)
	public String getArmAdjPrd() {
		return this.armAdjPrd;
	}

	public void setArmAdjPrd(String armAdjPrd) {
		this.armAdjPrd = armAdjPrd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ARM_DT", length=23)
	public Date getArmDt() {
		return this.armDt;
	}

	public void setArmDt(Date armDt) {
		this.armDt = armDt;
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

	@Column(name="ARM_INDX_IND", length=3)
	public String getArmIndxInd() {
		return this.armIndxInd;
	}

	public void setArmIndxInd(String armIndxInd) {
		this.armIndxInd = armIndxInd;
	}

	@Column(name="ARM_MRGN_AMT")
	public Float getArmMrgnAmt() {
		return this.armMrgnAmt;
	}

	public void setArmMrgnAmt(Float armMrgnAmt) {
		this.armMrgnAmt = armMrgnAmt;
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

	@Column(name="BORR_1_YRS_AT_CURR_JOB")
	public Integer getBorr1YrsAtCurrJob() {
		return this.borr1YrsAtCurrJob;
	}

	public void setBorr1YrsAtCurrJob(Integer borr1YrsAtCurrJob) {
		this.borr1YrsAtCurrJob = borr1YrsAtCurrJob;
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

	@Column(name="BORR_CNSL_TYP", length=16)
	public String getBorrCnslTyp() {
		return this.borrCnslTyp;
	}

	public void setBorrCnslTyp(String borrCnslTyp) {
		this.borrCnslTyp = borrCnslTyp;
	}

	@Column(name="BORR_GENDER", length=13)
	public String getBorrGender() {
		return this.borrGender;
	}

	public void setBorrGender(String borrGender) {
		this.borrGender = borrGender;
	}

	@Column(name="BORR_HSNG_EXP_ENDRS")
	public Float getBorrHsngExpEndrs() {
		return this.borrHsngExpEndrs;
	}

	public void setBorrHsngExpEndrs(Float borrHsngExpEndrs) {
		this.borrHsngExpEndrs = borrHsngExpEndrs;
	}

	@Column(name="BORR_HSNG_EXP_UW")
	public Float getBorrHsngExpUw() {
		return this.borrHsngExpUw;
	}

	public void setBorrHsngExpUw(Float borrHsngExpUw) {
		this.borrHsngExpUw = borrHsngExpUw;
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

	@Column(name="BSMT_CD", length=16)
	public String getBsmtCd() {
		return this.bsmtCd;
	}

	public void setBsmtCd(String bsmtCd) {
		this.bsmtCd = bsmtCd;
	}

	@Column(name="BUILDING_ON_OWN_LAND_IND", length=1)
	public Character getBuildingOnOwnLandInd() {
		return this.buildingOnOwnLandInd;
	}

	public void setBuildingOnOwnLandInd(Character buildingOnOwnLandInd) {
		this.buildingOnOwnLandInd = buildingOnOwnLandInd;
	}

	@Column(name="BUY_DWN_IND", length=3)
	public String getBuyDwnInd() {
		return this.buyDwnInd;
	}

	public void setBuyDwnInd(String buyDwnInd) {
		this.buyDwnInd = buyDwnInd;
	}

	@Column(name="CASHOUT_REFI_IND", length=1)
	public Character getCashoutRefiInd() {
		return this.cashoutRefiInd;
	}

	public void setCashoutRefiInd(Character cashoutRefiInd) {
		this.cashoutRefiInd = cashoutRefiInd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CCT_REINS_DT", length=23)
	public Date getCctReinsDt() {
		return this.cctReinsDt;
	}

	public void setCctReinsDt(Date cctReinsDt) {
		this.cctReinsDt = cctReinsDt;
	}

	@Column(name="CF_EQUIVICAL_ASSETS_IND", length=1)
	public Character getCfEquivicalAssetsInd() {
		return this.cfEquivicalAssetsInd;
	}

	public void setCfEquivicalAssetsInd(Character cfEquivicalAssetsInd) {
		this.cfEquivicalAssetsInd = cfEquivicalAssetsInd;
	}

	@Column(name="CF_EXPECTED_SSI_IND", length=1)
	public Character getCfExpectedSsiInd() {
		return this.cfExpectedSsiInd;
	}

	public void setCfExpectedSsiInd(Character cfExpectedSsiInd) {
		this.cfExpectedSsiInd = cfExpectedSsiInd;
	}

	@Column(name="CF_HECM_SUFFICIENT_IND", length=1)
	public Character getCfHecmSufficientInd() {
		return this.cfHecmSufficientInd;
	}

	public void setCfHecmSufficientInd(Character cfHecmSufficientInd) {
		this.cfHecmSufficientInd = cfHecmSufficientInd;
	}

	@Column(name="CF_IMPUTED_INCOME_IND", length=1)
	public Character getCfImputedIncomeInd() {
		return this.cfImputedIncomeInd;
	}

	public void setCfImputedIncomeInd(Character cfImputedIncomeInd) {
		this.cfImputedIncomeInd = cfImputedIncomeInd;
	}

	@Column(name="CF_NBS_INCOME_IND", length=1)
	public Character getCfNbsIncomeInd() {
		return this.cfNbsIncomeInd;
	}

	public void setCfNbsIncomeInd(Character cfNbsIncomeInd) {
		this.cfNbsIncomeInd = cfNbsIncomeInd;
	}

	@Column(name="CF_OTHER_INCOME_IND", length=1)
	public Character getCfOtherIncomeInd() {
		return this.cfOtherIncomeInd;
	}

	public void setCfOtherIncomeInd(Character cfOtherIncomeInd) {
		this.cfOtherIncomeInd = cfOtherIncomeInd;
	}

	@Column(name="CF_OT_INCOME_IND", length=1)
	public Character getCfOtIncomeInd() {
		return this.cfOtIncomeInd;
	}

	public void setCfOtIncomeInd(Character cfOtIncomeInd) {
		this.cfOtIncomeInd = cfOtIncomeInd;
	}

	@Column(name="CF_PROP_PMT_HIST_IND", length=1)
	public Character getCfPropPmtHistInd() {
		return this.cfPropPmtHistInd;
	}

	public void setCfPropPmtHistInd(Character cfPropPmtHistInd) {
		this.cfPropPmtHistInd = cfPropPmtHistInd;
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

	@Column(name="CND_IND", length=1)
	public Character getCndInd() {
		return this.cndInd;
	}

	public void setCndInd(Character cndInd) {
		this.cndInd = cndInd;
	}

	@Column(name="CNDTNL_CMMTMNT_RVWR", length=23)
	public String getCndtnlCmmtmntRvwr() {
		return this.cndtnlCmmtmntRvwr;
	}

	public void setCndtnlCmmtmntRvwr(String cndtnlCmmtmntRvwr) {
		this.cndtnlCmmtmntRvwr = cndtnlCmmtmntRvwr;
	}

	@Column(name="COBORR_1_FICO_CNT")
	public Short getCoborr1FicoCnt() {
		return this.coborr1FicoCnt;
	}

	public void setCoborr1FicoCnt(Short coborr1FicoCnt) {
		this.coborr1FicoCnt = coborr1FicoCnt;
	}

	@Column(name="COMBINED_LOAN_TO_VALUE_PCT")
	public Float getCombinedLoanToValuePct() {
		return this.combinedLoanToValuePct;
	}

	public void setCombinedLoanToValuePct(Float combinedLoanToValuePct) {
		this.combinedLoanToValuePct = combinedLoanToValuePct;
	}

	@Column(name="CONDO_FEE_CURR_IND", length=1)
	public Character getCondoFeeCurrInd() {
		return this.condoFeeCurrInd;
	}

	public void setCondoFeeCurrInd(Character condoFeeCurrInd) {
		this.condoFeeCurrInd = condoFeeCurrInd;
	}

	@Column(name="CONDO_FEE_DELINQ_IND", length=1)
	public Character getCondoFeeDelinqInd() {
		return this.condoFeeDelinqInd;
	}

	public void setCondoFeeDelinqInd(Character condoFeeDelinqInd) {
		this.condoFeeDelinqInd = condoFeeDelinqInd;
	}

	@Column(name="CONST_CD", length=16)
	public String getConstCd() {
		return this.constCd;
	}

	public void setConstCd(String constCd) {
		this.constCd = constCd;
	}

	@Column(name="CONST_STS_CD", length=16)
	public String getConstStsCd() {
		return this.constStsCd;
	}

	public void setConstStsCd(String constStsCd) {
		this.constStsCd = constStsCd;
	}

	@Column(name="CORP_ADVNC_AMT")
	public Float getCorpAdvncAmt() {
		return this.corpAdvncAmt;
	}

	public void setCorpAdvncAmt(Float corpAdvncAmt) {
		this.corpAdvncAmt = corpAdvncAmt;
	}

	@Column(name="COUNT_AUS")
	public Integer getCountAus() {
		return this.countAus;
	}

	public void setCountAus(Integer countAus) {
		this.countAus = countAus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CS_ESTAB_DT", length=23)
	public Date getCsEstabDt() {
		return this.csEstabDt;
	}

	public void setCsEstabDt(Date csEstabDt) {
		this.csEstabDt = csEstabDt;
	}

	@Column(name="CS_TYP", length=16)
	public String getCsTyp() {
		return this.csTyp;
	}

	public void setCsTyp(String csTyp) {
		this.csTyp = csTyp;
	}

	@Column(name="CURR_DFLT_90DAY_IND", length=1)
	public Character getCurrDflt90dayInd() {
		return this.currDflt90dayInd;
	}

	public void setCurrDflt90dayInd(Character currDflt90dayInd) {
		this.currDflt90dayInd = currDflt90dayInd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CURR_DFLT_CYC_DT", length=23)
	public Date getCurrDfltCycDt() {
		return this.currDfltCycDt;
	}

	public void setCurrDfltCycDt(Date currDfltCycDt) {
		this.currDfltCycDt = currDfltCycDt;
	}

	@Column(name="CURR_DFLT_EPISODE_NBR")
	public Integer getCurrDfltEpisodeNbr() {
		return this.currDfltEpisodeNbr;
	}

	public void setCurrDfltEpisodeNbr(Integer currDfltEpisodeNbr) {
		this.currDfltEpisodeNbr = currDfltEpisodeNbr;
	}

	@Column(name="CURR_DFLT_RSN_CD", length=16)
	public String getCurrDfltRsnCd() {
		return this.currDfltRsnCd;
	}

	public void setCurrDfltRsnCd(String currDfltRsnCd) {
		this.currDfltRsnCd = currDfltRsnCd;
	}

	@Column(name="CURR_DFLT_STS_CD", length=16)
	public String getCurrDfltStsCd() {
		return this.currDfltStsCd;
	}

	public void setCurrDfltStsCd(String currDfltStsCd) {
		this.currDfltStsCd = currDfltStsCd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CURR_DFLT_STS_DT", length=23)
	public Date getCurrDfltStsDt() {
		return this.currDfltStsDt;
	}

	public void setCurrDfltStsDt(Date currDfltStsDt) {
		this.currDfltStsDt = currDfltStsDt;
	}

	@Column(name="CURR_DFLT_STS_SUMMARY_CD", length=16)
	public String getCurrDfltStsSummaryCd() {
		return this.currDfltStsSummaryCd;
	}

	public void setCurrDfltStsSummaryCd(String currDfltStsSummaryCd) {
		this.currDfltStsSummaryCd = currDfltStsSummaryCd;
	}

	@Column(name="CURR_DLNQNCY_MNTHS_DLNQNT")
	public Short getCurrDlnqncyMnthsDlnqnt() {
		return this.currDlnqncyMnthsDlnqnt;
	}

	public void setCurrDlnqncyMnthsDlnqnt(Short currDlnqncyMnthsDlnqnt) {
		this.currDlnqncyMnthsDlnqnt = currDlnqncyMnthsDlnqnt;
	}

	@Column(name="CURRENT_AT_ENDORSE_IND", length=1)
	public Character getCurrentAtEndorseInd() {
		return this.currentAtEndorseInd;
	}

	public void setCurrentAtEndorseInd(Character currentAtEndorseInd) {
		this.currentAtEndorseInd = currentAtEndorseInd;
	}

	@Column(name="CURR_MNTHLY_MIP")
	public Float getCurrMnthlyMip() {
		return this.currMnthlyMip;
	}

	public void setCurrMnthlyMip(Float currMnthlyMip) {
		this.currMnthlyMip = currMnthlyMip;
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

	@Column(name="DCSN_CD_UW", length=16)
	public String getDcsnCdUw() {
		return this.dcsnCdUw;
	}

	public void setDcsnCdUw(String dcsnCdUw) {
		this.dcsnCdUw = dcsnCdUw;
	}

	@Column(name="DEFAULT_EPISODE_EXISTS_IND", length=1)
	public Character getDefaultEpisodeExistsInd() {
		return this.defaultEpisodeExistsInd;
	}

	public void setDefaultEpisodeExistsInd(Character defaultEpisodeExistsInd) {
		this.defaultEpisodeExistsInd = defaultEpisodeExistsInd;
	}

	@Column(name="DIR_LENDING_BRANCH_IND", length=16)
	public String getDirLendingBranchInd() {
		return this.dirLendingBranchInd;
	}

	public void setDirLendingBranchInd(String dirLendingBranchInd) {
		this.dirLendingBranchInd = dirLendingBranchInd;
	}

	@Column(name="DISASTER_IND", length=1)
	public Character getDisasterInd() {
		return this.disasterInd;
	}

	public void setDisasterInd(Character disasterInd) {
		this.disasterInd = disasterInd;
	}

	@Column(name="DPNDNT_CNT", scale=0)
	public Short getDpndntCnt() {
		return this.dpndntCnt;
	}

	public void setDpndntCnt(Short dpndntCnt) {
		this.dpndntCnt = dpndntCnt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_ACQ", length=23)
	public Date getDtAcq() {
		return this.dtAcq;
	}

	public void setDtAcq(Date dtAcq) {
		this.dtAcq = dtAcq;
	}

	@Column(name="EARLY_CLAIM_IND", length=1)
	public Character getEarlyClaimInd() {
		return this.earlyClaimInd;
	}

	public void setEarlyClaimInd(Character earlyClaimInd) {
		this.earlyClaimInd = earlyClaimInd;
	}

	@Column(name="EARLY_DEFAULT_IND", length=1)
	public Character getEarlyDefaultInd() {
		return this.earlyDefaultInd;
	}

	public void setEarlyDefaultInd(Character earlyDefaultInd) {
		this.earlyDefaultInd = earlyDefaultInd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EFF_DATE_APRSL_UPDATE", length=23)
	public Date getEffDateAprslUpdate() {
		return this.effDateAprslUpdate;
	}

	public void setEffDateAprslUpdate(Date effDateAprslUpdate) {
		this.effDateAprslUpdate = effDateAprslUpdate;
	}

	@Column(name="ENDRS_APRSL_DAY_DIFF")
	public Short getEndrsAprslDayDiff() {
		return this.endrsAprslDayDiff;
	}

	public void setEndrsAprslDayDiff(Short endrsAprslDayDiff) {
		this.endrsAprslDayDiff = endrsAprslDayDiff;
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

	@Column(name="ENDRSMNT_RVW_PRSNNL_ID", length=32)
	public String getEndrsmntRvwPrsnnlId() {
		return this.endrsmntRvwPrsnnlId;
	}

	public void setEndrsmntRvwPrsnnlId(String endrsmntRvwPrsnnlId) {
		this.endrsmntRvwPrsnnlId = endrsmntRvwPrsnnlId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ENDRS_PRCSNG_DT", length=23)
	public Date getEndrsPrcsngDt() {
		return this.endrsPrcsngDt;
	}

	public void setEndrsPrcsngDt(Date endrsPrcsngDt) {
		this.endrsPrcsngDt = endrsPrcsngDt;
	}

	@Column(name="ENERGY_EFF_MRTG", length=3)
	public String getEnergyEffMrtg() {
		return this.energyEffMrtg;
	}

	public void setEnergyEffMrtg(String energyEffMrtg) {
		this.energyEffMrtg = energyEffMrtg;
	}

	@Column(name="ESCROW_AMOUNT")
	public Float getEscrowAmount() {
		return this.escrowAmount;
	}

	public void setEscrowAmount(Float escrowAmount) {
		this.escrowAmount = escrowAmount;
	}

	@Column(name="ESCRW_FLAG", length=1)
	public Character getEscrwFlag() {
		return this.escrwFlag;
	}

	public void setEscrwFlag(Character escrwFlag) {
		this.escrwFlag = escrwFlag;
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

	@Column(name="FEMA_FLOOD_AREA_IND", length=1)
	public Character getFemaFloodAreaInd() {
		return this.femaFloodAreaInd;
	}

	public void setFemaFloodAreaInd(Character femaFloodAreaInd) {
		this.femaFloodAreaInd = femaFloodAreaInd;
	}

	@Column(name="FHAC_ADDR_CHG", length=3)
	public String getFhacAddrChg() {
		return this.fhacAddrChg;
	}

	public void setFhacAddrChg(String fhacAddrChg) {
		this.fhacAddrChg = fhacAddrChg;
	}

	@Column(name="FICO_DECISION_SCORE_ENDRS")
	public Integer getFicoDecisionScoreEndrs() {
		return this.ficoDecisionScoreEndrs;
	}

	public void setFicoDecisionScoreEndrs(Integer ficoDecisionScoreEndrs) {
		this.ficoDecisionScoreEndrs = ficoDecisionScoreEndrs;
	}

	@Column(name="FICO_DECISION_SCORE_UW")
	public Integer getFicoDecisionScoreUw() {
		return this.ficoDecisionScoreUw;
	}

	public void setFicoDecisionScoreUw(Integer ficoDecisionScoreUw) {
		this.ficoDecisionScoreUw = ficoDecisionScoreUw;
	}

	@Column(name="FLIPPING_CATEGORY_2_IND", length=1)
	public Character getFlippingCategory2Ind() {
		return this.flippingCategory2Ind;
	}

	public void setFlippingCategory2Ind(Character flippingCategory2Ind) {
		this.flippingCategory2Ind = flippingCategory2Ind;
	}

	@Column(name="FNCNG_TYP", length=16)
	public String getFncngTyp() {
		return this.fncngTyp;
	}

	public void setFncngTyp(String fncngTyp) {
		this.fncngTyp = fncngTyp;
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

	@Column(name="GEOCD_FLAG", length=3)
	public String getGeocdFlag() {
		return this.geocdFlag;
	}

	public void setGeocdFlag(String geocdFlag) {
		this.geocdFlag = geocdFlag;
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

	@Column(name="GIFT_LTR_SRC", length=30)
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

	@Column(name="HECM_ASSETS")
	public Float getHecmAssets() {
		return this.hecmAssets;
	}

	public void setHecmAssets(Float hecmAssets) {
		this.hecmAssets = hecmAssets;
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

	@Column(name="HECM_LIENS")
	public Float getHecmLiens() {
		return this.hecmLiens;
	}

	public void setHecmLiens(Float hecmLiens) {
		this.hecmLiens = hecmLiens;
	}

	@Column(name="HECM_PRNCPL_LMT")
	public Float getHecmPrncplLmt() {
		return this.hecmPrncplLmt;
	}

	public void setHecmPrncplLmt(Float hecmPrncplLmt) {
		this.hecmPrncplLmt = hecmPrncplLmt;
	}

	@Column(name="HLDR_MTGEE5_A43", length=15)
	public String getHldrMtgee5A43() {
		return this.hldrMtgee5A43;
	}

	public void setHldrMtgee5A43(String hldrMtgee5A43) {
		this.hldrMtgee5A43 = hldrMtgee5A43;
	}

	@Column(name="HSNG_PGM_CD", length=16)
	public String getHsngPgmCd() {
		return this.hsngPgmCd;
	}

	public void setHsngPgmCd(String hsngPgmCd) {
		this.hsngPgmCd = hsngPgmCd;
	}

	@Column(name="HUD_REO_REPAIR_AMT")
	public Float getHudReoRepairAmt() {
		return this.hudReoRepairAmt;
	}

	public void setHudReoRepairAmt(Float hudReoRepairAmt) {
		this.hudReoRepairAmt = hudReoRepairAmt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INDEM_DT", length=23)
	public Date getIndemDt() {
		return this.indemDt;
	}

	public void setIndemDt(Date indemDt) {
		this.indemDt = indemDt;
	}

	@Column(name="INDEM_STAT", length=1)
	public Character getIndemStat() {
		return this.indemStat;
	}

	public void setIndemStat(Character indemStat) {
		this.indemStat = indemStat;
	}

	@Column(name="INDEM_TERM")
	public Short getIndemTerm() {
		return this.indemTerm;
	}

	public void setIndemTerm(Short indemTerm) {
		this.indemTerm = indemTerm;
	}

	@Column(name="INDEM_TYPE", length=1)
	public Character getIndemType() {
		return this.indemType;
	}

	public void setIndemType(Character indemType) {
		this.indemType = indemType;
	}

	@Column(name="INIT_DISBURSEMENT_LIMIT")
	public Float getInitDisbursementLimit() {
		return this.initDisbursementLimit;
	}

	public void setInitDisbursementLimit(Float initDisbursementLimit) {
		this.initDisbursementLimit = initDisbursementLimit;
	}

	@Column(name="INIT_FEE")
	public Float getInitFee() {
		return this.initFee;
	}

	public void setInitFee(Float initFee) {
		this.initFee = initFee;
	}

	@Column(name="INSRNC_STATUS_CD", length=16)
	public String getInsrncStatusCd() {
		return this.insrncStatusCd;
	}

	public void setInsrncStatusCd(String insrncStatusCd) {
		this.insrncStatusCd = insrncStatusCd;
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
	@Column(name="LAST_CASE_QLFCTN_EVNT_DT", length=23)
	public Date getLastCaseQlfctnEvntDt() {
		return this.lastCaseQlfctnEvntDt;
	}

	public void setLastCaseQlfctnEvntDt(Date lastCaseQlfctnEvntDt) {
		this.lastCaseQlfctnEvntDt = lastCaseQlfctnEvntDt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_SERVICING_MRTG_XFER_DT", length=23)
	public Date getLastServicingMrtgXferDt() {
		return this.lastServicingMrtgXferDt;
	}

	public void setLastServicingMrtgXferDt(Date lastServicingMrtgXferDt) {
		this.lastServicingMrtgXferDt = lastServicingMrtgXferDt;
	}

	@Column(name="LE_COMPOUND_RATE")
	public Float getLeCompoundRate() {
		return this.leCompoundRate;
	}

	public void setLeCompoundRate(Float leCompoundRate) {
		this.leCompoundRate = leCompoundRate;
	}

	@Column(name="LE_EXPECTED_RATE")
	public Float getLeExpectedRate() {
		return this.leExpectedRate;
	}

	public void setLeExpectedRate(Float leExpectedRate) {
		this.leExpectedRate = leExpectedRate;
	}

	@Column(name="LE_PROJECTED_AMT")
	public Float getLeProjectedAmt() {
		return this.leProjectedAmt;
	}

	public void setLeProjectedAmt(Float leProjectedAmt) {
		this.leProjectedAmt = leProjectedAmt;
	}

	@Column(name="LESA_FUNDING_AMT")
	public Float getLesaFundingAmt() {
		return this.lesaFundingAmt;
	}

	public void setLesaFundingAmt(Float lesaFundingAmt) {
		this.lesaFundingAmt = lesaFundingAmt;
	}

	@Column(name="LESA_FUNDING_TYPE", length=16)
	public String getLesaFundingType() {
		return this.lesaFundingType;
	}

	public void setLesaFundingType(String lesaFundingType) {
		this.lesaFundingType = lesaFundingType;
	}

	@Column(name="LE_SETASIDE_AMT")
	public Float getLeSetasideAmt() {
		return this.leSetasideAmt;
	}

	public void setLeSetasideAmt(Float leSetasideAmt) {
		this.leSetasideAmt = leSetasideAmt;
	}

	@Column(name="LE_TALC_MONTHS")
	public Integer getLeTalcMonths() {
		return this.leTalcMonths;
	}

	public void setLeTalcMonths(Integer leTalcMonths) {
		this.leTalcMonths = leTalcMonths;
	}

	@Column(name="LOAN_NBR", length=30)
	public String getLoanNbr() {
		return this.loanNbr;
	}

	public void setLoanNbr(String loanNbr) {
		this.loanNbr = loanNbr;
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

	@Column(name="LOAN_PRPS_FRWD_PYMT_IND", length=1)
	public Character getLoanPrpsFrwdPymtInd() {
		return this.loanPrpsFrwdPymtInd;
	}

	public void setLoanPrpsFrwdPymtInd(Character loanPrpsFrwdPymtInd) {
		this.loanPrpsFrwdPymtInd = loanPrpsFrwdPymtInd;
	}

	@Column(name="LOAN_PRPS_IMPRVMNT_IND", length=1)
	public Character getLoanPrpsImprvmntInd() {
		return this.loanPrpsImprvmntInd;
	}

	public void setLoanPrpsImprvmntInd(Character loanPrpsImprvmntInd) {
		this.loanPrpsImprvmntInd = loanPrpsImprvmntInd;
	}

	@Column(name="LOAN_PRPS_INCM_IND", length=1)
	public Character getLoanPrpsIncmInd() {
		return this.loanPrpsIncmInd;
	}

	public void setLoanPrpsIncmInd(Character loanPrpsIncmInd) {
		this.loanPrpsIncmInd = loanPrpsIncmInd;
	}

	@Column(name="LOAN_PRPS_INSRNC_IND", length=1)
	public Character getLoanPrpsInsrncInd() {
		return this.loanPrpsInsrncInd;
	}

	public void setLoanPrpsInsrncInd(Character loanPrpsInsrncInd) {
		this.loanPrpsInsrncInd = loanPrpsInsrncInd;
	}

	@Column(name="LOAN_PRPS_LEISURE_IND", length=1)
	public Character getLoanPrpsLeisureInd() {
		return this.loanPrpsLeisureInd;
	}

	public void setLoanPrpsLeisureInd(Character loanPrpsLeisureInd) {
		this.loanPrpsLeisureInd = loanPrpsLeisureInd;
	}

	@Column(name="LOAN_PRPS_MEDCL_IND", length=1)
	public Character getLoanPrpsMedclInd() {
		return this.loanPrpsMedclInd;
	}

	public void setLoanPrpsMedclInd(Character loanPrpsMedclInd) {
		this.loanPrpsMedclInd = loanPrpsMedclInd;
	}

	@Column(name="LOAN_PRPS_OTHR_IND", length=1)
	public Character getLoanPrpsOthrInd() {
		return this.loanPrpsOthrInd;
	}

	public void setLoanPrpsOthrInd(Character loanPrpsOthrInd) {
		this.loanPrpsOthrInd = loanPrpsOthrInd;
	}

	@Column(name="LOAN_PRPS_TAXES_IND", length=1)
	public Character getLoanPrpsTaxesInd() {
		return this.loanPrpsTaxesInd;
	}

	public void setLoanPrpsTaxesInd(Character loanPrpsTaxesInd) {
		this.loanPrpsTaxesInd = loanPrpsTaxesInd;
	}

	@Column(name="LOAN_PRPS_TEXT", length=100)
	public String getLoanPrpsText() {
		return this.loanPrpsText;
	}

	public void setLoanPrpsText(String loanPrpsText) {
		this.loanPrpsText = loanPrpsText;
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

	@Column(name="LTV_CAT", length=16)
	public String getLtvCat() {
		return this.ltvCat;
	}

	public void setLtvCat(String ltvCat) {
		this.ltvCat = ltvCat;
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

	@Column(name="ME_DEBT_AMT")
	public Float getMeDebtAmt() {
		return this.meDebtAmt;
	}

	public void setMeDebtAmt(Float meDebtAmt) {
		this.meDebtAmt = meDebtAmt;
	}

	@Column(name="ME_OTHER_AMT")
	public Float getMeOtherAmt() {
		return this.meOtherAmt;
	}

	public void setMeOtherAmt(Float meOtherAmt) {
		this.meOtherAmt = meOtherAmt;
	}

	@Column(name="ME_RE_AMT")
	public Float getMeReAmt() {
		return this.meReAmt;
	}

	public void setMeReAmt(Float meReAmt) {
		this.meReAmt = meReAmt;
	}

	@Column(name="ME_TOTAL_AMT")
	public Float getMeTotalAmt() {
		return this.meTotalAmt;
	}

	public void setMeTotalAmt(Float meTotalAmt) {
		this.meTotalAmt = meTotalAmt;
	}

	@Column(name="MI_IMPUTED_AMT")
	public Float getMiImputedAmt() {
		return this.miImputedAmt;
	}

	public void setMiImputedAmt(Float miImputedAmt) {
		this.miImputedAmt = miImputedAmt;
	}

	@Column(name="MI_OTHER_AMT")
	public Float getMiOtherAmt() {
		return this.miOtherAmt;
	}

	public void setMiOtherAmt(Float miOtherAmt) {
		this.miOtherAmt = miOtherAmt;
	}

	@Column(name="MIP_FINANCED_IND", length=1)
	public Character getMipFinancedInd() {
		return this.mipFinancedInd;
	}

	public void setMipFinancedInd(Character mipFinancedInd) {
		this.mipFinancedInd = mipFinancedInd;
	}

	@Column(name="MISC_AUS_DCSN_CD", length=16)
	public String getMiscAusDcsnCd() {
		return this.miscAusDcsnCd;
	}

	public void setMiscAusDcsnCd(String miscAusDcsnCd) {
		this.miscAusDcsnCd = miscAusDcsnCd;
	}

	@Column(name="MISC_AUS_IND", length=3)
	public String getMiscAusInd() {
		return this.miscAusInd;
	}

	public void setMiscAusInd(String miscAusInd) {
		this.miscAusInd = miscAusInd;
	}

	@Column(name="MISC_EBNDR_IND", length=1)
	public Character getMiscEbndrInd() {
		return this.miscEbndrInd;
	}

	public void setMiscEbndrInd(Character miscEbndrInd) {
		this.miscEbndrInd = miscEbndrInd;
	}

	@Column(name="MISC_LNDR_INSRNC_IND", length=13)
	public String getMiscLndrInsrncInd() {
		return this.miscLndrInsrncInd;
	}

	public void setMiscLndrInsrncInd(String miscLndrInsrncInd) {
		this.miscLndrInsrncInd = miscLndrInsrncInd;
	}

	@Column(name="MI_TOTAL_AMT")
	public Float getMiTotalAmt() {
		return this.miTotalAmt;
	}

	public void setMiTotalAmt(Float miTotalAmt) {
		this.miTotalAmt = miTotalAmt;
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

	@Column(name="MNTHLY_PRNCPL_AND_INTRST")
	public Float getMnthlyPrncplAndIntrst() {
		return this.mnthlyPrncplAndIntrst;
	}

	public void setMnthlyPrncplAndIntrst(Float mnthlyPrncplAndIntrst) {
		this.mnthlyPrncplAndIntrst = mnthlyPrncplAndIntrst;
	}

	@Column(name="MNTHLY_SET_ASIDE")
	public Float getMnthlySetAside() {
		return this.mnthlySetAside;
	}

	public void setMnthlySetAside(Float mnthlySetAside) {
		this.mnthlySetAside = mnthlySetAside;
	}

	@Column(name="MORT_EXCLD_FNCD_MIP")
	public Float getMortExcldFncdMip() {
		return this.mortExcldFncdMip;
	}

	public void setMortExcldFncdMip(Float mortExcldFncdMip) {
		this.mortExcldFncdMip = mortExcldFncdMip;
	}

	@Column(name="NBR_BDRM", scale=0)
	public Short getNbrBdrm() {
		return this.nbrBdrm;
	}

	public void setNbrBdrm(Short nbrBdrm) {
		this.nbrBdrm = nbrBdrm;
	}

	@Column(name="NBR_BTHRMS", scale=1)
	public Float getNbrBthrms() {
		return this.nbrBthrms;
	}

	public void setNbrBthrms(Float nbrBthrms) {
		this.nbrBthrms = nbrBthrms;
	}

	@Column(name="NBRHD_CD", length=16)
	public String getNbrhdCd() {
		return this.nbrhdCd;
	}

	public void setNbrhdCd(String nbrhdCd) {
		this.nbrhdCd = nbrhdCd;
	}

	@Column(name="NBRHD_PCT_OWNED")
	public Float getNbrhdPctOwned() {
		return this.nbrhdPctOwned;
	}

	public void setNbrhdPctOwned(Float nbrhdPctOwned) {
		this.nbrhdPctOwned = nbrhdPctOwned;
	}

	@Column(name="NBRHD_PRICE")
	public Float getNbrhdPrice() {
		return this.nbrhdPrice;
	}

	public void setNbrhdPrice(Float nbrhdPrice) {
		this.nbrhdPrice = nbrhdPrice;
	}

	@Column(name="NBR_RMS", scale=0)
	public Short getNbrRms() {
		return this.nbrRms;
	}

	public void setNbrRms(Short nbrRms) {
		this.nbrRms = nbrRms;
	}

	@Column(name="NBS_FIRST_MIDDLE_LAST", length=150)
	public String getNbsFirstMiddleLast() {
		return this.nbsFirstMiddleLast;
	}

	public void setNbsFirstMiddleLast(String nbsFirstMiddleLast) {
		this.nbsFirstMiddleLast = nbsFirstMiddleLast;
	}

	@Column(name="NON_OCCUPYING_BORR_IND", length=1)
	public Character getNonOccupyingBorrInd() {
		return this.nonOccupyingBorrInd;
	}

	public void setNonOccupyingBorrInd(Character nonOccupyingBorrInd) {
		this.nonOccupyingBorrInd = nonOccupyingBorrInd;
	}

	@Column(name="NONPERF_LOAN_IND", length=1)
	public Character getNonperfLoanInd() {
		return this.nonperfLoanInd;
	}

	public void setNonperfLoanInd(Character nonperfLoanInd) {
		this.nonperfLoanInd = nonperfLoanInd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="NOR_ISS_RPT_DT", length=23)
	public Date getNorIssRptDt() {
		return this.norIssRptDt;
	}

	public void setNorIssRptDt(Date norIssRptDt) {
		this.norIssRptDt = norIssRptDt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="NOR_ISSUE_DT", length=23)
	public Date getNorIssueDt() {
		return this.norIssueDt;
	}

	public void setNorIssueDt(Date norIssueDt) {
		this.norIssueDt = norIssueDt;
	}

	@Column(name="NUM_LIVING_UNITS", scale=0)
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
	@Column(name="OCPNCY_STS_DT", length=23)
	public Date getOcpncyStsDt() {
		return this.ocpncyStsDt;
	}

	public void setOcpncyStsDt(Date ocpncyStsDt) {
		this.ocpncyStsDt = ocpncyStsDt;
	}

	@Column(name="OLD_SRVCR_MTGEE", length=5)
	public String getOldSrvcrMtgee() {
		return this.oldSrvcrMtgee;
	}

	public void setOldSrvcrMtgee(String oldSrvcrMtgee) {
		this.oldSrvcrMtgee = oldSrvcrMtgee;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="OLDST_UNPD_DT", length=23)
	public Date getOldstUnpdDt() {
		return this.oldstUnpdDt;
	}

	public void setOldstUnpdDt(Date oldstUnpdDt) {
		this.oldstUnpdDt = oldstUnpdDt;
	}

	@Column(name="ORGNTNG_MRTGGEE_INST_STAT", length=30)
	public String getOrgntngMrtggeeInstStat() {
		return this.orgntngMrtggeeInstStat;
	}

	public void setOrgntngMrtggeeInstStat(String orgntngMrtggeeInstStat) {
		this.orgntngMrtggeeInstStat = orgntngMrtggeeInstStat;
	}

	@Column(name="ORGNTNG_MTGEE10_ID", length=10)
	public String getOrgntngMtgee10Id() {
		return this.orgntngMtgee10Id;
	}

	public void setOrgntngMtgee10Id(String orgntngMtgee10Id) {
		this.orgntngMtgee10Id = orgntngMtgee10Id;
	}

	@Column(name="ORGNTNG_MTGEE5", length=5)
	public String getOrgntngMtgee5() {
		return this.orgntngMtgee5;
	}

	public void setOrgntngMtgee5(String orgntngMtgee5) {
		this.orgntngMtgee5 = orgntngMtgee5;
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

	@Column(name="OTHER_DEBT_CURR_IND", length=1)
	public Character getOtherDebtCurrInd() {
		return this.otherDebtCurrInd;
	}

	public void setOtherDebtCurrInd(Character otherDebtCurrInd) {
		this.otherDebtCurrInd = otherDebtCurrInd;
	}

	@Column(name="OTHER_DEBT_LATE_PMT_IND", length=1)
	public Character getOtherDebtLatePmtInd() {
		return this.otherDebtLatePmtInd;
	}

	public void setOtherDebtLatePmtInd(Character otherDebtLatePmtInd) {
		this.otherDebtLatePmtInd = otherDebtLatePmtInd;
	}

	@Column(name="PAYMENT_PLAN", length=16)
	public String getPaymentPlan() {
		return this.paymentPlan;
	}

	public void setPaymentPlan(String paymentPlan) {
		this.paymentPlan = paymentPlan;
	}

	@Column(name="PC_CONDO_FEE_AMT")
	public Float getPcCondoFeeAmt() {
		return this.pcCondoFeeAmt;
	}

	public void setPcCondoFeeAmt(Float pcCondoFeeAmt) {
		this.pcCondoFeeAmt = pcCondoFeeAmt;
	}

	@Column(name="PC_FLOOD_INS_AMT")
	public Float getPcFloodInsAmt() {
		return this.pcFloodInsAmt;
	}

	public void setPcFloodInsAmt(Float pcFloodInsAmt) {
		this.pcFloodInsAmt = pcFloodInsAmt;
	}

	@Column(name="PC_HAZ_INS_AMT")
	public Float getPcHazInsAmt() {
		return this.pcHazInsAmt;
	}

	public void setPcHazInsAmt(Float pcHazInsAmt) {
		this.pcHazInsAmt = pcHazInsAmt;
	}

	@Column(name="PC_OTHER_AMT")
	public Float getPcOtherAmt() {
		return this.pcOtherAmt;
	}

	public void setPcOtherAmt(Float pcOtherAmt) {
		this.pcOtherAmt = pcOtherAmt;
	}

	@Column(name="PC_RE_TAX_AMT")
	public Float getPcReTaxAmt() {
		return this.pcReTaxAmt;
	}

	public void setPcReTaxAmt(Float pcReTaxAmt) {
		this.pcReTaxAmt = pcReTaxAmt;
	}

	@Column(name="PC_SETASIDE_TOT_AMT")
	public Float getPcSetasideTotAmt() {
		return this.pcSetasideTotAmt;
	}

	public void setPcSetasideTotAmt(Float pcSetasideTotAmt) {
		this.pcSetasideTotAmt = pcSetasideTotAmt;
	}

	@Column(name="PCT_1_FMLY")
	public Float getPct1Fmly() {
		return this.pct1Fmly;
	}

	public void setPct1Fmly(Float pct1Fmly) {
		this.pct1Fmly = pct1Fmly;
	}

	@Column(name="PC_TOTAL_AMT")
	public Float getPcTotalAmt() {
		return this.pcTotalAmt;
	}

	public void setPcTotalAmt(Float pcTotalAmt) {
		this.pcTotalAmt = pcTotalAmt;
	}

	@Column(name="PD_STRMLN_FLG", length=3)
	public String getPdStrmlnFlg() {
		return this.pdStrmlnFlg;
	}

	public void setPdStrmlnFlg(String pdStrmlnFlg) {
		this.pdStrmlnFlg = pdStrmlnFlg;
	}

	@Column(name="PMTS_BFR_FRST_MISSD_PMT")
	public Short getPmtsBfrFrstMissdPmt() {
		return this.pmtsBfrFrstMissdPmt;
	}

	public void setPmtsBfrFrstMissdPmt(Short pmtsBfrFrstMissdPmt) {
		this.pmtsBfrFrstMissdPmt = pmtsBfrFrstMissdPmt;
	}

	@Column(name="PRCSNG_TYP", length=16)
	public String getPrcsngTyp() {
		return this.prcsngTyp;
	}

	public void setPrcsngTyp(String prcsngTyp) {
		this.prcsngTyp = prcsngTyp;
	}

	@Column(name="PRE_CLSNG_IND", length=3)
	public String getPreClsngInd() {
		return this.preClsngInd;
	}

	public void setPreClsngInd(String preClsngInd) {
		this.preClsngInd = preClsngInd;
	}

	@Column(name="PREQUAL_CNT")
	public Short getPrequalCnt() {
		return this.prequalCnt;
	}

	public void setPrequalCnt(Short prequalCnt) {
		this.prequalCnt = prequalCnt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PREQUAL_DT", length=23)
	public Date getPrequalDt() {
		return this.prequalDt;
	}

	public void setPrequalDt(Date prequalDt) {
		this.prequalDt = prequalDt;
	}

	@Column(name="PRE_RVW_DCSN", length=16)
	public String getPreRvwDcsn() {
		return this.preRvwDcsn;
	}

	public void setPreRvwDcsn(String preRvwDcsn) {
		this.preRvwDcsn = preRvwDcsn;
	}

	@Column(name="PREV_COMPLT_SBDVSN_IND", length=1)
	public Character getPrevCompltSbdvsnInd() {
		return this.prevCompltSbdvsnInd;
	}

	public void setPrevCompltSbdvsnInd(Character prevCompltSbdvsnInd) {
		this.prevCompltSbdvsnInd = prevCompltSbdvsnInd;
	}

	@Column(name="PRICE_EXCLDNG_CLSNG_COSTS")
	public Float getPriceExcldngClsngCosts() {
		return this.priceExcldngClsngCosts;
	}

	public void setPriceExcldngClsngCosts(Float priceExcldngClsngCosts) {
		this.priceExcldngClsngCosts = priceExcldngClsngCosts;
	}

	@Column(name="PRICE_INCLDNG_CLSNG_COSTS")
	public Float getPriceIncldngClsngCosts() {
		return this.priceIncldngClsngCosts;
	}

	public void setPriceIncldngClsngCosts(Float priceIncldngClsngCosts) {
		this.priceIncldngClsngCosts = priceIncldngClsngCosts;
	}

	@Column(name="PRICE_OF_PRIOR_SALE")
	public Float getPriceOfPriorSale() {
		return this.priceOfPriorSale;
	}

	public void setPriceOfPriorSale(Float priceOfPriorSale) {
		this.priceOfPriorSale = priceOfPriorSale;
	}

	@Column(name="PRIOR_SALE_RQRD_IND", length=1)
	public Character getPriorSaleRqrdInd() {
		return this.priorSaleRqrdInd;
	}

	public void setPriorSaleRqrdInd(Character priorSaleRqrdInd) {
		this.priorSaleRqrdInd = priorSaleRqrdInd;
	}

	@Column(name="PRIOR_SALE_WITHIN_LAST_3YR_IND", length=1)
	public Character getPriorSaleWithinLast3yrInd() {
		return this.priorSaleWithinLast3yrInd;
	}

	public void setPriorSaleWithinLast3yrInd(Character priorSaleWithinLast3yrInd) {
		this.priorSaleWithinLast3yrInd = priorSaleWithinLast3yrInd;
	}

	@Column(name="PRNCPL_RDCTN_AMT")
	public Float getPrncplRdctnAmt() {
		return this.prncplRdctnAmt;
	}

	public void setPrncplRdctnAmt(Float prncplRdctnAmt) {
		this.prncplRdctnAmt = prncplRdctnAmt;
	}

	@Column(name="PROD_TYPE", length=3)
	public String getProdType() {
		return this.prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	@Column(name="PROG_ID_F17", length=16)
	public String getProgIdF17() {
		return this.progIdF17;
	}

	public void setProgIdF17(String progIdF17) {
		this.progIdF17 = progIdF17;
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

	@Column(name="PROP_ADDR_ST", length=10)
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

	@Column(name="PROP_TYP", length=16)
	public String getPropTyp() {
		return this.propTyp;
	}

	public void setPropTyp(String propTyp) {
		this.propTyp = propTyp;
	}

	@Column(name="PRPRTY_APRSL_VL")
	public Float getPrprtyAprslVl() {
		return this.prprtyAprslVl;
	}

	public void setPrprtyAprslVl(Float prprtyAprslVl) {
		this.prprtyAprslVl = prprtyAprslVl;
	}

	@Column(name="PRPRTY_CNVRSN_TYP", length=16)
	public String getPrprtyCnvrsnTyp() {
		return this.prprtyCnvrsnTyp;
	}

	public void setPrprtyCnvrsnTyp(String prprtyCnvrsnTyp) {
		this.prprtyCnvrsnTyp = prprtyCnvrsnTyp;
	}

	@Column(name="PRR_CASE_MXMM_CLM_AMT")
	public Float getPrrCaseMxmmClmAmt() {
		return this.prrCaseMxmmClmAmt;
	}

	public void setPrrCaseMxmmClmAmt(Float prrCaseMxmmClmAmt) {
		this.prrCaseMxmmClmAmt = prrCaseMxmmClmAmt;
	}

	@Column(name="PTI_CAT", length=5)
	public String getPtiCat() {
		return this.ptiCat;
	}

	public void setPtiCat(String ptiCat) {
		this.ptiCat = ptiCat;
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

	@Column(name="RATIO_FIX_TEI_UW")
	public Float getRatioFixTeiUw() {
		return this.ratioFixTeiUw;
	}

	public void setRatioFixTeiUw(Float ratioFixTeiUw) {
		this.ratioFixTeiUw = ratioFixTeiUw;
	}

	@Column(name="RATIO_LOAN_TO_VL_NEW")
	public Float getRatioLoanToVlNew() {
		return this.ratioLoanToVlNew;
	}

	public void setRatioLoanToVlNew(Float ratioLoanToVlNew) {
		this.ratioLoanToVlNew = ratioLoanToVlNew;
	}

	@Column(name="RATIO_ORE_TEI")
	public Float getRatioOreTei() {
		return this.ratioOreTei;
	}

	public void setRatioOreTei(Float ratioOreTei) {
		this.ratioOreTei = ratioOreTei;
	}

	@Column(name="RATIO_TOT_PMT_TO_TOT_INC_ENDRS")
	public Float getRatioTotPmtToTotIncEndrs() {
		return this.ratioTotPmtToTotIncEndrs;
	}

	public void setRatioTotPmtToTotIncEndrs(Float ratioTotPmtToTotIncEndrs) {
		this.ratioTotPmtToTotIncEndrs = ratioTotPmtToTotIncEndrs;
	}

	@Column(name="RATIO_TOT_PMT_TO_TOT_INC_UW")
	public Float getRatioTotPmtToTotIncUw() {
		return this.ratioTotPmtToTotIncUw;
	}

	public void setRatioTotPmtToTotIncUw(Float ratioTotPmtToTotIncUw) {
		this.ratioTotPmtToTotIncUw = ratioTotPmtToTotIncUw;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RCV_SALE_DT", length=23)
	public Date getRcvSaleDt() {
		return this.rcvSaleDt;
	}

	public void setRcvSaleDt(Date rcvSaleDt) {
		this.rcvSaleDt = rcvSaleDt;
	}

	@Column(name="RE_DEBT_CURR_IND", length=1)
	public Character getReDebtCurrInd() {
		return this.reDebtCurrInd;
	}

	public void setReDebtCurrInd(Character reDebtCurrInd) {
		this.reDebtCurrInd = reDebtCurrInd;
	}

	@Column(name="RE_DEBT_LATE_PMT_IND", length=1)
	public Character getReDebtLatePmtInd() {
		return this.reDebtLatePmtInd;
	}

	public void setReDebtLatePmtInd(Character reDebtLatePmtInd) {
		this.reDebtLatePmtInd = reDebtLatePmtInd;
	}

	@Column(name="REFINANCE_IND", length=1)
	public Character getRefinanceInd() {
		return this.refinanceInd;
	}

	public void setRefinanceInd(Character refinanceInd) {
		this.refinanceInd = refinanceInd;
	}

	@Column(name="REFI_PRR_CASE_NBR", length=11)
	public String getRefiPrrCaseNbr() {
		return this.refiPrrCaseNbr;
	}

	public void setRefiPrrCaseNbr(String refiPrrCaseNbr) {
		this.refiPrrCaseNbr = refiPrrCaseNbr;
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

	@Column(name="RE_TAX_CURR_IND", length=1)
	public Character getReTaxCurrInd() {
		return this.reTaxCurrInd;
	}

	public void setReTaxCurrInd(Character reTaxCurrInd) {
		this.reTaxCurrInd = reTaxCurrInd;
	}

	@Column(name="RE_TAX_DELINQ_IND", length=1)
	public Character getReTaxDelinqInd() {
		return this.reTaxDelinqInd;
	}

	public void setReTaxDelinqInd(Character reTaxDelinqInd) {
		this.reTaxDelinqInd = reTaxDelinqInd;
	}

	@Column(name="REVOLVE_DEBT_CURR_IND", length=1)
	public Character getRevolveDebtCurrInd() {
		return this.revolveDebtCurrInd;
	}

	public void setRevolveDebtCurrInd(Character revolveDebtCurrInd) {
		this.revolveDebtCurrInd = revolveDebtCurrInd;
	}

	@Column(name="REVOLVE_DEBT_LATE_PMT_IND", length=1)
	public Character getRevolveDebtLatePmtInd() {
		return this.revolveDebtLatePmtInd;
	}

	public void setRevolveDebtLatePmtInd(Character revolveDebtLatePmtInd) {
		this.revolveDebtLatePmtInd = revolveDebtLatePmtInd;
	}

	@Column(name="RFNC_CD", length=16)
	public String getRfncCd() {
		return this.rfncCd;
	}

	public void setRfncCd(String rfncCd) {
		this.rfncCd = rfncCd;
	}

	@Column(name="RFNC_NEXT_CASE_NBR", length=11)
	public String getRfncNextCaseNbr() {
		return this.rfncNextCaseNbr;
	}

	public void setRfncNextCaseNbr(String rfncNextCaseNbr) {
		this.rfncNextCaseNbr = rfncNextCaseNbr;
	}

	@Column(name="RI_FAMILY_SIZE", scale=0)
	public Short getRiFamilySize() {
		return this.riFamilySize;
	}

	public void setRiFamilySize(Short riFamilySize) {
		this.riFamilySize = riFamilySize;
	}

	@Column(name="RI_SHORTFALL_AMT")
	public Float getRiShortfallAmt() {
		return this.riShortfallAmt;
	}

	public void setRiShortfallAmt(Float riShortfallAmt) {
		this.riShortfallAmt = riShortfallAmt;
	}

	@Column(name="RI_STANDARD_AMT")
	public Float getRiStandardAmt() {
		return this.riStandardAmt;
	}

	public void setRiStandardAmt(Float riStandardAmt) {
		this.riStandardAmt = riStandardAmt;
	}

	@Column(name="RI_TOTAL_AMT")
	public Float getRiTotalAmt() {
		return this.riTotalAmt;
	}

	public void setRiTotalAmt(Float riTotalAmt) {
		this.riTotalAmt = riTotalAmt;
	}

	@Column(name="RPR_SET_ASIDE")
	public Float getRprSetAside() {
		return this.rprSetAside;
	}

	public void setRprSetAside(Float rprSetAside) {
		this.rprSetAside = rprSetAside;
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

	@Column(name="SBDVSN_SPOT_LOT", length=16)
	public String getSbdvsnSpotLot() {
		return this.sbdvsnSpotLot;
	}

	public void setSbdvsnSpotLot(String sbdvsnSpotLot) {
		this.sbdvsnSpotLot = sbdvsnSpotLot;
	}

	@Column(name="SCNDRY_FNC_SRC", length=16)
	public String getScndryFncSrc() {
		return this.scndryFncSrc;
	}

	public void setScndryFncSrc(String scndryFncSrc) {
		this.scndryFncSrc = scndryFncSrc;
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

	@Column(name="SEND_MIC_IND", length=1)
	public Character getSendMicInd() {
		return this.sendMicInd;
	}

	public void setSendMicInd(Character sendMicInd) {
		this.sendMicInd = sendMicInd;
	}

	@Column(name="SITE_TYPE", length=30)
	public String getSiteType() {
		return this.siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	@Column(name="SNGL_DSBRSE_LMP_SUM_PMT_OPT", length=1)
	public Character getSnglDsbrseLmpSumPmtOpt() {
		return this.snglDsbrseLmpSumPmtOpt;
	}

	public void setSnglDsbrseLmpSumPmtOpt(Character snglDsbrseLmpSumPmtOpt) {
		this.snglDsbrseLmpSumPmtOpt = snglDsbrseLmpSumPmtOpt;
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

	@Column(name="SPNSR_MRTGGEE_ID_INST", length=5)
	public String getSpnsrMrtggeeIdInst() {
		return this.spnsrMrtggeeIdInst;
	}

	public void setSpnsrMrtggeeIdInst(String spnsrMrtggeeIdInst) {
		this.spnsrMrtggeeIdInst = spnsrMrtggeeIdInst;
	}

	@Column(name="SPNSR_MTGEE10_ID", length=10)
	public String getSpnsrMtgee10Id() {
		return this.spnsrMtgee10Id;
	}

	public void setSpnsrMtgee10Id(String spnsrMtgee10Id) {
		this.spnsrMtgee10Id = spnsrMtgee10Id;
	}

	@Column(name="SRVCR_MTGEE5_A43", length=5)
	public String getSrvcrMtgee5A43() {
		return this.srvcrMtgee5A43;
	}

	public void setSrvcrMtgee5A43(String srvcrMtgee5A43) {
		this.srvcrMtgee5A43 = srvcrMtgee5A43;
	}

	@Column(name="STRMNLN_REFI_TYPE", length=1)
	public Character getStrmnlnRefiType() {
		return this.strmnlnRefiType;
	}

	public void setStrmnlnRefiType(Character strmnlnRefiType) {
		this.strmnlnRefiType = strmnlnRefiType;
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

	@Column(name="TERM_15_YR_IND", length=1)
	public Character getTerm15YrInd() {
		return this.term15YrInd;
	}

	public void setTerm15YrInd(Character term15YrInd) {
		this.term15YrInd = term15YrInd;
	}

	@Column(name="TERM")
	public Integer getTerm() {
		return this.term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	@Column(name="TERM_TYP_CD", length=16)
	public String getTermTypCd() {
		return this.termTypCd;
	}

	public void setTermTypCd(String termTypCd) {
		this.termTypCd = termTypCd;
	}

	@Column(name="TOT_ACQ_COSTS")
	public Float getTotAcqCosts() {
		return this.totAcqCosts;
	}

	public void setTotAcqCosts(Float totAcqCosts) {
		this.totAcqCosts = totAcqCosts;
	}

	@Column(name="TOTAL_REQUIRED_FUNDS_TO_CLOSE")
	public Float getTotalRequiredFundsToClose() {
		return this.totalRequiredFundsToClose;
	}

	public void setTotalRequiredFundsToClose(Float totalRequiredFundsToClose) {
		this.totalRequiredFundsToClose = totalRequiredFundsToClose;
	}

	@Column(name="TOT_ANN_EFF_INCM")
	public Float getTotAnnEffIncm() {
		return this.totAnnEffIncm;
	}

	public void setTotAnnEffIncm(Float totAnnEffIncm) {
		this.totAnnEffIncm = totAnnEffIncm;
	}

	@Column(name="TOT_ASSETS_ENDRS")
	public Float getTotAssetsEndrs() {
		return this.totAssetsEndrs;
	}

	public void setTotAssetsEndrs(Float totAssetsEndrs) {
		this.totAssetsEndrs = totAssetsEndrs;
	}

	@Column(name="TOT_ASSETS_UW")
	public Float getTotAssetsUw() {
		return this.totAssetsUw;
	}

	public void setTotAssetsUw(Float totAssetsUw) {
		this.totAssetsUw = totAssetsUw;
	}

	@Column(name="TOT_CLSNG_CSTS_ENDRS")
	public Float getTotClsngCstsEndrs() {
		return this.totClsngCstsEndrs;
	}

	public void setTotClsngCstsEndrs(Float totClsngCstsEndrs) {
		this.totClsngCstsEndrs = totClsngCstsEndrs;
	}

	@Column(name="TOT_CLSNG_CSTS_UW")
	public Float getTotClsngCstsUw() {
		return this.totClsngCstsUw;
	}

	public void setTotClsngCstsUw(Float totClsngCstsUw) {
		this.totClsngCstsUw = totClsngCstsUw;
	}

	@Column(name="TOT_FIXED_PYMT_ENDRS")
	public Float getTotFixedPymtEndrs() {
		return this.totFixedPymtEndrs;
	}

	public void setTotFixedPymtEndrs(Float totFixedPymtEndrs) {
		this.totFixedPymtEndrs = totFixedPymtEndrs;
	}

	@Column(name="TOT_FIXED_PYMT_UW")
	public Float getTotFixedPymtUw() {
		return this.totFixedPymtUw;
	}

	public void setTotFixedPymtUw(Float totFixedPymtUw) {
		this.totFixedPymtUw = totFixedPymtUw;
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

	@Column(name="TOT_MNTHLY_MTG_PYMT_UW")
	public Float getTotMnthlyMtgPymtUw() {
		return this.totMnthlyMtgPymtUw;
	}

	public void setTotMnthlyMtgPymtUw(Float totMnthlyMtgPymtUw) {
		this.totMnthlyMtgPymtUw = totMnthlyMtgPymtUw;
	}

	@Column(name="TRNSMSN_TYP", length=16)
	public String getTrnsmsnTyp() {
		return this.trnsmsnTyp;
	}

	public void setTrnsmsnTyp(String trnsmsnTyp) {
		this.trnsmsnTyp = trnsmsnTyp;
	}

	@Column(name="TYP_ORGNTNG_MTGEE", length=16)
	public String getTypOrgntngMtgee() {
		return this.typOrgntngMtgee;
	}

	public void setTypOrgntngMtgee(String typOrgntngMtgee) {
		this.typOrgntngMtgee = typOrgntngMtgee;
	}

	@Column(name="UFMIP_EARNED_CURR_MM")
	public Float getUfmipEarnedCurrMm() {
		return this.ufmipEarnedCurrMm;
	}

	public void setUfmipEarnedCurrMm(Float ufmipEarnedCurrMm) {
		this.ufmipEarnedCurrMm = ufmipEarnedCurrMm;
	}

	@Column(name="UFMIP_FACTOR")
	public Float getUfmipFactor() {
		return this.ufmipFactor;
	}

	public void setUfmipFactor(Float ufmipFactor) {
		this.ufmipFactor = ufmipFactor;
	}

	@Column(name="UFMIP_PD_AMT")
	public Float getUfmipPdAmt() {
		return this.ufmipPdAmt;
	}

	public void setUfmipPdAmt(Float ufmipPdAmt) {
		this.ufmipPdAmt = ufmipPdAmt;
	}

	@Column(name="UFMIP_PD_CASH")
	public Float getUfmipPdCash() {
		return this.ufmipPdCash;
	}

	public void setUfmipPdCash(Float ufmipPdCash) {
		this.ufmipPdCash = ufmipPdCash;
	}

	@Column(name="UNDERWRITER_ID", length=16)
	public String getUnderwriterId() {
		return this.underwriterId;
	}

	public void setUnderwriterId(String underwriterId) {
		this.underwriterId = underwriterId;
	}

	@Column(name="UNDERWRITING_METHOD", length=16)
	public String getUnderwritingMethod() {
		return this.underwritingMethod;
	}

	public void setUnderwritingMethod(String underwritingMethod) {
		this.underwritingMethod = underwritingMethod;
	}

	@Column(name="UNDRSRVD_IND", length=1)
	public Character getUndrsrvdInd() {
		return this.undrsrvdInd;
	}

	public void setUndrsrvdInd(Character undrsrvdInd) {
		this.undrsrvdInd = undrsrvdInd;
	}

	@Column(name="UNDRWRTING_MTGEE5", length=5)
	public String getUndrwrtingMtgee5() {
		return this.undrwrtingMtgee5;
	}

	public void setUndrwrtingMtgee5(String undrwrtingMtgee5) {
		this.undrwrtingMtgee5 = undrwrtingMtgee5;
	}

	@Column(name="UNPD_BAL")
	public Float getUnpdBal() {
		return this.unpdBal;
	}

	public void setUnpdBal(Float unpdBal) {
		this.unpdBal = unpdBal;
	}

	@Column(name="VAL_PLUS_CLSNG")
	public Float getValPlusClsng() {
		return this.valPlusClsng;
	}

	public void setValPlusClsng(Float valPlusClsng) {
		this.valPlusClsng = valPlusClsng;
	}

	@Column(name="YEAR_BUILT", length=4)
	public String getYearBuilt() {
		return this.yearBuilt;
	}

	public void setYearBuilt(String yearBuilt) {
		this.yearBuilt = yearBuilt;
	}

	@Column(name="YR_MO_CONST_CMPLTD")
	public Short getYrMoConstCmpltd() {
		return this.yrMoConstCmpltd;
	}

	public void setYrMoConstCmpltd(Short yrMoConstCmpltd) {
		this.yrMoConstCmpltd = yrMoConstCmpltd;
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
