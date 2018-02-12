//DO NOT MODIFY: generated for HUD LRS 
package gov.hud.lrs.common.entity.ext;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="LRS_CASE_RECORD")
@SuppressWarnings("serial")
public class LrsCaseRecord implements java.io.Serializable {
	private String caseNumber;
	private Date aprslCmpltnDt;
	private String selectionId;
	private Date prequalDt;
	private Integer addtnl10pctIplUsageAmt;
	private Character addtnl10pctIplUsageInd;
	private String adpCode;
	private Integer allowClsgCost;
	private String amortTypCd;
	private String aplctnMthd;
	private String armAdjPrd;
	private String armIndxExpctdRt;
	private String armIndxInd;
	private String armInd;
	private BigDecimal armMrgnAmt;
	private Date armDt;
	private Integer countAus;
	private String ausScoreCode;
	private String bsmtCd;
	private String bnkrptcyCd;
	private Date bnkrptcyDt;
	private String borrNm;
	private Date borrBrthDt;
	private String borrCnslTyp;
	private String borrEmplmntInd;
	private String borrGender;
	private Integer borrHsngExp;
	private String borrTyp;
	private String buyDwnInd;
	private Date csEstabDt;
	private Date cctReinsDt;
	private String csTyp;
	private Character cashoutRefiInd;
	private String claimType;
	private Date clsngDt;
	private Character cfEquivicalAssetsInd;
	private Character cfExpectedSsiInd;
	private Character cfHecmSufficientInd;
	private Character cfImputedIncomeInd;
	private Character cfNbsIncomeInd;
	private Character cfOtIncomeInd;
	private Character cfOtherIncomeInd;
	private Character cfPropPmtHistInd;
	private Character condoFeeCurrInd;
	private Character condoFeeDelinqInd;
	private String constCd;
	private String constStsCd;
	private Integer prcExclClsngAmt;
	private Integer prcInclClsngAmt;
	private Integer corpAdvncAmt;
	private Character currDflt90dayInd;
	private Date dfltAsgnmntDt;
	private Date dfltCrtnDt;
	private Date currDfltCycDt;
	private Integer currDfltEpisodeNbr;
	private Date currDfltMmCycDt;
	private String currDfltRsnCd;
	private String currDfltStsCd;
	private Date currDfltStsDt;
	private Character currDfltStsInd;
	private String currDfltStsSummaryCd;
	private Integer daysInDefault;
	private Character dflt90dayInd;
	private Date dfltCycDt;
	private Integer dfltEpisodeNbr;
	private Date ftInEps2mnthDelqDt;
	private String dfltHistLossmitCd;
	private Integer dfltMmCycDt;
	private String dfltRsnCd;
	private String dfltSrvcr;
	private String dfltStsCd;
	private Date dfltStsDt;
	private String dfltStsSummaryCd;
	private Date dfltTrnsctnDt;
	private Short dpndntCnt;
	private String derivedOptnUsedCd;
	private Date dfltHistFtInEps3mDelqDt;
	private String dirLendingBranchInd;
	private Character earlyClaimInd;
	private Character earlyDefaultInd;
	private Integer effectiveFicoScore;
	private String endCd;
	private Date endrsmntDt;
	private String endrsmntRvwPrsnnlId;
	private String energyEffMrtg;
	private Character fctryFbrct;
	private Character femaFloodAreaInd;
	private Character fhacAddrChg;
	private Integer ficoScore;
	private String fncngTyp;
	private Character frclsrInd;
	private Character ftInEps2mnthDelqInd;
	private Date ftInEps3mnthDelqDt;
	private Character ftInEps3mnthDelqInd;
	private Date fte2mnthDelqDt;
	private Integer giftLtrAmt;
	private Integer giftLtr2Amt;
	private String giftLtrSrc;
	private String giftLtrTin;
	private Character hardcopyDocsInd;
	private Integer hecmAssets;
	private Date hecmCounselDt;
	private Date createDate;
	private String createPrsnnlId;
	private Date updateDate;
	private String updatePrsnnlId;
	private Integer hecmLiens;
	private Integer hecmMnthlyInc;
	private Integer hecmPrncplLmt;
	private String hldrMtgee10A43c;
	private String hldrMtgee5A43;
	private String hsngPgmCd;
	private Integer initDisbursementLimit;
	private Integer initFee;
	private Integer initMipFactor;
	private String insrncStatusCd;
	private BigDecimal intRt;
	private Date lastCsScoreDt;
	private String miscLndrInsrncInd;
	private Integer leCompoundRate;
	private Integer leExpectedRate;
	private Integer leProjectedAmt;
	private Integer lesaFundingAmt;
	private String lesaFundingType;
	private Integer leSetasideAmt;
	private Integer leTalcMonths;
	private Integer monthlyPI;
	private String loanNbr;
	private String loanPrps;
	private Character loanPrpsFrwdPymtInd;
	private Character loanPrpsImprvmntInd;
	private Character loanPrpsIncmInd;
	private Character loanPrpsInsrncInd;
	private Character loanPrpsLeisureInd;
	private Character loanPrpsMedclInd;
	private Character loanPrpsOthrInd;
	private String propTyp;
	private Character loanPrpsTaxesInd;
	private String loanPrpsText;
	private String ltvCat;
	private String ltvCatNew;
	private String ltvCatOld;
	private BigDecimal ratioLoanToVlNew;
	private BigDecimal ratioLoanToVlOld;
	private String loanType;
	private String lossmitCd;
	private Integer mndtryOblgtnsAmt;
	private Integer mandatoryObligsAmt;
	private Character marriedToNbsInd;
	private BigDecimal currMnthlyMip;
	private String miscAusDcsnCd;
	private String miscAusInd;
	private Short randomNumber;
	private Integer meDebtAmt;
	private Integer meOtherAmt;
	private Integer meReAmt;
	private Integer meTotalAmt;
	private Integer miImputedAmt;
	private Integer miOtherAmt;
	private Integer miTotalAmt;
	private Integer mnthlySetAside;
	private BigDecimal mortExcldFncdMip;
	private Character mipFinancedInd;
	private Short nbrMonths;
	private String nbsFirstMiddleLast;
	private Integer nbrhdPctOwned;
	private Integer nbrhdPrice;
	private Integer maxClaimAmtNew;
	private Date norIssRptDt;
	private BigDecimal nbrBthrms;
	private Short nbrBdrm;
	private Short nbrRms;
	private Short numLivingUnits;
	private String ocpncySts;
	private String ocpncyStsCd;
	private Date ocpncyStsDt;
	private Integer maxClaimAmtOld;
	private Date oldstUnpdDt;
	private BigDecimal origMrtgAmt;
	private String orgntngMtgee5;
	private String orgntngMtgee10Id;
	private String typOrgntngMtgee;
	private Character otherDebtCurrInd;
	private Character otherDebtLatePmtInd;
	private String preRvwDcsn;
	private String preClsngInd;
	private String prequalOutput;
	private Character prevCompltSbdvsnInd;
	private BigDecimal prncplRdctnAmt;
	private Character priorSaleRqrdInd;
	private String prcsngTyp;
	private String productType;
	private String progIdF17;
	private Date dtAcq;
	private String propAddrSt;
	private BigDecimal prprtyAprslVl;
	private Integer pcCondoFeeAmt;
	private Integer pcFloodInsAmt;
	private Integer pcHazInsAmt;
	private Integer pcOtherAmt;
	private Integer pcReTaxAmt;
	private Integer pcSetasideTotAmt;
	private Integer pcTotalAmt;
	private String prprtyCnvrsnTyp;
	private String pdStrmlnFlg;
	private Short pymtsBfrFrstMissedPymt;
	private BigDecimal ratioOreTei;
	private BigDecimal ratioFixTei;
	private String ptiCat;
	private BigDecimal ratioTmpTei;
	private Character reDebtCurrInd;
	private Character reDebtLatePmtInd;
	private Character reTaxCurrInd;
	private Character reTaxDelinqInd;
	private String rfncCd;
	private String rfncInd;
	private String rfncNextCaseNbr;
	private Integer rprSetAside;
	private BigDecimal rqrdInvest;
	private Short riFamilySize;
	private Integer riShortfallAmt;
	private Integer riStandardAmt;
	private Integer riTotalAmt;
	private Character revolveDebtCurrInd;
	private Character revolveDebtLatePmtInd;
	private String nbrhdCd;
	private Date rcvSaleDt;
	private String scndryFncSrc;
	private Integer secondaryFinanceAmt;
	private BigDecimal sellerCntrbtn;
	private Character sendMicInd;
	private String oldSrvcrMtgee;
	private String srvcrMtgee10A43c;
	private String srvcrMtgee5A43;
	private String sfpcsMtgeeId;
	private Integer snglDsbrseLmpSumPmtOpt;
	private Integer pct1Fmly;
	private String soaCd;
	private String spnsrMtgee10Id;
	private Integer sqncNbr;
	private Character strmnlnRefiType;
	private String strtStopEpsInd;
	private String sbdvsnSpotLot;
	private Integer taxInsurYr1Pmts;
	private Integer taxesInsrncFrstYrAmt;
	private String trvSlctRsnCd;
	private Integer term;
	private String termTypCd;
	private BigDecimal totAnnEffIncm;
	private BigDecimal totAssets;
	private BigDecimal totClsngCsts;
	private BigDecimal totFixedPymt;
	private BigDecimal totMnthlyEffIncm;
	private BigDecimal totMnthlyMtgPymt;
	private BigDecimal totAcqCosts;
	private String trnsmsnTyp;
	private BigDecimal ufmipPdAmt;
	private BigDecimal ufmipPdCash;
	private BigDecimal ufmipEarnedCurrMm;
	private BigDecimal ufmipFactor;
	private String underwriterId;
	private String undrwrtingMtgee5;
	private Integer unpdBal;
	private Integer valPlusClsng;
	private String createdBy;
	private String updatedBy;
	private Date createdTs;
	private Date updatedTs;
	private Character bldgTyp;
	private String dcsnCd;
	private Character reo100DownPmtProgInd;
	private String affordHousProvEin;
	private String appraiserName;
	private Integer assetsAfterClosing;
	private Integer assetsAfterClosingEndrs;
	private Integer assetsAfterClosingUw;
	private Character assumedLoanInd;
	private BigDecimal ratioFixTeiEndrs;
	private BigDecimal ratioFixTeiUw;
	private Character backToWorkInd;
	private Character bnkrptcyAnyInd;
	private Character borr1FirstTimeBuyerInd;
	private String borr1Name;
	private Character borr1RentingInd;
	private Character borr1SelfEmplInd;
	private String borr1Ssn;
	private Integer borr1YearsCurrentJob;
	private Integer borr2YearsCurrentJob;
	private Character borr2FirstTimeBuyerInd;
	private String borr2Name;
	private Character borr2RentingInd;
	private Character borr2SelfEmplInd;
	private String borr2Ssn;
	private Integer borrReqdInvestToClose;
	private Integer borrHsngExpEndrs;
	private Integer borrHsngExpUw;
	private Integer borrPaidClosingCosts;
	private Character buildingOnOwnLandInd;
	private Character bnkrptcyChptr13Ind;
	private Character bnkrptcyChptr7Ind;
	private Date closngDt;
	private BigDecimal combinedLoanToValuePct;
	private String commtyLandTrustEin;
	private Character commPropertyStateInd;
	private Date applicationDate;
	private Date dateOfPriorSale;
	private String dcsnCdEndrs;
	private String dcsnCdUw;
	private Character defaultEpisodeExistsInd;
	private Character depoIsPresentInd;
	private Character disasterInd;
	private Date effDateAprsl;
	private Date effDateAprslUpdate;
	private Character endrsBynd60DaysCloseInd;
	private Integer escrowAmount;
	private BigDecimal expectedRate;
	private Integer ficoDecisionScoreUw;
	private Integer ficoDecisionScoreEndrs;
	private Character flippingCategory2Ind;
	private BigDecimal ratioTotPmtToTotIncUw;
	private BigDecimal ratioTotPmtToTotIncEndrs;
	private String giftLtr2Source;
	private String hecmCounselCertNo;
	private Integer hudReoRepairAmt;
	private Character insurAppInTimeInd;
	private Character invest2ndResidInd;
	private Date lastServicingMrtgXferDt;
	private String loanOfficer;
	private String loanOfficerNmls;
	private Integer mandatoryObligBorrAmt;
	private Integer mandatoryObligLendAmt;
	private Character manUwStretchRatioInd;
	private Integer maxClaimAmt;
	private BigDecimal maxRate;
	private Character currentAtEndorseInd;
	private Character nonOccupyingBorrInd;
	private String orgntngMtgeeNmlsId;
	private Integer originationFee;
	private String paymentPlan;
	private Integer priceOfPriorSale;
	private Character priorSaleWithinLast3yrInd;
	private String propCurrOccupancyType;
	private Integer propertyRepairs;
	private String constCompltYrMo;
	private Integer qualifiedMrtgPointsAndFees;
	private Integer realEstateAssets;
	private Date repairCompletionDate;
	private Character repairRiderInFileInd;
	private Integer salesPrice;
	private Character salePriceGtrAcqCostInd;
	private Character secondaryFinanceExistsInd;
	private BigDecimal sellerCntrbtnPcnt;
	private String siteType;
	private String specialProgram;
	private Character tenYrRateLockInd;
	private Integer totAssetsEndrs;
	private Integer totAssetsUw;
	private Integer totClsngCstsEndrs;
	private Integer totClsngCstsUw;
	private Integer totFixedPymtEndrs;
	private Integer totFixedPymtUw;
	private Integer totMnthlyMtgPymtEndrs;
	private Integer totMnthlyMtgPymtUw;
	private Integer totalRequiredFundsToClose;
	private String underwritingMethod;
	private String yearBuilt;
	private Date frclsrStrtDt;
	private Integer rprEscrwAmt;
	private String batchId;
	private String rvwLocationId;
	private Date distributionDt;
	private String mtgee5;
	private Date dueDate;
	private String prefRvwLocation;
	private String primarySelectionReason;
	private Date receivedDt;
	private String rqstDocsSourceCd;
	private Date requestedDtTm;
	private String reviewType;
	private Date selectionDt;
	private String status;
	private String selectionMethod;
	private String prodType;

	public LrsCaseRecord() {
	}

    @Id 
    
    @Column(name="CASE_NUMBER", unique=true, nullable=false, length=11)
	public String getCaseNumber() {
		return this.caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="APRSL_CMPLTN_DT", length=23)
	public Date getAprslCmpltnDt() {
		return this.aprslCmpltnDt;
	}

	public void setAprslCmpltnDt(Date aprslCmpltnDt) {
		this.aprslCmpltnDt = aprslCmpltnDt;
	}

    
    @Column(name="SELECTION_ID", nullable=false, length=36)
	public String getSelectionId() {
		return this.selectionId;
	}

	public void setSelectionId(String selectionId) {
		this.selectionId = selectionId;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="PREQUAL_DT", length=23)
	public Date getPrequalDt() {
		return this.prequalDt;
	}

	public void setPrequalDt(Date prequalDt) {
		this.prequalDt = prequalDt;
	}

    
    @Column(name="ADDTNL_10PCT_IPL_USAGE_AMT")
	public Integer getAddtnl10pctIplUsageAmt() {
		return this.addtnl10pctIplUsageAmt;
	}

	public void setAddtnl10pctIplUsageAmt(Integer addtnl10pctIplUsageAmt) {
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
	public Integer getAllowClsgCost() {
		return this.allowClsgCost;
	}

	public void setAllowClsgCost(Integer allowClsgCost) {
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

    
    @Column(name="ARM_ADJ_PRD", length=16)
	public String getArmAdjPrd() {
		return this.armAdjPrd;
	}

	public void setArmAdjPrd(String armAdjPrd) {
		this.armAdjPrd = armAdjPrd;
	}

    
    @Column(name="ARM_INDX_EXPCTD_RT", length=2)
	public String getArmIndxExpctdRt() {
		return this.armIndxExpctdRt;
	}

	public void setArmIndxExpctdRt(String armIndxExpctdRt) {
		this.armIndxExpctdRt = armIndxExpctdRt;
	}

    
    @Column(name="ARM_INDX_IND", length=3)
	public String getArmIndxInd() {
		return this.armIndxInd;
	}

	public void setArmIndxInd(String armIndxInd) {
		this.armIndxInd = armIndxInd;
	}

    
    @Column(name="ARM_IND", length=3)
	public String getArmInd() {
		return this.armInd;
	}

	public void setArmInd(String armInd) {
		this.armInd = armInd;
	}

    
    @Column(name="ARM_MRGN_AMT", precision=6, scale=3)
	public BigDecimal getArmMrgnAmt() {
		return this.armMrgnAmt;
	}

	public void setArmMrgnAmt(BigDecimal armMrgnAmt) {
		this.armMrgnAmt = armMrgnAmt;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ARM_DT", length=23)
	public Date getArmDt() {
		return this.armDt;
	}

	public void setArmDt(Date armDt) {
		this.armDt = armDt;
	}

    
    @Column(name="COUNT_AUS")
	public Integer getCountAus() {
		return this.countAus;
	}

	public void setCountAus(Integer countAus) {
		this.countAus = countAus;
	}

    
    @Column(name="AUS_SCORE_CODE", length=16)
	public String getAusScoreCode() {
		return this.ausScoreCode;
	}

	public void setAusScoreCode(String ausScoreCode) {
		this.ausScoreCode = ausScoreCode;
	}

    
    @Column(name="BSMT_CD", length=16)
	public String getBsmtCd() {
		return this.bsmtCd;
	}

	public void setBsmtCd(String bsmtCd) {
		this.bsmtCd = bsmtCd;
	}

    
    @Column(name="BNKRPTCY_CD", length=16)
	public String getBnkrptcyCd() {
		return this.bnkrptcyCd;
	}

	public void setBnkrptcyCd(String bnkrptcyCd) {
		this.bnkrptcyCd = bnkrptcyCd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="BNKRPTCY_DT", length=23)
	public Date getBnkrptcyDt() {
		return this.bnkrptcyDt;
	}

	public void setBnkrptcyDt(Date bnkrptcyDt) {
		this.bnkrptcyDt = bnkrptcyDt;
	}

    
    @Column(name="BORR_NM", length=100)
	public String getBorrNm() {
		return this.borrNm;
	}

	public void setBorrNm(String borrNm) {
		this.borrNm = borrNm;
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

    
    @Column(name="BORR_EMPLMNT_IND", length=3)
	public String getBorrEmplmntInd() {
		return this.borrEmplmntInd;
	}

	public void setBorrEmplmntInd(String borrEmplmntInd) {
		this.borrEmplmntInd = borrEmplmntInd;
	}

    
    @Column(name="BORR_GENDER", length=13)
	public String getBorrGender() {
		return this.borrGender;
	}

	public void setBorrGender(String borrGender) {
		this.borrGender = borrGender;
	}

    
    @Column(name="BORR_HSNG_EXP")
	public Integer getBorrHsngExp() {
		return this.borrHsngExp;
	}

	public void setBorrHsngExp(Integer borrHsngExp) {
		this.borrHsngExp = borrHsngExp;
	}

    
    @Column(name="BORR_TYP", length=16)
	public String getBorrTyp() {
		return this.borrTyp;
	}

	public void setBorrTyp(String borrTyp) {
		this.borrTyp = borrTyp;
	}

    
    @Column(name="BUY_DWN_IND", length=3)
	public String getBuyDwnInd() {
		return this.buyDwnInd;
	}

	public void setBuyDwnInd(String buyDwnInd) {
		this.buyDwnInd = buyDwnInd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CS_ESTAB_DT", length=23)
	public Date getCsEstabDt() {
		return this.csEstabDt;
	}

	public void setCsEstabDt(Date csEstabDt) {
		this.csEstabDt = csEstabDt;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CCT_REINS_DT", length=23)
	public Date getCctReinsDt() {
		return this.cctReinsDt;
	}

	public void setCctReinsDt(Date cctReinsDt) {
		this.cctReinsDt = cctReinsDt;
	}

    
    @Column(name="CS_TYP", length=16)
	public String getCsTyp() {
		return this.csTyp;
	}

	public void setCsTyp(String csTyp) {
		this.csTyp = csTyp;
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

    
    @Column(name="CF_OT_INCOME_IND", length=1)
	public Character getCfOtIncomeInd() {
		return this.cfOtIncomeInd;
	}

	public void setCfOtIncomeInd(Character cfOtIncomeInd) {
		this.cfOtIncomeInd = cfOtIncomeInd;
	}

    
    @Column(name="CF_OTHER_INCOME_IND", length=1)
	public Character getCfOtherIncomeInd() {
		return this.cfOtherIncomeInd;
	}

	public void setCfOtherIncomeInd(Character cfOtherIncomeInd) {
		this.cfOtherIncomeInd = cfOtherIncomeInd;
	}

    
    @Column(name="CF_PROP_PMT_HIST_IND", length=1)
	public Character getCfPropPmtHistInd() {
		return this.cfPropPmtHistInd;
	}

	public void setCfPropPmtHistInd(Character cfPropPmtHistInd) {
		this.cfPropPmtHistInd = cfPropPmtHistInd;
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

    
    @Column(name="PRC_EXCL_CLSNG_AMT")
	public Integer getPrcExclClsngAmt() {
		return this.prcExclClsngAmt;
	}

	public void setPrcExclClsngAmt(Integer prcExclClsngAmt) {
		this.prcExclClsngAmt = prcExclClsngAmt;
	}

    
    @Column(name="PRC_INCL_CLSNG_AMT")
	public Integer getPrcInclClsngAmt() {
		return this.prcInclClsngAmt;
	}

	public void setPrcInclClsngAmt(Integer prcInclClsngAmt) {
		this.prcInclClsngAmt = prcInclClsngAmt;
	}

    
    @Column(name="CORP_ADVNC_AMT")
	public Integer getCorpAdvncAmt() {
		return this.corpAdvncAmt;
	}

	public void setCorpAdvncAmt(Integer corpAdvncAmt) {
		this.corpAdvncAmt = corpAdvncAmt;
	}

    
    @Column(name="CURR_DFLT_90DAY_IND", length=1)
	public Character getCurrDflt90dayInd() {
		return this.currDflt90dayInd;
	}

	public void setCurrDflt90dayInd(Character currDflt90dayInd) {
		this.currDflt90dayInd = currDflt90dayInd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DFLT_ASGNMNT_DT", length=23)
	public Date getDfltAsgnmntDt() {
		return this.dfltAsgnmntDt;
	}

	public void setDfltAsgnmntDt(Date dfltAsgnmntDt) {
		this.dfltAsgnmntDt = dfltAsgnmntDt;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DFLT_CRTN_DT", length=23)
	public Date getDfltCrtnDt() {
		return this.dfltCrtnDt;
	}

	public void setDfltCrtnDt(Date dfltCrtnDt) {
		this.dfltCrtnDt = dfltCrtnDt;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CURR_DFLT_MM_CYC_DT", length=23)
	public Date getCurrDfltMmCycDt() {
		return this.currDfltMmCycDt;
	}

	public void setCurrDfltMmCycDt(Date currDfltMmCycDt) {
		this.currDfltMmCycDt = currDfltMmCycDt;
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

    
    @Column(name="CURR_DFLT_STS_IND", length=1)
	public Character getCurrDfltStsInd() {
		return this.currDfltStsInd;
	}

	public void setCurrDfltStsInd(Character currDfltStsInd) {
		this.currDfltStsInd = currDfltStsInd;
	}

    
    @Column(name="CURR_DFLT_STS_SUMMARY_CD", length=16)
	public String getCurrDfltStsSummaryCd() {
		return this.currDfltStsSummaryCd;
	}

	public void setCurrDfltStsSummaryCd(String currDfltStsSummaryCd) {
		this.currDfltStsSummaryCd = currDfltStsSummaryCd;
	}

    
    @Column(name="DAYS_IN_DEFAULT")
	public Integer getDaysInDefault() {
		return this.daysInDefault;
	}

	public void setDaysInDefault(Integer daysInDefault) {
		this.daysInDefault = daysInDefault;
	}

    
    @Column(name="DFLT_90DAY_IND", length=1)
	public Character getDflt90dayInd() {
		return this.dflt90dayInd;
	}

	public void setDflt90dayInd(Character dflt90dayInd) {
		this.dflt90dayInd = dflt90dayInd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DFLT_CYC_DT", length=23)
	public Date getDfltCycDt() {
		return this.dfltCycDt;
	}

	public void setDfltCycDt(Date dfltCycDt) {
		this.dfltCycDt = dfltCycDt;
	}

    
    @Column(name="DFLT_EPISODE_NBR")
	public Integer getDfltEpisodeNbr() {
		return this.dfltEpisodeNbr;
	}

	public void setDfltEpisodeNbr(Integer dfltEpisodeNbr) {
		this.dfltEpisodeNbr = dfltEpisodeNbr;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FT_IN_EPS_2MNTH_DELQ_DT", length=23)
	public Date getFtInEps2mnthDelqDt() {
		return this.ftInEps2mnthDelqDt;
	}

	public void setFtInEps2mnthDelqDt(Date ftInEps2mnthDelqDt) {
		this.ftInEps2mnthDelqDt = ftInEps2mnthDelqDt;
	}

    
    @Column(name="DFLT_HIST_LOSSMIT_CD", length=16)
	public String getDfltHistLossmitCd() {
		return this.dfltHistLossmitCd;
	}

	public void setDfltHistLossmitCd(String dfltHistLossmitCd) {
		this.dfltHistLossmitCd = dfltHistLossmitCd;
	}

    
    @Column(name="DFLT_MM_CYC_DT")
	public Integer getDfltMmCycDt() {
		return this.dfltMmCycDt;
	}

	public void setDfltMmCycDt(Integer dfltMmCycDt) {
		this.dfltMmCycDt = dfltMmCycDt;
	}

    
    @Column(name="DFLT_RSN_CD", length=16)
	public String getDfltRsnCd() {
		return this.dfltRsnCd;
	}

	public void setDfltRsnCd(String dfltRsnCd) {
		this.dfltRsnCd = dfltRsnCd;
	}

    
    @Column(name="DFLT_SRVCR", length=5)
	public String getDfltSrvcr() {
		return this.dfltSrvcr;
	}

	public void setDfltSrvcr(String dfltSrvcr) {
		this.dfltSrvcr = dfltSrvcr;
	}

    
    @Column(name="DFLT_STS_CD", length=16)
	public String getDfltStsCd() {
		return this.dfltStsCd;
	}

	public void setDfltStsCd(String dfltStsCd) {
		this.dfltStsCd = dfltStsCd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DFLT_STS_DT", length=23)
	public Date getDfltStsDt() {
		return this.dfltStsDt;
	}

	public void setDfltStsDt(Date dfltStsDt) {
		this.dfltStsDt = dfltStsDt;
	}

    
    @Column(name="DFLT_STS_SUMMARY_CD", length=16)
	public String getDfltStsSummaryCd() {
		return this.dfltStsSummaryCd;
	}

	public void setDfltStsSummaryCd(String dfltStsSummaryCd) {
		this.dfltStsSummaryCd = dfltStsSummaryCd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DFLT_TRNSCTN_DT", length=23)
	public Date getDfltTrnsctnDt() {
		return this.dfltTrnsctnDt;
	}

	public void setDfltTrnsctnDt(Date dfltTrnsctnDt) {
		this.dfltTrnsctnDt = dfltTrnsctnDt;
	}

    
    @Column(name="DPNDNT_CNT", precision=3, scale=0)
	public Short getDpndntCnt() {
		return this.dpndntCnt;
	}

	public void setDpndntCnt(Short dpndntCnt) {
		this.dpndntCnt = dpndntCnt;
	}

    
    @Column(name="DERIVED_OPTN_USED_CD", length=16)
	public String getDerivedOptnUsedCd() {
		return this.derivedOptnUsedCd;
	}

	public void setDerivedOptnUsedCd(String derivedOptnUsedCd) {
		this.derivedOptnUsedCd = derivedOptnUsedCd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DFLT_HIST_FT_IN_EPS_3M_DELQ_DT", length=23)
	public Date getDfltHistFtInEps3mDelqDt() {
		return this.dfltHistFtInEps3mDelqDt;
	}

	public void setDfltHistFtInEps3mDelqDt(Date dfltHistFtInEps3mDelqDt) {
		this.dfltHistFtInEps3mDelqDt = dfltHistFtInEps3mDelqDt;
	}

    
    @Column(name="DIR_LENDING_BRANCH_IND", length=16)
	public String getDirLendingBranchInd() {
		return this.dirLendingBranchInd;
	}

	public void setDirLendingBranchInd(String dirLendingBranchInd) {
		this.dirLendingBranchInd = dirLendingBranchInd;
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

    
    @Column(name="EFFECTIVE_FICO_SCORE")
	public Integer getEffectiveFicoScore() {
		return this.effectiveFicoScore;
	}

	public void setEffectiveFicoScore(Integer effectiveFicoScore) {
		this.effectiveFicoScore = effectiveFicoScore;
	}

    
    @Column(name="END_CD", length=16)
	public String getEndCd() {
		return this.endCd;
	}

	public void setEndCd(String endCd) {
		this.endCd = endCd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ENDRSMNT_DT", length=23)
	public Date getEndrsmntDt() {
		return this.endrsmntDt;
	}

	public void setEndrsmntDt(Date endrsmntDt) {
		this.endrsmntDt = endrsmntDt;
	}

    
    @Column(name="ENDRSMNT_RVW_PRSNNL_ID", length=36)
	public String getEndrsmntRvwPrsnnlId() {
		return this.endrsmntRvwPrsnnlId;
	}

	public void setEndrsmntRvwPrsnnlId(String endrsmntRvwPrsnnlId) {
		this.endrsmntRvwPrsnnlId = endrsmntRvwPrsnnlId;
	}

    
    @Column(name="ENERGY_EFF_MRTG", length=3)
	public String getEnergyEffMrtg() {
		return this.energyEffMrtg;
	}

	public void setEnergyEffMrtg(String energyEffMrtg) {
		this.energyEffMrtg = energyEffMrtg;
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

    
    @Column(name="FHAC_ADDR_CHG", length=1)
	public Character getFhacAddrChg() {
		return this.fhacAddrChg;
	}

	public void setFhacAddrChg(Character fhacAddrChg) {
		this.fhacAddrChg = fhacAddrChg;
	}

    
    @Column(name="FICO_SCORE")
	public Integer getFicoScore() {
		return this.ficoScore;
	}

	public void setFicoScore(Integer ficoScore) {
		this.ficoScore = ficoScore;
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

    
    @Column(name="FT_IN_EPS_2MNTH_DELQ_IND", length=1)
	public Character getFtInEps2mnthDelqInd() {
		return this.ftInEps2mnthDelqInd;
	}

	public void setFtInEps2mnthDelqInd(Character ftInEps2mnthDelqInd) {
		this.ftInEps2mnthDelqInd = ftInEps2mnthDelqInd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FT_IN_EPS_3MNTH_DELQ_DT", length=23)
	public Date getFtInEps3mnthDelqDt() {
		return this.ftInEps3mnthDelqDt;
	}

	public void setFtInEps3mnthDelqDt(Date ftInEps3mnthDelqDt) {
		this.ftInEps3mnthDelqDt = ftInEps3mnthDelqDt;
	}

    
    @Column(name="FT_IN_EPS_3MNTH_DELQ_IND", length=1)
	public Character getFtInEps3mnthDelqInd() {
		return this.ftInEps3mnthDelqInd;
	}

	public void setFtInEps3mnthDelqInd(Character ftInEps3mnthDelqInd) {
		this.ftInEps3mnthDelqInd = ftInEps3mnthDelqInd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FTE_2MNTH_DELQ_DT", length=23)
	public Date getFte2mnthDelqDt() {
		return this.fte2mnthDelqDt;
	}

	public void setFte2mnthDelqDt(Date fte2mnthDelqDt) {
		this.fte2mnthDelqDt = fte2mnthDelqDt;
	}

    
    @Column(name="GIFT_LTR_AMT")
	public Integer getGiftLtrAmt() {
		return this.giftLtrAmt;
	}

	public void setGiftLtrAmt(Integer giftLtrAmt) {
		this.giftLtrAmt = giftLtrAmt;
	}

    
    @Column(name="GIFT_LTR_2_AMT")
	public Integer getGiftLtr2Amt() {
		return this.giftLtr2Amt;
	}

	public void setGiftLtr2Amt(Integer giftLtr2Amt) {
		this.giftLtr2Amt = giftLtr2Amt;
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

    
    @Column(name="HARDCOPY_DOCS_IND", length=1)
	public Character getHardcopyDocsInd() {
		return this.hardcopyDocsInd;
	}

	public void setHardcopyDocsInd(Character hardcopyDocsInd) {
		this.hardcopyDocsInd = hardcopyDocsInd;
	}

    
    @Column(name="HECM_ASSETS")
	public Integer getHecmAssets() {
		return this.hecmAssets;
	}

	public void setHecmAssets(Integer hecmAssets) {
		this.hecmAssets = hecmAssets;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="HECM_COUNSEL_DT", length=23)
	public Date getHecmCounselDt() {
		return this.hecmCounselDt;
	}

	public void setHecmCounselDt(Date hecmCounselDt) {
		this.hecmCounselDt = hecmCounselDt;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATE_DATE", length=23)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    
    @Column(name="CREATE_PRSNNL_ID", length=36)
	public String getCreatePrsnnlId() {
		return this.createPrsnnlId;
	}

	public void setCreatePrsnnlId(String createPrsnnlId) {
		this.createPrsnnlId = createPrsnnlId;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATE_DATE", length=23)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

    
    @Column(name="UPDATE_PRSNNL_ID", length=36)
	public String getUpdatePrsnnlId() {
		return this.updatePrsnnlId;
	}

	public void setUpdatePrsnnlId(String updatePrsnnlId) {
		this.updatePrsnnlId = updatePrsnnlId;
	}

    
    @Column(name="HECM_LIENS")
	public Integer getHecmLiens() {
		return this.hecmLiens;
	}

	public void setHecmLiens(Integer hecmLiens) {
		this.hecmLiens = hecmLiens;
	}

    
    @Column(name="HECM_MNTHLY_INC")
	public Integer getHecmMnthlyInc() {
		return this.hecmMnthlyInc;
	}

	public void setHecmMnthlyInc(Integer hecmMnthlyInc) {
		this.hecmMnthlyInc = hecmMnthlyInc;
	}

    
    @Column(name="HECM_PRNCPL_LMT")
	public Integer getHecmPrncplLmt() {
		return this.hecmPrncplLmt;
	}

	public void setHecmPrncplLmt(Integer hecmPrncplLmt) {
		this.hecmPrncplLmt = hecmPrncplLmt;
	}

    
    @Column(name="HLDR_MTGEE10_A43C", length=10)
	public String getHldrMtgee10A43c() {
		return this.hldrMtgee10A43c;
	}

	public void setHldrMtgee10A43c(String hldrMtgee10A43c) {
		this.hldrMtgee10A43c = hldrMtgee10A43c;
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

    
    @Column(name="INIT_DISBURSEMENT_LIMIT")
	public Integer getInitDisbursementLimit() {
		return this.initDisbursementLimit;
	}

	public void setInitDisbursementLimit(Integer initDisbursementLimit) {
		this.initDisbursementLimit = initDisbursementLimit;
	}

    
    @Column(name="INIT_FEE")
	public Integer getInitFee() {
		return this.initFee;
	}

	public void setInitFee(Integer initFee) {
		this.initFee = initFee;
	}

    
    @Column(name="INIT_MIP_FACTOR")
	public Integer getInitMipFactor() {
		return this.initMipFactor;
	}

	public void setInitMipFactor(Integer initMipFactor) {
		this.initMipFactor = initMipFactor;
	}

    
    @Column(name="INSRNC_STATUS_CD", length=16)
	public String getInsrncStatusCd() {
		return this.insrncStatusCd;
	}

	public void setInsrncStatusCd(String insrncStatusCd) {
		this.insrncStatusCd = insrncStatusCd;
	}

    
    @Column(name="INT_RT", precision=5, scale=3)
	public BigDecimal getIntRt() {
		return this.intRt;
	}

	public void setIntRt(BigDecimal intRt) {
		this.intRt = intRt;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LAST_CS_SCORE_DT", length=23)
	public Date getLastCsScoreDt() {
		return this.lastCsScoreDt;
	}

	public void setLastCsScoreDt(Date lastCsScoreDt) {
		this.lastCsScoreDt = lastCsScoreDt;
	}

    
    @Column(name="MISC_LNDR_INSRNC_IND", length=13)
	public String getMiscLndrInsrncInd() {
		return this.miscLndrInsrncInd;
	}

	public void setMiscLndrInsrncInd(String miscLndrInsrncInd) {
		this.miscLndrInsrncInd = miscLndrInsrncInd;
	}

    
    @Column(name="LE_COMPOUND_RATE")
	public Integer getLeCompoundRate() {
		return this.leCompoundRate;
	}

	public void setLeCompoundRate(Integer leCompoundRate) {
		this.leCompoundRate = leCompoundRate;
	}

    
    @Column(name="LE_EXPECTED_RATE")
	public Integer getLeExpectedRate() {
		return this.leExpectedRate;
	}

	public void setLeExpectedRate(Integer leExpectedRate) {
		this.leExpectedRate = leExpectedRate;
	}

    
    @Column(name="LE_PROJECTED_AMT")
	public Integer getLeProjectedAmt() {
		return this.leProjectedAmt;
	}

	public void setLeProjectedAmt(Integer leProjectedAmt) {
		this.leProjectedAmt = leProjectedAmt;
	}

    
    @Column(name="LESA_FUNDING_AMT")
	public Integer getLesaFundingAmt() {
		return this.lesaFundingAmt;
	}

	public void setLesaFundingAmt(Integer lesaFundingAmt) {
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
	public Integer getLeSetasideAmt() {
		return this.leSetasideAmt;
	}

	public void setLeSetasideAmt(Integer leSetasideAmt) {
		this.leSetasideAmt = leSetasideAmt;
	}

    
    @Column(name="LE_TALC_MONTHS")
	public Integer getLeTalcMonths() {
		return this.leTalcMonths;
	}

	public void setLeTalcMonths(Integer leTalcMonths) {
		this.leTalcMonths = leTalcMonths;
	}

    
    @Column(name="MONTHLY_P_I")
	public Integer getMonthlyPI() {
		return this.monthlyPI;
	}

	public void setMonthlyPI(Integer monthlyPI) {
		this.monthlyPI = monthlyPI;
	}

    
    @Column(name="LOAN_NBR", length=30)
	public String getLoanNbr() {
		return this.loanNbr;
	}

	public void setLoanNbr(String loanNbr) {
		this.loanNbr = loanNbr;
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

    
    @Column(name="PROP_TYP", length=16)
	public String getPropTyp() {
		return this.propTyp;
	}

	public void setPropTyp(String propTyp) {
		this.propTyp = propTyp;
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

    
    @Column(name="LTV_CAT", length=16)
	public String getLtvCat() {
		return this.ltvCat;
	}

	public void setLtvCat(String ltvCat) {
		this.ltvCat = ltvCat;
	}

    
    @Column(name="LTV_CAT_NEW", length=16)
	public String getLtvCatNew() {
		return this.ltvCatNew;
	}

	public void setLtvCatNew(String ltvCatNew) {
		this.ltvCatNew = ltvCatNew;
	}

    
    @Column(name="LTV_CAT_OLD", length=16)
	public String getLtvCatOld() {
		return this.ltvCatOld;
	}

	public void setLtvCatOld(String ltvCatOld) {
		this.ltvCatOld = ltvCatOld;
	}

    
    @Column(name="RATIO_LOAN_TO_VL_NEW", precision=7)
	public BigDecimal getRatioLoanToVlNew() {
		return this.ratioLoanToVlNew;
	}

	public void setRatioLoanToVlNew(BigDecimal ratioLoanToVlNew) {
		this.ratioLoanToVlNew = ratioLoanToVlNew;
	}

    
    @Column(name="RATIO_LOAN_TO_VL_OLD", precision=7)
	public BigDecimal getRatioLoanToVlOld() {
		return this.ratioLoanToVlOld;
	}

	public void setRatioLoanToVlOld(BigDecimal ratioLoanToVlOld) {
		this.ratioLoanToVlOld = ratioLoanToVlOld;
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

    
    @Column(name="MNDTRY_OBLGTNS_AMT")
	public Integer getMndtryOblgtnsAmt() {
		return this.mndtryOblgtnsAmt;
	}

	public void setMndtryOblgtnsAmt(Integer mndtryOblgtnsAmt) {
		this.mndtryOblgtnsAmt = mndtryOblgtnsAmt;
	}

    
    @Column(name="MANDATORY_OBLIGS_AMT")
	public Integer getMandatoryObligsAmt() {
		return this.mandatoryObligsAmt;
	}

	public void setMandatoryObligsAmt(Integer mandatoryObligsAmt) {
		this.mandatoryObligsAmt = mandatoryObligsAmt;
	}

    
    @Column(name="MARRIED_TO_NBS_IND", length=1)
	public Character getMarriedToNbsInd() {
		return this.marriedToNbsInd;
	}

	public void setMarriedToNbsInd(Character marriedToNbsInd) {
		this.marriedToNbsInd = marriedToNbsInd;
	}

    
    @Column(name="CURR_MNTHLY_MIP", precision=7)
	public BigDecimal getCurrMnthlyMip() {
		return this.currMnthlyMip;
	}

	public void setCurrMnthlyMip(BigDecimal currMnthlyMip) {
		this.currMnthlyMip = currMnthlyMip;
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

    
    @Column(name="RANDOM_NUMBER", precision=3, scale=0)
	public Short getRandomNumber() {
		return this.randomNumber;
	}

	public void setRandomNumber(Short randomNumber) {
		this.randomNumber = randomNumber;
	}

    
    @Column(name="ME_DEBT_AMT")
	public Integer getMeDebtAmt() {
		return this.meDebtAmt;
	}

	public void setMeDebtAmt(Integer meDebtAmt) {
		this.meDebtAmt = meDebtAmt;
	}

    
    @Column(name="ME_OTHER_AMT")
	public Integer getMeOtherAmt() {
		return this.meOtherAmt;
	}

	public void setMeOtherAmt(Integer meOtherAmt) {
		this.meOtherAmt = meOtherAmt;
	}

    
    @Column(name="ME_RE_AMT")
	public Integer getMeReAmt() {
		return this.meReAmt;
	}

	public void setMeReAmt(Integer meReAmt) {
		this.meReAmt = meReAmt;
	}

    
    @Column(name="ME_TOTAL_AMT")
	public Integer getMeTotalAmt() {
		return this.meTotalAmt;
	}

	public void setMeTotalAmt(Integer meTotalAmt) {
		this.meTotalAmt = meTotalAmt;
	}

    
    @Column(name="MI_IMPUTED_AMT")
	public Integer getMiImputedAmt() {
		return this.miImputedAmt;
	}

	public void setMiImputedAmt(Integer miImputedAmt) {
		this.miImputedAmt = miImputedAmt;
	}

    
    @Column(name="MI_OTHER_AMT")
	public Integer getMiOtherAmt() {
		return this.miOtherAmt;
	}

	public void setMiOtherAmt(Integer miOtherAmt) {
		this.miOtherAmt = miOtherAmt;
	}

    
    @Column(name="MI_TOTAL_AMT")
	public Integer getMiTotalAmt() {
		return this.miTotalAmt;
	}

	public void setMiTotalAmt(Integer miTotalAmt) {
		this.miTotalAmt = miTotalAmt;
	}

    
    @Column(name="MNTHLY_SET_ASIDE")
	public Integer getMnthlySetAside() {
		return this.mnthlySetAside;
	}

	public void setMnthlySetAside(Integer mnthlySetAside) {
		this.mnthlySetAside = mnthlySetAside;
	}

    
    @Column(name="MORT_EXCLD_FNCD_MIP", precision=9)
	public BigDecimal getMortExcldFncdMip() {
		return this.mortExcldFncdMip;
	}

	public void setMortExcldFncdMip(BigDecimal mortExcldFncdMip) {
		this.mortExcldFncdMip = mortExcldFncdMip;
	}

    
    @Column(name="MIP_FINANCED_IND", length=1)
	public Character getMipFinancedInd() {
		return this.mipFinancedInd;
	}

	public void setMipFinancedInd(Character mipFinancedInd) {
		this.mipFinancedInd = mipFinancedInd;
	}

    
    @Column(name="NBR_MONTHS", precision=3, scale=0)
	public Short getNbrMonths() {
		return this.nbrMonths;
	}

	public void setNbrMonths(Short nbrMonths) {
		this.nbrMonths = nbrMonths;
	}

    
    @Column(name="NBS_FIRST_MIDDLE_LAST", length=150)
	public String getNbsFirstMiddleLast() {
		return this.nbsFirstMiddleLast;
	}

	public void setNbsFirstMiddleLast(String nbsFirstMiddleLast) {
		this.nbsFirstMiddleLast = nbsFirstMiddleLast;
	}

    
    @Column(name="NBRHD_PCT_OWNED")
	public Integer getNbrhdPctOwned() {
		return this.nbrhdPctOwned;
	}

	public void setNbrhdPctOwned(Integer nbrhdPctOwned) {
		this.nbrhdPctOwned = nbrhdPctOwned;
	}

    
    @Column(name="NBRHD_PRICE")
	public Integer getNbrhdPrice() {
		return this.nbrhdPrice;
	}

	public void setNbrhdPrice(Integer nbrhdPrice) {
		this.nbrhdPrice = nbrhdPrice;
	}

    
    @Column(name="MAX_CLAIM_AMT_NEW")
	public Integer getMaxClaimAmtNew() {
		return this.maxClaimAmtNew;
	}

	public void setMaxClaimAmtNew(Integer maxClaimAmtNew) {
		this.maxClaimAmtNew = maxClaimAmtNew;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="NOR_ISS_RPT_DT", length=23)
	public Date getNorIssRptDt() {
		return this.norIssRptDt;
	}

	public void setNorIssRptDt(Date norIssRptDt) {
		this.norIssRptDt = norIssRptDt;
	}

    
    @Column(name="NBR_BTHRMS", precision=3, scale=1)
	public BigDecimal getNbrBthrms() {
		return this.nbrBthrms;
	}

	public void setNbrBthrms(BigDecimal nbrBthrms) {
		this.nbrBthrms = nbrBthrms;
	}

    
    @Column(name="NBR_BDRM", precision=3, scale=0)
	public Short getNbrBdrm() {
		return this.nbrBdrm;
	}

	public void setNbrBdrm(Short nbrBdrm) {
		this.nbrBdrm = nbrBdrm;
	}

    
    @Column(name="NBR_RMS", precision=3, scale=0)
	public Short getNbrRms() {
		return this.nbrRms;
	}

	public void setNbrRms(Short nbrRms) {
		this.nbrRms = nbrRms;
	}

    
    @Column(name="NUM_LIVING_UNITS", precision=3, scale=0)
	public Short getNumLivingUnits() {
		return this.numLivingUnits;
	}

	public void setNumLivingUnits(Short numLivingUnits) {
		this.numLivingUnits = numLivingUnits;
	}

    
    @Column(name="OCPNCY_STS", length=16)
	public String getOcpncySts() {
		return this.ocpncySts;
	}

	public void setOcpncySts(String ocpncySts) {
		this.ocpncySts = ocpncySts;
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

    
    @Column(name="MAX_CLAIM_AMT_OLD")
	public Integer getMaxClaimAmtOld() {
		return this.maxClaimAmtOld;
	}

	public void setMaxClaimAmtOld(Integer maxClaimAmtOld) {
		this.maxClaimAmtOld = maxClaimAmtOld;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="OLDST_UNPD_DT", length=23)
	public Date getOldstUnpdDt() {
		return this.oldstUnpdDt;
	}

	public void setOldstUnpdDt(Date oldstUnpdDt) {
		this.oldstUnpdDt = oldstUnpdDt;
	}

    
    @Column(name="ORIG_MRTG_AMT", precision=9)
	public BigDecimal getOrigMrtgAmt() {
		return this.origMrtgAmt;
	}

	public void setOrigMrtgAmt(BigDecimal origMrtgAmt) {
		this.origMrtgAmt = origMrtgAmt;
	}

    
    @Column(name="ORGNTNG_MTGEE5", length=5)
	public String getOrgntngMtgee5() {
		return this.orgntngMtgee5;
	}

	public void setOrgntngMtgee5(String orgntngMtgee5) {
		this.orgntngMtgee5 = orgntngMtgee5;
	}

    
    @Column(name="ORGNTNG_MTGEE10_ID", length=10)
	public String getOrgntngMtgee10Id() {
		return this.orgntngMtgee10Id;
	}

	public void setOrgntngMtgee10Id(String orgntngMtgee10Id) {
		this.orgntngMtgee10Id = orgntngMtgee10Id;
	}

    
    @Column(name="TYP_ORGNTNG_MTGEE", length=16)
	public String getTypOrgntngMtgee() {
		return this.typOrgntngMtgee;
	}

	public void setTypOrgntngMtgee(String typOrgntngMtgee) {
		this.typOrgntngMtgee = typOrgntngMtgee;
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

    
    @Column(name="PRE_RVW_DCSN", length=16)
	public String getPreRvwDcsn() {
		return this.preRvwDcsn;
	}

	public void setPreRvwDcsn(String preRvwDcsn) {
		this.preRvwDcsn = preRvwDcsn;
	}

    
    @Column(name="PRE_CLSNG_IND", length=3)
	public String getPreClsngInd() {
		return this.preClsngInd;
	}

	public void setPreClsngInd(String preClsngInd) {
		this.preClsngInd = preClsngInd;
	}

    
    @Column(name="PREQUAL_OUTPUT", length=16)
	public String getPrequalOutput() {
		return this.prequalOutput;
	}

	public void setPrequalOutput(String prequalOutput) {
		this.prequalOutput = prequalOutput;
	}

    
    @Column(name="PREV_COMPLT_SBDVSN_IND", length=1)
	public Character getPrevCompltSbdvsnInd() {
		return this.prevCompltSbdvsnInd;
	}

	public void setPrevCompltSbdvsnInd(Character prevCompltSbdvsnInd) {
		this.prevCompltSbdvsnInd = prevCompltSbdvsnInd;
	}

    
    @Column(name="PRNCPL_RDCTN_AMT", precision=9)
	public BigDecimal getPrncplRdctnAmt() {
		return this.prncplRdctnAmt;
	}

	public void setPrncplRdctnAmt(BigDecimal prncplRdctnAmt) {
		this.prncplRdctnAmt = prncplRdctnAmt;
	}

    
    @Column(name="PRIOR_SALE_RQRD_IND", length=1)
	public Character getPriorSaleRqrdInd() {
		return this.priorSaleRqrdInd;
	}

	public void setPriorSaleRqrdInd(Character priorSaleRqrdInd) {
		this.priorSaleRqrdInd = priorSaleRqrdInd;
	}

    
    @Column(name="PRCSNG_TYP", length=16)
	public String getPrcsngTyp() {
		return this.prcsngTyp;
	}

	public void setPrcsngTyp(String prcsngTyp) {
		this.prcsngTyp = prcsngTyp;
	}

    
    @Column(name="PRODUCT_TYPE", length=16)
	public String getProductType() {
		return this.productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

    
    @Column(name="PROG_ID_F17", length=16)
	public String getProgIdF17() {
		return this.progIdF17;
	}

	public void setProgIdF17(String progIdF17) {
		this.progIdF17 = progIdF17;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DT_ACQ", length=23)
	public Date getDtAcq() {
		return this.dtAcq;
	}

	public void setDtAcq(Date dtAcq) {
		this.dtAcq = dtAcq;
	}

    
    @Column(name="PROP_ADDR_ST", length=10)
	public String getPropAddrSt() {
		return this.propAddrSt;
	}

	public void setPropAddrSt(String propAddrSt) {
		this.propAddrSt = propAddrSt;
	}

    
    @Column(name="PRPRTY_APRSL_VL", precision=9)
	public BigDecimal getPrprtyAprslVl() {
		return this.prprtyAprslVl;
	}

	public void setPrprtyAprslVl(BigDecimal prprtyAprslVl) {
		this.prprtyAprslVl = prprtyAprslVl;
	}

    
    @Column(name="PC_CONDO_FEE_AMT")
	public Integer getPcCondoFeeAmt() {
		return this.pcCondoFeeAmt;
	}

	public void setPcCondoFeeAmt(Integer pcCondoFeeAmt) {
		this.pcCondoFeeAmt = pcCondoFeeAmt;
	}

    
    @Column(name="PC_FLOOD_INS_AMT")
	public Integer getPcFloodInsAmt() {
		return this.pcFloodInsAmt;
	}

	public void setPcFloodInsAmt(Integer pcFloodInsAmt) {
		this.pcFloodInsAmt = pcFloodInsAmt;
	}

    
    @Column(name="PC_HAZ_INS_AMT")
	public Integer getPcHazInsAmt() {
		return this.pcHazInsAmt;
	}

	public void setPcHazInsAmt(Integer pcHazInsAmt) {
		this.pcHazInsAmt = pcHazInsAmt;
	}

    
    @Column(name="PC_OTHER_AMT")
	public Integer getPcOtherAmt() {
		return this.pcOtherAmt;
	}

	public void setPcOtherAmt(Integer pcOtherAmt) {
		this.pcOtherAmt = pcOtherAmt;
	}

    
    @Column(name="PC_RE_TAX_AMT")
	public Integer getPcReTaxAmt() {
		return this.pcReTaxAmt;
	}

	public void setPcReTaxAmt(Integer pcReTaxAmt) {
		this.pcReTaxAmt = pcReTaxAmt;
	}

    
    @Column(name="PC_SETASIDE_TOT_AMT")
	public Integer getPcSetasideTotAmt() {
		return this.pcSetasideTotAmt;
	}

	public void setPcSetasideTotAmt(Integer pcSetasideTotAmt) {
		this.pcSetasideTotAmt = pcSetasideTotAmt;
	}

    
    @Column(name="PC_TOTAL_AMT")
	public Integer getPcTotalAmt() {
		return this.pcTotalAmt;
	}

	public void setPcTotalAmt(Integer pcTotalAmt) {
		this.pcTotalAmt = pcTotalAmt;
	}

    
    @Column(name="PRPRTY_CNVRSN_TYP", length=16)
	public String getPrprtyCnvrsnTyp() {
		return this.prprtyCnvrsnTyp;
	}

	public void setPrprtyCnvrsnTyp(String prprtyCnvrsnTyp) {
		this.prprtyCnvrsnTyp = prprtyCnvrsnTyp;
	}

    
    @Column(name="PD_STRMLN_FLG", length=3)
	public String getPdStrmlnFlg() {
		return this.pdStrmlnFlg;
	}

	public void setPdStrmlnFlg(String pdStrmlnFlg) {
		this.pdStrmlnFlg = pdStrmlnFlg;
	}

    
    @Column(name="PYMTS_BFR_FRST_MISSED_PYMT", precision=3, scale=0)
	public Short getPymtsBfrFrstMissedPymt() {
		return this.pymtsBfrFrstMissedPymt;
	}

	public void setPymtsBfrFrstMissedPymt(Short pymtsBfrFrstMissedPymt) {
		this.pymtsBfrFrstMissedPymt = pymtsBfrFrstMissedPymt;
	}

    
    @Column(name="RATIO_ORE_TEI", precision=5)
	public BigDecimal getRatioOreTei() {
		return this.ratioOreTei;
	}

	public void setRatioOreTei(BigDecimal ratioOreTei) {
		this.ratioOreTei = ratioOreTei;
	}

    
    @Column(name="RATIO_FIX_TEI", precision=5)
	public BigDecimal getRatioFixTei() {
		return this.ratioFixTei;
	}

	public void setRatioFixTei(BigDecimal ratioFixTei) {
		this.ratioFixTei = ratioFixTei;
	}

    
    @Column(name="PTI_CAT", length=5)
	public String getPtiCat() {
		return this.ptiCat;
	}

	public void setPtiCat(String ptiCat) {
		this.ptiCat = ptiCat;
	}

    
    @Column(name="RATIO_TMP_TEI", precision=7)
	public BigDecimal getRatioTmpTei() {
		return this.ratioTmpTei;
	}

	public void setRatioTmpTei(BigDecimal ratioTmpTei) {
		this.ratioTmpTei = ratioTmpTei;
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

    
    @Column(name="RFNC_CD", length=16)
	public String getRfncCd() {
		return this.rfncCd;
	}

	public void setRfncCd(String rfncCd) {
		this.rfncCd = rfncCd;
	}

    
    @Column(name="RFNC_IND", length=3)
	public String getRfncInd() {
		return this.rfncInd;
	}

	public void setRfncInd(String rfncInd) {
		this.rfncInd = rfncInd;
	}

    
    @Column(name="RFNC_NEXT_CASE_NBR", length=11)
	public String getRfncNextCaseNbr() {
		return this.rfncNextCaseNbr;
	}

	public void setRfncNextCaseNbr(String rfncNextCaseNbr) {
		this.rfncNextCaseNbr = rfncNextCaseNbr;
	}

    
    @Column(name="RPR_SET_ASIDE")
	public Integer getRprSetAside() {
		return this.rprSetAside;
	}

	public void setRprSetAside(Integer rprSetAside) {
		this.rprSetAside = rprSetAside;
	}

    
    @Column(name="RQRD_INVEST", precision=9)
	public BigDecimal getRqrdInvest() {
		return this.rqrdInvest;
	}

	public void setRqrdInvest(BigDecimal rqrdInvest) {
		this.rqrdInvest = rqrdInvest;
	}

    
    @Column(name="RI_FAMILY_SIZE", precision=3, scale=0)
	public Short getRiFamilySize() {
		return this.riFamilySize;
	}

	public void setRiFamilySize(Short riFamilySize) {
		this.riFamilySize = riFamilySize;
	}

    
    @Column(name="RI_SHORTFALL_AMT")
	public Integer getRiShortfallAmt() {
		return this.riShortfallAmt;
	}

	public void setRiShortfallAmt(Integer riShortfallAmt) {
		this.riShortfallAmt = riShortfallAmt;
	}

    
    @Column(name="RI_STANDARD_AMT")
	public Integer getRiStandardAmt() {
		return this.riStandardAmt;
	}

	public void setRiStandardAmt(Integer riStandardAmt) {
		this.riStandardAmt = riStandardAmt;
	}

    
    @Column(name="RI_TOTAL_AMT")
	public Integer getRiTotalAmt() {
		return this.riTotalAmt;
	}

	public void setRiTotalAmt(Integer riTotalAmt) {
		this.riTotalAmt = riTotalAmt;
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

    
    @Column(name="NBRHD_CD", length=16)
	public String getNbrhdCd() {
		return this.nbrhdCd;
	}

	public void setNbrhdCd(String nbrhdCd) {
		this.nbrhdCd = nbrhdCd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="RCV_SALE_DT", length=23)
	public Date getRcvSaleDt() {
		return this.rcvSaleDt;
	}

	public void setRcvSaleDt(Date rcvSaleDt) {
		this.rcvSaleDt = rcvSaleDt;
	}

    
    @Column(name="SCNDRY_FNC_SRC", length=16)
	public String getScndryFncSrc() {
		return this.scndryFncSrc;
	}

	public void setScndryFncSrc(String scndryFncSrc) {
		this.scndryFncSrc = scndryFncSrc;
	}

    
    @Column(name="SECONDARY_FINANCE_AMT")
	public Integer getSecondaryFinanceAmt() {
		return this.secondaryFinanceAmt;
	}

	public void setSecondaryFinanceAmt(Integer secondaryFinanceAmt) {
		this.secondaryFinanceAmt = secondaryFinanceAmt;
	}

    
    @Column(name="SELLER_CNTRBTN", precision=9)
	public BigDecimal getSellerCntrbtn() {
		return this.sellerCntrbtn;
	}

	public void setSellerCntrbtn(BigDecimal sellerCntrbtn) {
		this.sellerCntrbtn = sellerCntrbtn;
	}

    
    @Column(name="SEND_MIC_IND", length=1)
	public Character getSendMicInd() {
		return this.sendMicInd;
	}

	public void setSendMicInd(Character sendMicInd) {
		this.sendMicInd = sendMicInd;
	}

    
    @Column(name="OLD_SRVCR_MTGEE", length=5)
	public String getOldSrvcrMtgee() {
		return this.oldSrvcrMtgee;
	}

	public void setOldSrvcrMtgee(String oldSrvcrMtgee) {
		this.oldSrvcrMtgee = oldSrvcrMtgee;
	}

    
    @Column(name="SRVCR_MTGEE10_A43C", length=10)
	public String getSrvcrMtgee10A43c() {
		return this.srvcrMtgee10A43c;
	}

	public void setSrvcrMtgee10A43c(String srvcrMtgee10A43c) {
		this.srvcrMtgee10A43c = srvcrMtgee10A43c;
	}

    
    @Column(name="SRVCR_MTGEE5_A43", length=5)
	public String getSrvcrMtgee5A43() {
		return this.srvcrMtgee5A43;
	}

	public void setSrvcrMtgee5A43(String srvcrMtgee5A43) {
		this.srvcrMtgee5A43 = srvcrMtgee5A43;
	}

    
    @Column(name="SFPCS_MTGEE_ID", length=5)
	public String getSfpcsMtgeeId() {
		return this.sfpcsMtgeeId;
	}

	public void setSfpcsMtgeeId(String sfpcsMtgeeId) {
		this.sfpcsMtgeeId = sfpcsMtgeeId;
	}

    
    @Column(name="SNGL_DSBRSE_LMP_SUM_PMT_OPT")
	public Integer getSnglDsbrseLmpSumPmtOpt() {
		return this.snglDsbrseLmpSumPmtOpt;
	}

	public void setSnglDsbrseLmpSumPmtOpt(Integer snglDsbrseLmpSumPmtOpt) {
		this.snglDsbrseLmpSumPmtOpt = snglDsbrseLmpSumPmtOpt;
	}

    
    @Column(name="PCT_1_FMLY")
	public Integer getPct1Fmly() {
		return this.pct1Fmly;
	}

	public void setPct1Fmly(Integer pct1Fmly) {
		this.pct1Fmly = pct1Fmly;
	}

    
    @Column(name="SOA_CD", length=16)
	public String getSoaCd() {
		return this.soaCd;
	}

	public void setSoaCd(String soaCd) {
		this.soaCd = soaCd;
	}

    
    @Column(name="SPNSR_MTGEE10_ID", length=10)
	public String getSpnsrMtgee10Id() {
		return this.spnsrMtgee10Id;
	}

	public void setSpnsrMtgee10Id(String spnsrMtgee10Id) {
		this.spnsrMtgee10Id = spnsrMtgee10Id;
	}

    
    @Column(name="SQNC_NBR")
	public Integer getSqncNbr() {
		return this.sqncNbr;
	}

	public void setSqncNbr(Integer sqncNbr) {
		this.sqncNbr = sqncNbr;
	}

    
    @Column(name="STRMNLN_REFI_TYPE", length=1)
	public Character getStrmnlnRefiType() {
		return this.strmnlnRefiType;
	}

	public void setStrmnlnRefiType(Character strmnlnRefiType) {
		this.strmnlnRefiType = strmnlnRefiType;
	}

    
    @Column(name="STRT_STOP_EPS_IND", length=2)
	public String getStrtStopEpsInd() {
		return this.strtStopEpsInd;
	}

	public void setStrtStopEpsInd(String strtStopEpsInd) {
		this.strtStopEpsInd = strtStopEpsInd;
	}

    
    @Column(name="SBDVSN_SPOT_LOT", length=16)
	public String getSbdvsnSpotLot() {
		return this.sbdvsnSpotLot;
	}

	public void setSbdvsnSpotLot(String sbdvsnSpotLot) {
		this.sbdvsnSpotLot = sbdvsnSpotLot;
	}

    
    @Column(name="TAX_INSUR_YR_1_PMTS")
	public Integer getTaxInsurYr1Pmts() {
		return this.taxInsurYr1Pmts;
	}

	public void setTaxInsurYr1Pmts(Integer taxInsurYr1Pmts) {
		this.taxInsurYr1Pmts = taxInsurYr1Pmts;
	}

    
    @Column(name="TAXES_INSRNC_FRST_YR_AMT")
	public Integer getTaxesInsrncFrstYrAmt() {
		return this.taxesInsrncFrstYrAmt;
	}

	public void setTaxesInsrncFrstYrAmt(Integer taxesInsrncFrstYrAmt) {
		this.taxesInsrncFrstYrAmt = taxesInsrncFrstYrAmt;
	}

    
    @Column(name="TRV_SLCT_RSN_CD", length=16)
	public String getTrvSlctRsnCd() {
		return this.trvSlctRsnCd;
	}

	public void setTrvSlctRsnCd(String trvSlctRsnCd) {
		this.trvSlctRsnCd = trvSlctRsnCd;
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

    
    @Column(name="TOT_ANN_EFF_INCM", precision=9)
	public BigDecimal getTotAnnEffIncm() {
		return this.totAnnEffIncm;
	}

	public void setTotAnnEffIncm(BigDecimal totAnnEffIncm) {
		this.totAnnEffIncm = totAnnEffIncm;
	}

    
    @Column(name="TOT_ASSETS", precision=9)
	public BigDecimal getTotAssets() {
		return this.totAssets;
	}

	public void setTotAssets(BigDecimal totAssets) {
		this.totAssets = totAssets;
	}

    
    @Column(name="TOT_CLSNG_CSTS", precision=9)
	public BigDecimal getTotClsngCsts() {
		return this.totClsngCsts;
	}

	public void setTotClsngCsts(BigDecimal totClsngCsts) {
		this.totClsngCsts = totClsngCsts;
	}

    
    @Column(name="TOT_FIXED_PYMT", precision=9)
	public BigDecimal getTotFixedPymt() {
		return this.totFixedPymt;
	}

	public void setTotFixedPymt(BigDecimal totFixedPymt) {
		this.totFixedPymt = totFixedPymt;
	}

    
    @Column(name="TOT_MNTHLY_EFF_INCM", precision=9)
	public BigDecimal getTotMnthlyEffIncm() {
		return this.totMnthlyEffIncm;
	}

	public void setTotMnthlyEffIncm(BigDecimal totMnthlyEffIncm) {
		this.totMnthlyEffIncm = totMnthlyEffIncm;
	}

    
    @Column(name="TOT_MNTHLY_MTG_PYMT", precision=9)
	public BigDecimal getTotMnthlyMtgPymt() {
		return this.totMnthlyMtgPymt;
	}

	public void setTotMnthlyMtgPymt(BigDecimal totMnthlyMtgPymt) {
		this.totMnthlyMtgPymt = totMnthlyMtgPymt;
	}

    
    @Column(name="TOT_ACQ_COSTS", precision=9)
	public BigDecimal getTotAcqCosts() {
		return this.totAcqCosts;
	}

	public void setTotAcqCosts(BigDecimal totAcqCosts) {
		this.totAcqCosts = totAcqCosts;
	}

    
    @Column(name="TRNSMSN_TYP", length=16)
	public String getTrnsmsnTyp() {
		return this.trnsmsnTyp;
	}

	public void setTrnsmsnTyp(String trnsmsnTyp) {
		this.trnsmsnTyp = trnsmsnTyp;
	}

    
    @Column(name="UFMIP_PD_AMT", precision=9)
	public BigDecimal getUfmipPdAmt() {
		return this.ufmipPdAmt;
	}

	public void setUfmipPdAmt(BigDecimal ufmipPdAmt) {
		this.ufmipPdAmt = ufmipPdAmt;
	}

    
    @Column(name="UFMIP_PD_CASH", precision=9)
	public BigDecimal getUfmipPdCash() {
		return this.ufmipPdCash;
	}

	public void setUfmipPdCash(BigDecimal ufmipPdCash) {
		this.ufmipPdCash = ufmipPdCash;
	}

    
    @Column(name="UFMIP_EARNED_CURR_MM", precision=9)
	public BigDecimal getUfmipEarnedCurrMm() {
		return this.ufmipEarnedCurrMm;
	}

	public void setUfmipEarnedCurrMm(BigDecimal ufmipEarnedCurrMm) {
		this.ufmipEarnedCurrMm = ufmipEarnedCurrMm;
	}

    
    @Column(name="UFMIP_FACTOR", precision=7, scale=5)
	public BigDecimal getUfmipFactor() {
		return this.ufmipFactor;
	}

	public void setUfmipFactor(BigDecimal ufmipFactor) {
		this.ufmipFactor = ufmipFactor;
	}

    
    @Column(name="UNDERWRITER_ID", length=16)
	public String getUnderwriterId() {
		return this.underwriterId;
	}

	public void setUnderwriterId(String underwriterId) {
		this.underwriterId = underwriterId;
	}

    
    @Column(name="UNDRWRTING_MTGEE5", length=5)
	public String getUndrwrtingMtgee5() {
		return this.undrwrtingMtgee5;
	}

	public void setUndrwrtingMtgee5(String undrwrtingMtgee5) {
		this.undrwrtingMtgee5 = undrwrtingMtgee5;
	}

    
    @Column(name="UNPD_BAL")
	public Integer getUnpdBal() {
		return this.unpdBal;
	}

	public void setUnpdBal(Integer unpdBal) {
		this.unpdBal = unpdBal;
	}

    
    @Column(name="VAL_PLUS_CLSNG")
	public Integer getValPlusClsng() {
		return this.valPlusClsng;
	}

	public void setValPlusClsng(Integer valPlusClsng) {
		this.valPlusClsng = valPlusClsng;
	}

    
    @Column(name="CREATED_BY", length=6)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

    
    @Column(name="UPDATED_BY", length=6)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_TS", length=23)
	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_TS", length=23)
	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}

    
    @Column(name="BLDG_TYP", length=1)
	public Character getBldgTyp() {
		return this.bldgTyp;
	}

	public void setBldgTyp(Character bldgTyp) {
		this.bldgTyp = bldgTyp;
	}

    
    @Column(name="DCSN_CD", length=16)
	public String getDcsnCd() {
		return this.dcsnCd;
	}

	public void setDcsnCd(String dcsnCd) {
		this.dcsnCd = dcsnCd;
	}

    
    @Column(name="REO_100_DOWN_PMT_PROG_IND", length=1)
	public Character getReo100DownPmtProgInd() {
		return this.reo100DownPmtProgInd;
	}

	public void setReo100DownPmtProgInd(Character reo100DownPmtProgInd) {
		this.reo100DownPmtProgInd = reo100DownPmtProgInd;
	}

    
    @Column(name="AFFORD_HOUS_PROV_EIN", length=9)
	public String getAffordHousProvEin() {
		return this.affordHousProvEin;
	}

	public void setAffordHousProvEin(String affordHousProvEin) {
		this.affordHousProvEin = affordHousProvEin;
	}

    
    @Column(name="APPRAISER_NAME", length=100)
	public String getAppraiserName() {
		return this.appraiserName;
	}

	public void setAppraiserName(String appraiserName) {
		this.appraiserName = appraiserName;
	}

    
    @Column(name="ASSETS_AFTER_CLOSING")
	public Integer getAssetsAfterClosing() {
		return this.assetsAfterClosing;
	}

	public void setAssetsAfterClosing(Integer assetsAfterClosing) {
		this.assetsAfterClosing = assetsAfterClosing;
	}

    
    @Column(name="ASSETS_AFTER_CLOSING_ENDRS")
	public Integer getAssetsAfterClosingEndrs() {
		return this.assetsAfterClosingEndrs;
	}

	public void setAssetsAfterClosingEndrs(Integer assetsAfterClosingEndrs) {
		this.assetsAfterClosingEndrs = assetsAfterClosingEndrs;
	}

    
    @Column(name="ASSETS_AFTER_CLOSING_UW")
	public Integer getAssetsAfterClosingUw() {
		return this.assetsAfterClosingUw;
	}

	public void setAssetsAfterClosingUw(Integer assetsAfterClosingUw) {
		this.assetsAfterClosingUw = assetsAfterClosingUw;
	}

    
    @Column(name="ASSUMED_LOAN_IND", length=1)
	public Character getAssumedLoanInd() {
		return this.assumedLoanInd;
	}

	public void setAssumedLoanInd(Character assumedLoanInd) {
		this.assumedLoanInd = assumedLoanInd;
	}

    
    @Column(name="RATIO_FIX_TEI_ENDRS", precision=7)
	public BigDecimal getRatioFixTeiEndrs() {
		return this.ratioFixTeiEndrs;
	}

	public void setRatioFixTeiEndrs(BigDecimal ratioFixTeiEndrs) {
		this.ratioFixTeiEndrs = ratioFixTeiEndrs;
	}

    
    @Column(name="RATIO_FIX_TEI_UW", precision=7)
	public BigDecimal getRatioFixTeiUw() {
		return this.ratioFixTeiUw;
	}

	public void setRatioFixTeiUw(BigDecimal ratioFixTeiUw) {
		this.ratioFixTeiUw = ratioFixTeiUw;
	}

    
    @Column(name="BACK_TO_WORK_IND", length=1)
	public Character getBackToWorkInd() {
		return this.backToWorkInd;
	}

	public void setBackToWorkInd(Character backToWorkInd) {
		this.backToWorkInd = backToWorkInd;
	}

    
    @Column(name="BNKRPTCY_ANY_IND", length=1)
	public Character getBnkrptcyAnyInd() {
		return this.bnkrptcyAnyInd;
	}

	public void setBnkrptcyAnyInd(Character bnkrptcyAnyInd) {
		this.bnkrptcyAnyInd = bnkrptcyAnyInd;
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

    
    @Column(name="BORR_1_YEARS_CURRENT_JOB")
	public Integer getBorr1YearsCurrentJob() {
		return this.borr1YearsCurrentJob;
	}

	public void setBorr1YearsCurrentJob(Integer borr1YearsCurrentJob) {
		this.borr1YearsCurrentJob = borr1YearsCurrentJob;
	}

    
    @Column(name="BORR_2_YEARS_CURRENT_JOB")
	public Integer getBorr2YearsCurrentJob() {
		return this.borr2YearsCurrentJob;
	}

	public void setBorr2YearsCurrentJob(Integer borr2YearsCurrentJob) {
		this.borr2YearsCurrentJob = borr2YearsCurrentJob;
	}

    
    @Column(name="BORR_2_FIRST_TIME_BUYER_IND", length=1)
	public Character getBorr2FirstTimeBuyerInd() {
		return this.borr2FirstTimeBuyerInd;
	}

	public void setBorr2FirstTimeBuyerInd(Character borr2FirstTimeBuyerInd) {
		this.borr2FirstTimeBuyerInd = borr2FirstTimeBuyerInd;
	}

    
    @Column(name="BORR_2_NAME", length=100)
	public String getBorr2Name() {
		return this.borr2Name;
	}

	public void setBorr2Name(String borr2Name) {
		this.borr2Name = borr2Name;
	}

    
    @Column(name="BORR_2_RENTING_IND", length=1)
	public Character getBorr2RentingInd() {
		return this.borr2RentingInd;
	}

	public void setBorr2RentingInd(Character borr2RentingInd) {
		this.borr2RentingInd = borr2RentingInd;
	}

    
    @Column(name="BORR_2_SELF_EMPL_IND", length=1)
	public Character getBorr2SelfEmplInd() {
		return this.borr2SelfEmplInd;
	}

	public void setBorr2SelfEmplInd(Character borr2SelfEmplInd) {
		this.borr2SelfEmplInd = borr2SelfEmplInd;
	}

    
    @Column(name="BORR_2_SSN", length=11)
	public String getBorr2Ssn() {
		return this.borr2Ssn;
	}

	public void setBorr2Ssn(String borr2Ssn) {
		this.borr2Ssn = borr2Ssn;
	}

    
    @Column(name="BORR_REQD_INVEST_TO_CLOSE")
	public Integer getBorrReqdInvestToClose() {
		return this.borrReqdInvestToClose;
	}

	public void setBorrReqdInvestToClose(Integer borrReqdInvestToClose) {
		this.borrReqdInvestToClose = borrReqdInvestToClose;
	}

    
    @Column(name="BORR_HSNG_EXP_ENDRS")
	public Integer getBorrHsngExpEndrs() {
		return this.borrHsngExpEndrs;
	}

	public void setBorrHsngExpEndrs(Integer borrHsngExpEndrs) {
		this.borrHsngExpEndrs = borrHsngExpEndrs;
	}

    
    @Column(name="BORR_HSNG_EXP_UW")
	public Integer getBorrHsngExpUw() {
		return this.borrHsngExpUw;
	}

	public void setBorrHsngExpUw(Integer borrHsngExpUw) {
		this.borrHsngExpUw = borrHsngExpUw;
	}

    
    @Column(name="BORR_PAID_CLOSING_COSTS")
	public Integer getBorrPaidClosingCosts() {
		return this.borrPaidClosingCosts;
	}

	public void setBorrPaidClosingCosts(Integer borrPaidClosingCosts) {
		this.borrPaidClosingCosts = borrPaidClosingCosts;
	}

    
    @Column(name="BUILDING_ON_OWN_LAND_IND", length=1)
	public Character getBuildingOnOwnLandInd() {
		return this.buildingOnOwnLandInd;
	}

	public void setBuildingOnOwnLandInd(Character buildingOnOwnLandInd) {
		this.buildingOnOwnLandInd = buildingOnOwnLandInd;
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
    @Column(name="CLOSNG_DT", length=23)
	public Date getClosngDt() {
		return this.closngDt;
	}

	public void setClosngDt(Date closngDt) {
		this.closngDt = closngDt;
	}

    
    @Column(name="COMBINED_LOAN_TO_VALUE_PCT", precision=5, scale=3)
	public BigDecimal getCombinedLoanToValuePct() {
		return this.combinedLoanToValuePct;
	}

	public void setCombinedLoanToValuePct(BigDecimal combinedLoanToValuePct) {
		this.combinedLoanToValuePct = combinedLoanToValuePct;
	}

    
    @Column(name="COMMTY_LAND_TRUST_EIN", length=9)
	public String getCommtyLandTrustEin() {
		return this.commtyLandTrustEin;
	}

	public void setCommtyLandTrustEin(String commtyLandTrustEin) {
		this.commtyLandTrustEin = commtyLandTrustEin;
	}

    
    @Column(name="COMM_PROPERTY_STATE_IND", length=1)
	public Character getCommPropertyStateInd() {
		return this.commPropertyStateInd;
	}

	public void setCommPropertyStateInd(Character commPropertyStateInd) {
		this.commPropertyStateInd = commPropertyStateInd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="APPLICATION_DATE", length=23)
	public Date getApplicationDate() {
		return this.applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
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

    
    @Column(name="DEPO_IS_PRESENT_IND", length=1)
	public Character getDepoIsPresentInd() {
		return this.depoIsPresentInd;
	}

	public void setDepoIsPresentInd(Character depoIsPresentInd) {
		this.depoIsPresentInd = depoIsPresentInd;
	}

    
    @Column(name="DISASTER_IND", length=1)
	public Character getDisasterInd() {
		return this.disasterInd;
	}

	public void setDisasterInd(Character disasterInd) {
		this.disasterInd = disasterInd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFF_DATE_APRSL", length=23)
	public Date getEffDateAprsl() {
		return this.effDateAprsl;
	}

	public void setEffDateAprsl(Date effDateAprsl) {
		this.effDateAprsl = effDateAprsl;
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

    
    @Column(name="ESCROW_AMOUNT")
	public Integer getEscrowAmount() {
		return this.escrowAmount;
	}

	public void setEscrowAmount(Integer escrowAmount) {
		this.escrowAmount = escrowAmount;
	}

    
    @Column(name="EXPECTED_RATE", precision=5, scale=3)
	public BigDecimal getExpectedRate() {
		return this.expectedRate;
	}

	public void setExpectedRate(BigDecimal expectedRate) {
		this.expectedRate = expectedRate;
	}

    
    @Column(name="FICO_DECISION_SCORE_UW")
	public Integer getFicoDecisionScoreUw() {
		return this.ficoDecisionScoreUw;
	}

	public void setFicoDecisionScoreUw(Integer ficoDecisionScoreUw) {
		this.ficoDecisionScoreUw = ficoDecisionScoreUw;
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

    
    @Column(name="RATIO_TOT_PMT_TO_TOT_INC_UW", precision=7)
	public BigDecimal getRatioTotPmtToTotIncUw() {
		return this.ratioTotPmtToTotIncUw;
	}

	public void setRatioTotPmtToTotIncUw(BigDecimal ratioTotPmtToTotIncUw) {
		this.ratioTotPmtToTotIncUw = ratioTotPmtToTotIncUw;
	}

    
    @Column(name="RATIO_TOT_PMT_TO_TOT_INC_ENDRS", precision=7)
	public BigDecimal getRatioTotPmtToTotIncEndrs() {
		return this.ratioTotPmtToTotIncEndrs;
	}

	public void setRatioTotPmtToTotIncEndrs(BigDecimal ratioTotPmtToTotIncEndrs) {
		this.ratioTotPmtToTotIncEndrs = ratioTotPmtToTotIncEndrs;
	}

    
    @Column(name="GIFT_LTR_2_SOURCE", length=32)
	public String getGiftLtr2Source() {
		return this.giftLtr2Source;
	}

	public void setGiftLtr2Source(String giftLtr2Source) {
		this.giftLtr2Source = giftLtr2Source;
	}

    
    @Column(name="HECM_COUNSEL_CERT_NO", length=36)
	public String getHecmCounselCertNo() {
		return this.hecmCounselCertNo;
	}

	public void setHecmCounselCertNo(String hecmCounselCertNo) {
		this.hecmCounselCertNo = hecmCounselCertNo;
	}

    
    @Column(name="HUD_REO_REPAIR_AMT")
	public Integer getHudReoRepairAmt() {
		return this.hudReoRepairAmt;
	}

	public void setHudReoRepairAmt(Integer hudReoRepairAmt) {
		this.hudReoRepairAmt = hudReoRepairAmt;
	}

    
    @Column(name="INSUR_APP_IN_TIME_IND", length=1)
	public Character getInsurAppInTimeInd() {
		return this.insurAppInTimeInd;
	}

	public void setInsurAppInTimeInd(Character insurAppInTimeInd) {
		this.insurAppInTimeInd = insurAppInTimeInd;
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

    
    @Column(name="MANDATORY_OBLIG_BORR_AMT")
	public Integer getMandatoryObligBorrAmt() {
		return this.mandatoryObligBorrAmt;
	}

	public void setMandatoryObligBorrAmt(Integer mandatoryObligBorrAmt) {
		this.mandatoryObligBorrAmt = mandatoryObligBorrAmt;
	}

    
    @Column(name="MANDATORY_OBLIG_LEND_AMT")
	public Integer getMandatoryObligLendAmt() {
		return this.mandatoryObligLendAmt;
	}

	public void setMandatoryObligLendAmt(Integer mandatoryObligLendAmt) {
		this.mandatoryObligLendAmt = mandatoryObligLendAmt;
	}

    
    @Column(name="MAN_UW_STRETCH_RATIO_IND", length=1)
	public Character getManUwStretchRatioInd() {
		return this.manUwStretchRatioInd;
	}

	public void setManUwStretchRatioInd(Character manUwStretchRatioInd) {
		this.manUwStretchRatioInd = manUwStretchRatioInd;
	}

    
    @Column(name="MAX_CLAIM_AMT")
	public Integer getMaxClaimAmt() {
		return this.maxClaimAmt;
	}

	public void setMaxClaimAmt(Integer maxClaimAmt) {
		this.maxClaimAmt = maxClaimAmt;
	}

    
    @Column(name="MAX_RATE", precision=5, scale=3)
	public BigDecimal getMaxRate() {
		return this.maxRate;
	}

	public void setMaxRate(BigDecimal maxRate) {
		this.maxRate = maxRate;
	}

    
    @Column(name="CURRENT_AT_ENDORSE_IND", length=1)
	public Character getCurrentAtEndorseInd() {
		return this.currentAtEndorseInd;
	}

	public void setCurrentAtEndorseInd(Character currentAtEndorseInd) {
		this.currentAtEndorseInd = currentAtEndorseInd;
	}

    
    @Column(name="NON_OCCUPYING_BORR_IND", length=1)
	public Character getNonOccupyingBorrInd() {
		return this.nonOccupyingBorrInd;
	}

	public void setNonOccupyingBorrInd(Character nonOccupyingBorrInd) {
		this.nonOccupyingBorrInd = nonOccupyingBorrInd;
	}

    
    @Column(name="ORGNTNG_MTGEE_NMLS_ID", length=12)
	public String getOrgntngMtgeeNmlsId() {
		return this.orgntngMtgeeNmlsId;
	}

	public void setOrgntngMtgeeNmlsId(String orgntngMtgeeNmlsId) {
		this.orgntngMtgeeNmlsId = orgntngMtgeeNmlsId;
	}

    
    @Column(name="ORIGINATION_FEE")
	public Integer getOriginationFee() {
		return this.originationFee;
	}

	public void setOriginationFee(Integer originationFee) {
		this.originationFee = originationFee;
	}

    
    @Column(name="PAYMENT_PLAN", length=16)
	public String getPaymentPlan() {
		return this.paymentPlan;
	}

	public void setPaymentPlan(String paymentPlan) {
		this.paymentPlan = paymentPlan;
	}

    
    @Column(name="PRICE_OF_PRIOR_SALE")
	public Integer getPriceOfPriorSale() {
		return this.priceOfPriorSale;
	}

	public void setPriceOfPriorSale(Integer priceOfPriorSale) {
		this.priceOfPriorSale = priceOfPriorSale;
	}

    
    @Column(name="PRIOR_SALE_WITHIN_LAST_3YR_IND", length=1)
	public Character getPriorSaleWithinLast3yrInd() {
		return this.priorSaleWithinLast3yrInd;
	}

	public void setPriorSaleWithinLast3yrInd(Character priorSaleWithinLast3yrInd) {
		this.priorSaleWithinLast3yrInd = priorSaleWithinLast3yrInd;
	}

    
    @Column(name="PROP_CURR_OCCUPANCY_TYPE", length=16)
	public String getPropCurrOccupancyType() {
		return this.propCurrOccupancyType;
	}

	public void setPropCurrOccupancyType(String propCurrOccupancyType) {
		this.propCurrOccupancyType = propCurrOccupancyType;
	}

    
    @Column(name="PROPERTY_REPAIRS")
	public Integer getPropertyRepairs() {
		return this.propertyRepairs;
	}

	public void setPropertyRepairs(Integer propertyRepairs) {
		this.propertyRepairs = propertyRepairs;
	}

    
    @Column(name="CONST_COMPLT_YR_MO", length=6)
	public String getConstCompltYrMo() {
		return this.constCompltYrMo;
	}

	public void setConstCompltYrMo(String constCompltYrMo) {
		this.constCompltYrMo = constCompltYrMo;
	}

    
    @Column(name="QUALIFIED_MRTG_POINTS_AND_FEES")
	public Integer getQualifiedMrtgPointsAndFees() {
		return this.qualifiedMrtgPointsAndFees;
	}

	public void setQualifiedMrtgPointsAndFees(Integer qualifiedMrtgPointsAndFees) {
		this.qualifiedMrtgPointsAndFees = qualifiedMrtgPointsAndFees;
	}

    
    @Column(name="REAL_ESTATE_ASSETS")
	public Integer getRealEstateAssets() {
		return this.realEstateAssets;
	}

	public void setRealEstateAssets(Integer realEstateAssets) {
		this.realEstateAssets = realEstateAssets;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REPAIR_COMPLETION_DATE", length=23)
	public Date getRepairCompletionDate() {
		return this.repairCompletionDate;
	}

	public void setRepairCompletionDate(Date repairCompletionDate) {
		this.repairCompletionDate = repairCompletionDate;
	}

    
    @Column(name="REPAIR_RIDER_IN_FILE_IND", length=1)
	public Character getRepairRiderInFileInd() {
		return this.repairRiderInFileInd;
	}

	public void setRepairRiderInFileInd(Character repairRiderInFileInd) {
		this.repairRiderInFileInd = repairRiderInFileInd;
	}

    
    @Column(name="SALES_PRICE")
	public Integer getSalesPrice() {
		return this.salesPrice;
	}

	public void setSalesPrice(Integer salesPrice) {
		this.salesPrice = salesPrice;
	}

    
    @Column(name="SALE_PRICE_GTR_ACQ_COST_IND", length=1)
	public Character getSalePriceGtrAcqCostInd() {
		return this.salePriceGtrAcqCostInd;
	}

	public void setSalePriceGtrAcqCostInd(Character salePriceGtrAcqCostInd) {
		this.salePriceGtrAcqCostInd = salePriceGtrAcqCostInd;
	}

    
    @Column(name="SECONDARY_FINANCE_EXISTS_IND", length=1)
	public Character getSecondaryFinanceExistsInd() {
		return this.secondaryFinanceExistsInd;
	}

	public void setSecondaryFinanceExistsInd(Character secondaryFinanceExistsInd) {
		this.secondaryFinanceExistsInd = secondaryFinanceExistsInd;
	}

    
    @Column(name="SELLER_CNTRBTN_PCNT", precision=5, scale=3)
	public BigDecimal getSellerCntrbtnPcnt() {
		return this.sellerCntrbtnPcnt;
	}

	public void setSellerCntrbtnPcnt(BigDecimal sellerCntrbtnPcnt) {
		this.sellerCntrbtnPcnt = sellerCntrbtnPcnt;
	}

    
    @Column(name="SITE_TYPE", length=30)
	public String getSiteType() {
		return this.siteType;
	}

	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

    
    @Column(name="SPECIAL_PROGRAM", length=30)
	public String getSpecialProgram() {
		return this.specialProgram;
	}

	public void setSpecialProgram(String specialProgram) {
		this.specialProgram = specialProgram;
	}

    
    @Column(name="TEN_YR_RATE_LOCK_IND", length=1)
	public Character getTenYrRateLockInd() {
		return this.tenYrRateLockInd;
	}

	public void setTenYrRateLockInd(Character tenYrRateLockInd) {
		this.tenYrRateLockInd = tenYrRateLockInd;
	}

    
    @Column(name="TOT_ASSETS_ENDRS")
	public Integer getTotAssetsEndrs() {
		return this.totAssetsEndrs;
	}

	public void setTotAssetsEndrs(Integer totAssetsEndrs) {
		this.totAssetsEndrs = totAssetsEndrs;
	}

    
    @Column(name="TOT_ASSETS_UW")
	public Integer getTotAssetsUw() {
		return this.totAssetsUw;
	}

	public void setTotAssetsUw(Integer totAssetsUw) {
		this.totAssetsUw = totAssetsUw;
	}

    
    @Column(name="TOT_CLSNG_CSTS_ENDRS")
	public Integer getTotClsngCstsEndrs() {
		return this.totClsngCstsEndrs;
	}

	public void setTotClsngCstsEndrs(Integer totClsngCstsEndrs) {
		this.totClsngCstsEndrs = totClsngCstsEndrs;
	}

    
    @Column(name="TOT_CLSNG_CSTS_UW")
	public Integer getTotClsngCstsUw() {
		return this.totClsngCstsUw;
	}

	public void setTotClsngCstsUw(Integer totClsngCstsUw) {
		this.totClsngCstsUw = totClsngCstsUw;
	}

    
    @Column(name="TOT_FIXED_PYMT_ENDRS")
	public Integer getTotFixedPymtEndrs() {
		return this.totFixedPymtEndrs;
	}

	public void setTotFixedPymtEndrs(Integer totFixedPymtEndrs) {
		this.totFixedPymtEndrs = totFixedPymtEndrs;
	}

    
    @Column(name="TOT_FIXED_PYMT_UW")
	public Integer getTotFixedPymtUw() {
		return this.totFixedPymtUw;
	}

	public void setTotFixedPymtUw(Integer totFixedPymtUw) {
		this.totFixedPymtUw = totFixedPymtUw;
	}

    
    @Column(name="TOT_MNTHLY_MTG_PYMT_ENDRS")
	public Integer getTotMnthlyMtgPymtEndrs() {
		return this.totMnthlyMtgPymtEndrs;
	}

	public void setTotMnthlyMtgPymtEndrs(Integer totMnthlyMtgPymtEndrs) {
		this.totMnthlyMtgPymtEndrs = totMnthlyMtgPymtEndrs;
	}

    
    @Column(name="TOT_MNTHLY_MTG_PYMT_UW")
	public Integer getTotMnthlyMtgPymtUw() {
		return this.totMnthlyMtgPymtUw;
	}

	public void setTotMnthlyMtgPymtUw(Integer totMnthlyMtgPymtUw) {
		this.totMnthlyMtgPymtUw = totMnthlyMtgPymtUw;
	}

    
    @Column(name="TOTAL_REQUIRED_FUNDS_TO_CLOSE")
	public Integer getTotalRequiredFundsToClose() {
		return this.totalRequiredFundsToClose;
	}

	public void setTotalRequiredFundsToClose(Integer totalRequiredFundsToClose) {
		this.totalRequiredFundsToClose = totalRequiredFundsToClose;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="FRCLSR_STRT_DT", length=23)
	public Date getFrclsrStrtDt() {
		return this.frclsrStrtDt;
	}

	public void setFrclsrStrtDt(Date frclsrStrtDt) {
		this.frclsrStrtDt = frclsrStrtDt;
	}

    
    @Column(name="RPR_ESCRW_AMT")
	public Integer getRprEscrwAmt() {
		return this.rprEscrwAmt;
	}

	public void setRprEscrwAmt(Integer rprEscrwAmt) {
		this.rprEscrwAmt = rprEscrwAmt;
	}

    
    @Column(name="BATCH_ID", length=36)
	public String getBatchId() {
		return this.batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

    
    @Column(name="RVW_LOCATION_ID", length=16)
	public String getRvwLocationId() {
		return this.rvwLocationId;
	}

	public void setRvwLocationId(String rvwLocationId) {
		this.rvwLocationId = rvwLocationId;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DISTRIBUTION_DT", length=23)
	public Date getDistributionDt() {
		return this.distributionDt;
	}

	public void setDistributionDt(Date distributionDt) {
		this.distributionDt = distributionDt;
	}

    
    @Column(name="MTGEE5", length=5)
	public String getMtgee5() {
		return this.mtgee5;
	}

	public void setMtgee5(String mtgee5) {
		this.mtgee5 = mtgee5;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DUE_DATE", length=23)
	public Date getDueDate() {
		return this.dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

    
    @Column(name="PREF_RVW_LOCATION", length=16)
	public String getPrefRvwLocation() {
		return this.prefRvwLocation;
	}

	public void setPrefRvwLocation(String prefRvwLocation) {
		this.prefRvwLocation = prefRvwLocation;
	}

    
    @Column(name="PRIMARY_SELECTION_REASON", length=16)
	public String getPrimarySelectionReason() {
		return this.primarySelectionReason;
	}

	public void setPrimarySelectionReason(String primarySelectionReason) {
		this.primarySelectionReason = primarySelectionReason;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="RECEIVED_DT", length=23)
	public Date getReceivedDt() {
		return this.receivedDt;
	}

	public void setReceivedDt(Date receivedDt) {
		this.receivedDt = receivedDt;
	}

    
    @Column(name="RQST_DOCS_SOURCE_CD", length=16)
	public String getRqstDocsSourceCd() {
		return this.rqstDocsSourceCd;
	}

	public void setRqstDocsSourceCd(String rqstDocsSourceCd) {
		this.rqstDocsSourceCd = rqstDocsSourceCd;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="REQUESTED_DT_TM", length=23)
	public Date getRequestedDtTm() {
		return this.requestedDtTm;
	}

	public void setRequestedDtTm(Date requestedDtTm) {
		this.requestedDtTm = requestedDtTm;
	}

    
    @Column(name="REVIEW_TYPE", length=16)
	public String getReviewType() {
		return this.reviewType;
	}

	public void setReviewType(String reviewType) {
		this.reviewType = reviewType;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="SELECTION_DT", length=23)
	public Date getSelectionDt() {
		return this.selectionDt;
	}

	public void setSelectionDt(Date selectionDt) {
		this.selectionDt = selectionDt;
	}

    
    @Column(name="STATUS", length=16)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    
    @Column(name="SELECTION_METHOD", length=20)
	public String getSelectionMethod() {
		return this.selectionMethod;
	}

	public void setSelectionMethod(String selectionMethod) {
		this.selectionMethod = selectionMethod;
	}

    
    @Column(name="PROD_TYPE", length=3)
	public String getProdType() {
		return this.prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

}
