package gov.hud.lrs.common.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.entity.Batch;
import gov.hud.lrs.common.entity.LoanSelection;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.entity.LoanSelectionPending;
import gov.hud.lrs.common.entity.Review;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.entity.ReviewScopeRef;
import gov.hud.lrs.common.entity.ReviewTypeRef;
import gov.hud.lrs.common.entity.SelectionReason;
import gov.hud.lrs.common.entity.SelectionRequest;
import gov.hud.lrs.common.enumeration.LoanSelectionStatusCodes;
import gov.hud.lrs.common.enumeration.ReviewTypeCodes;
import gov.hud.lrs.common.exception.BadRequestException;
import gov.hud.lrs.common.repository.LoanSelectionCaseSummaryRepository;
import gov.hud.lrs.common.repository.LoanSelectionPendingRepository;
import gov.hud.lrs.common.repository.LoanSelectionRepository;
import gov.hud.lrs.common.repository.LoanSelectionStatusRefRepository;
import gov.hud.lrs.common.repository.ProductTypeRefRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.security.SecurityService;
import gov.hud.lrs.common.util.DateUtils;

@Service
public class CommonLoanSelectionService {

	@Autowired private LoanSelectionRepository loanSelectionRepository;
	@Autowired private LoanSelectionPendingRepository loanSelectionPendingRepository;
	@Autowired private LoanSelectionStatusRefRepository loanSelectionStatusRefRepository;
	@Autowired private LoanSelectionCaseSummaryRepository loanSelectionCaseSummaryRepository;
	@Autowired private ProductTypeRefRepository productTypeRefRepository;
	@Autowired private ReviewLocationRepository reviewLocationRepository;

	@Autowired private LenderService lenderService;
	@Autowired private SecurityService securityService;

	@Transactional
	public LoanSelectionCaseSummary cloneLoanSelectionCaseSummary(LoanSelectionCaseSummary loanSelectionCaseSummary, SelectionRequest selectionRequest) {
		LoanSelectionCaseSummary newLoanSelectionCaseSummary = new LoanSelectionCaseSummary();
		cloneLoanSelectionCaseSummary(newLoanSelectionCaseSummary, loanSelectionCaseSummary);
		return createLoanSelectionCaseSummary(newLoanSelectionCaseSummary, selectionRequest);
	}
	
	@Transactional
	public LoanSelectionCaseSummary cloneLoanSelectionCaseSummary(LoanSelectionCaseSummary newLoanSelectionCaseSummary, LoanSelectionCaseSummary loanSelectionCaseSummary) {
		newLoanSelectionCaseSummary.setAprslCmpltnDt(loanSelectionCaseSummary.getAprslCmpltnDt());
		newLoanSelectionCaseSummary.setCaseNumber(loanSelectionCaseSummary.getCaseNumber());
		newLoanSelectionCaseSummary.setPrequalDt(loanSelectionCaseSummary.getPrequalDt());
		newLoanSelectionCaseSummary.setAddtnl10pctIplUsageAmt(loanSelectionCaseSummary.getAddtnl10pctIplUsageAmt());
		newLoanSelectionCaseSummary.setAddtnl10pctIplUsageInd(loanSelectionCaseSummary.getAddtnl10pctIplUsageInd());
		newLoanSelectionCaseSummary.setAdpCode(loanSelectionCaseSummary.getAdpCode());
		newLoanSelectionCaseSummary.setAllowClsgCost(loanSelectionCaseSummary.getAllowClsgCost());
		newLoanSelectionCaseSummary.setAmortTypCd(loanSelectionCaseSummary.getAmortTypCd());
		newLoanSelectionCaseSummary.setAplctnMthd(loanSelectionCaseSummary.getAplctnMthd());
		newLoanSelectionCaseSummary.setArmAdjPrd(loanSelectionCaseSummary.getArmAdjPrd());
		newLoanSelectionCaseSummary.setArmIndxExpctdRt(loanSelectionCaseSummary.getArmIndxExpctdRt());
		newLoanSelectionCaseSummary.setArmIndxInd(loanSelectionCaseSummary.getArmIndxInd());
		newLoanSelectionCaseSummary.setArmInd(loanSelectionCaseSummary.getArmInd());
		newLoanSelectionCaseSummary.setArmMrgnAmt(loanSelectionCaseSummary.getArmMrgnAmt());
		newLoanSelectionCaseSummary.setArmDt(loanSelectionCaseSummary.getArmDt());
		newLoanSelectionCaseSummary.setCountAus(loanSelectionCaseSummary.getCountAus());
		newLoanSelectionCaseSummary.setBsmtCd(loanSelectionCaseSummary.getBsmtCd());
		newLoanSelectionCaseSummary.setBnkrptcyCd(loanSelectionCaseSummary.getBnkrptcyCd());
		newLoanSelectionCaseSummary.setBnkrptcyDt(loanSelectionCaseSummary.getBnkrptcyDt());
		newLoanSelectionCaseSummary.setBorrBrthDt(loanSelectionCaseSummary.getBorrBrthDt());
		newLoanSelectionCaseSummary.setBorrCnslTyp(loanSelectionCaseSummary.getBorrCnslTyp());
		newLoanSelectionCaseSummary.setBorrGender(loanSelectionCaseSummary.getBorrGender());
		newLoanSelectionCaseSummary.setBorrTyp(loanSelectionCaseSummary.getBorrTyp());
		newLoanSelectionCaseSummary.setBuyDwnInd(loanSelectionCaseSummary.getBuyDwnInd());
		newLoanSelectionCaseSummary.setCsEstabDt(loanSelectionCaseSummary.getCsEstabDt());
		newLoanSelectionCaseSummary.setCctReinsDt(loanSelectionCaseSummary.getCctReinsDt());
		newLoanSelectionCaseSummary.setCsTyp(loanSelectionCaseSummary.getCsTyp());
		newLoanSelectionCaseSummary.setCashoutRefiInd(loanSelectionCaseSummary.getCashoutRefiInd());
		newLoanSelectionCaseSummary.setClaimType(loanSelectionCaseSummary.getClaimType());
		newLoanSelectionCaseSummary.setClsngDt(loanSelectionCaseSummary.getClsngDt());
		newLoanSelectionCaseSummary.setCfEquivicalAssetsInd(loanSelectionCaseSummary.getCfEquivicalAssetsInd());
		newLoanSelectionCaseSummary.setCfExpectedSsiInd(loanSelectionCaseSummary.getCfExpectedSsiInd());
		newLoanSelectionCaseSummary.setCfHecmSufficientInd(loanSelectionCaseSummary.getCfHecmSufficientInd());
		newLoanSelectionCaseSummary.setCfImputedIncomeInd(loanSelectionCaseSummary.getCfImputedIncomeInd());
		newLoanSelectionCaseSummary.setCfNbsIncomeInd(loanSelectionCaseSummary.getCfNbsIncomeInd());
		newLoanSelectionCaseSummary.setCfOtIncomeInd(loanSelectionCaseSummary.getCfOtIncomeInd());
		newLoanSelectionCaseSummary.setCfOtherIncomeInd(loanSelectionCaseSummary.getCfOtherIncomeInd());
		newLoanSelectionCaseSummary.setCfPropPmtHistInd(loanSelectionCaseSummary.getCfPropPmtHistInd());
		newLoanSelectionCaseSummary.setCondoFeeCurrInd(loanSelectionCaseSummary.getCondoFeeCurrInd());
		newLoanSelectionCaseSummary.setCondoFeeDelinqInd(loanSelectionCaseSummary.getCondoFeeDelinqInd());
		newLoanSelectionCaseSummary.setConstCd(loanSelectionCaseSummary.getConstCd());
		newLoanSelectionCaseSummary.setConstStsCd(loanSelectionCaseSummary.getConstStsCd());
		newLoanSelectionCaseSummary.setCorpAdvncAmt(loanSelectionCaseSummary.getCorpAdvncAmt());
		newLoanSelectionCaseSummary.setCurrDflt90dayInd(loanSelectionCaseSummary.getCurrDflt90dayInd());
		newLoanSelectionCaseSummary.setCurrDfltCycDt(loanSelectionCaseSummary.getCurrDfltCycDt());
		newLoanSelectionCaseSummary.setCurrDfltEpisodeNbr(loanSelectionCaseSummary.getCurrDfltEpisodeNbr());
		newLoanSelectionCaseSummary.setCurrDfltRsnCd(loanSelectionCaseSummary.getCurrDfltRsnCd());
		newLoanSelectionCaseSummary.setCurrDfltStsCd(loanSelectionCaseSummary.getCurrDfltStsCd());
		newLoanSelectionCaseSummary.setCurrDfltStsDt(loanSelectionCaseSummary.getCurrDfltStsDt());
		newLoanSelectionCaseSummary.setCurrDfltStsSummaryCd(loanSelectionCaseSummary.getCurrDfltStsSummaryCd());
		newLoanSelectionCaseSummary.setDpndntCnt(loanSelectionCaseSummary.getDpndntCnt());
		newLoanSelectionCaseSummary.setDirLendingBranchInd(loanSelectionCaseSummary.getDirLendingBranchInd());
		newLoanSelectionCaseSummary.setEarlyClaimInd(loanSelectionCaseSummary.getEarlyClaimInd());
		newLoanSelectionCaseSummary.setEarlyDefaultInd(loanSelectionCaseSummary.getEarlyDefaultInd());
		newLoanSelectionCaseSummary.setEndrsmntDt(loanSelectionCaseSummary.getEndrsmntDt());
		newLoanSelectionCaseSummary.setEndrsmntRvwPrsnnlId(loanSelectionCaseSummary.getEndrsmntRvwPrsnnlId());
		newLoanSelectionCaseSummary.setEnergyEffMrtg(loanSelectionCaseSummary.getEnergyEffMrtg());
		newLoanSelectionCaseSummary.setFctryFbrct(loanSelectionCaseSummary.getFctryFbrct());
		newLoanSelectionCaseSummary.setFemaFloodAreaInd(loanSelectionCaseSummary.getFemaFloodAreaInd());
		newLoanSelectionCaseSummary.setFhacAddrChg(loanSelectionCaseSummary.getFhacAddrChg());
		newLoanSelectionCaseSummary.setFncngTyp(loanSelectionCaseSummary.getFncngTyp());
		newLoanSelectionCaseSummary.setFrclsrInd(loanSelectionCaseSummary.getFrclsrInd());
		newLoanSelectionCaseSummary.setFtInEps3mnthDelqDt(loanSelectionCaseSummary.getFtInEps3mnthDelqDt());
		newLoanSelectionCaseSummary.setGiftLtrAmt(loanSelectionCaseSummary.getGiftLtrAmt());
		newLoanSelectionCaseSummary.setGiftLtr2Amt(loanSelectionCaseSummary.getGiftLtr2Amt());
		newLoanSelectionCaseSummary.setGiftLtrSrc(loanSelectionCaseSummary.getGiftLtrSrc());
		newLoanSelectionCaseSummary.setGiftLtrTin(loanSelectionCaseSummary.getGiftLtrTin());
		newLoanSelectionCaseSummary.setHecmAssets(loanSelectionCaseSummary.getHecmAssets());
		newLoanSelectionCaseSummary.setHecmCounselDt(loanSelectionCaseSummary.getHecmCounselDt());
		newLoanSelectionCaseSummary.setHecmLiens(loanSelectionCaseSummary.getHecmLiens());
		newLoanSelectionCaseSummary.setHecmPrncplLmt(loanSelectionCaseSummary.getHecmPrncplLmt());
		newLoanSelectionCaseSummary.setHldrMtgee5A43(loanSelectionCaseSummary.getHldrMtgee5A43());
		newLoanSelectionCaseSummary.setHsngPgmCd(loanSelectionCaseSummary.getHsngPgmCd());
		newLoanSelectionCaseSummary.setInitDisbursementLimit(loanSelectionCaseSummary.getInitDisbursementLimit());
		newLoanSelectionCaseSummary.setInitFee(loanSelectionCaseSummary.getInitFee());
		newLoanSelectionCaseSummary.setInsrncStatusCd(loanSelectionCaseSummary.getInsrncStatusCd());
		newLoanSelectionCaseSummary.setIntRt(loanSelectionCaseSummary.getIntRt());
		newLoanSelectionCaseSummary.setMiscLndrInsrncInd(loanSelectionCaseSummary.getMiscLndrInsrncInd());
		newLoanSelectionCaseSummary.setLeCompoundRate(loanSelectionCaseSummary.getLeCompoundRate());
		newLoanSelectionCaseSummary.setLeExpectedRate(loanSelectionCaseSummary.getLeExpectedRate());
		newLoanSelectionCaseSummary.setLeProjectedAmt(loanSelectionCaseSummary.getLeProjectedAmt());
		newLoanSelectionCaseSummary.setLesaFundingAmt(loanSelectionCaseSummary.getLesaFundingAmt());
		newLoanSelectionCaseSummary.setLesaFundingType(loanSelectionCaseSummary.getLesaFundingType());
		newLoanSelectionCaseSummary.setLeSetasideAmt(loanSelectionCaseSummary.getLeSetasideAmt());
		newLoanSelectionCaseSummary.setLeTalcMonths(loanSelectionCaseSummary.getLeTalcMonths());
		newLoanSelectionCaseSummary.setLoanNbr(loanSelectionCaseSummary.getLoanNbr());
		newLoanSelectionCaseSummary.setLoanPrps(loanSelectionCaseSummary.getLoanPrps());
		newLoanSelectionCaseSummary.setLoanPrpsFrwdPymtInd(loanSelectionCaseSummary.getLoanPrpsFrwdPymtInd());
		newLoanSelectionCaseSummary.setLoanPrpsImprvmntInd(loanSelectionCaseSummary.getLoanPrpsImprvmntInd());
		newLoanSelectionCaseSummary.setLoanPrpsIncmInd(loanSelectionCaseSummary.getLoanPrpsIncmInd());
		newLoanSelectionCaseSummary.setLoanPrpsInsrncInd(loanSelectionCaseSummary.getLoanPrpsInsrncInd());
		newLoanSelectionCaseSummary.setLoanPrpsLeisureInd(loanSelectionCaseSummary.getLoanPrpsLeisureInd());
		newLoanSelectionCaseSummary.setLoanPrpsMedclInd(loanSelectionCaseSummary.getLoanPrpsMedclInd());
		newLoanSelectionCaseSummary.setLoanPrpsOthrInd(loanSelectionCaseSummary.getLoanPrpsOthrInd());
		newLoanSelectionCaseSummary.setPropTyp(loanSelectionCaseSummary.getPropTyp());
		newLoanSelectionCaseSummary.setLoanPrpsTaxesInd(loanSelectionCaseSummary.getLoanPrpsTaxesInd());
		newLoanSelectionCaseSummary.setLoanPrpsText(loanSelectionCaseSummary.getLoanPrpsText());
		newLoanSelectionCaseSummary.setLtvCat(loanSelectionCaseSummary.getLtvCat());
		newLoanSelectionCaseSummary.setRatioLoanToVlNew(loanSelectionCaseSummary.getRatioLoanToVlNew());
		newLoanSelectionCaseSummary.setLoanType(loanSelectionCaseSummary.getLoanType());
		newLoanSelectionCaseSummary.setLossmitCd(loanSelectionCaseSummary.getLossmitCd());
		newLoanSelectionCaseSummary.setMndtryOblgtnsAmt(loanSelectionCaseSummary.getMndtryOblgtnsAmt());
		newLoanSelectionCaseSummary.setMarriedToNbsInd(loanSelectionCaseSummary.getMarriedToNbsInd());
		newLoanSelectionCaseSummary.setCurrMnthlyMip(loanSelectionCaseSummary.getCurrMnthlyMip());
		newLoanSelectionCaseSummary.setMiscAusDcsnCd(loanSelectionCaseSummary.getMiscAusDcsnCd());
		newLoanSelectionCaseSummary.setMiscAusInd(loanSelectionCaseSummary.getMiscAusInd());
		newLoanSelectionCaseSummary.setMeDebtAmt(loanSelectionCaseSummary.getMeDebtAmt());
		newLoanSelectionCaseSummary.setMeOtherAmt(loanSelectionCaseSummary.getMeOtherAmt());
		newLoanSelectionCaseSummary.setMeReAmt(loanSelectionCaseSummary.getMeReAmt());
		newLoanSelectionCaseSummary.setMeTotalAmt(loanSelectionCaseSummary.getMeTotalAmt());
		newLoanSelectionCaseSummary.setMiImputedAmt(loanSelectionCaseSummary.getMiImputedAmt());
		newLoanSelectionCaseSummary.setMiOtherAmt(loanSelectionCaseSummary.getMiOtherAmt());
		newLoanSelectionCaseSummary.setMiTotalAmt(loanSelectionCaseSummary.getMiTotalAmt());
		newLoanSelectionCaseSummary.setMnthlySetAside(loanSelectionCaseSummary.getMnthlySetAside());
		newLoanSelectionCaseSummary.setMortExcldFncdMip(loanSelectionCaseSummary.getMortExcldFncdMip());
		newLoanSelectionCaseSummary.setMipFinancedInd(loanSelectionCaseSummary.getMipFinancedInd());
		newLoanSelectionCaseSummary.setNbsFirstMiddleLast(loanSelectionCaseSummary.getNbsFirstMiddleLast());
		newLoanSelectionCaseSummary.setNbrhdPctOwned(loanSelectionCaseSummary.getNbrhdPctOwned());
		newLoanSelectionCaseSummary.setNbrhdPrice(loanSelectionCaseSummary.getNbrhdPrice());
		newLoanSelectionCaseSummary.setNorIssRptDt(loanSelectionCaseSummary.getNorIssRptDt());
		newLoanSelectionCaseSummary.setNbrBthrms(loanSelectionCaseSummary.getNbrBthrms());
		newLoanSelectionCaseSummary.setNbrBdrm(loanSelectionCaseSummary.getNbrBdrm());
		newLoanSelectionCaseSummary.setNbrRms(loanSelectionCaseSummary.getNbrRms());
		newLoanSelectionCaseSummary.setNumLivingUnits(loanSelectionCaseSummary.getNumLivingUnits());
		newLoanSelectionCaseSummary.setOcpncyStsCd(loanSelectionCaseSummary.getOcpncyStsCd());
		newLoanSelectionCaseSummary.setOcpncyStsDt(loanSelectionCaseSummary.getOcpncyStsDt());
		newLoanSelectionCaseSummary.setOldstUnpdDt(loanSelectionCaseSummary.getOldstUnpdDt());
		newLoanSelectionCaseSummary.setOrigMrtgAmt(loanSelectionCaseSummary.getOrigMrtgAmt());
		newLoanSelectionCaseSummary.setOrgntngMtgee5(loanSelectionCaseSummary.getOrgntngMtgee5());
		newLoanSelectionCaseSummary.setOrgntngMtgee10Id(loanSelectionCaseSummary.getOrgntngMtgee10Id());
		newLoanSelectionCaseSummary.setTypOrgntngMtgee(loanSelectionCaseSummary.getTypOrgntngMtgee());
		newLoanSelectionCaseSummary.setOtherDebtCurrInd(loanSelectionCaseSummary.getOtherDebtCurrInd());
		newLoanSelectionCaseSummary.setOtherDebtLatePmtInd(loanSelectionCaseSummary.getOtherDebtLatePmtInd());
		newLoanSelectionCaseSummary.setPreRvwDcsn(loanSelectionCaseSummary.getPreRvwDcsn());
		newLoanSelectionCaseSummary.setPreClsngInd(loanSelectionCaseSummary.getPreClsngInd());
		newLoanSelectionCaseSummary.setPrevCompltSbdvsnInd(loanSelectionCaseSummary.getPrevCompltSbdvsnInd());
		newLoanSelectionCaseSummary.setPrncplRdctnAmt(loanSelectionCaseSummary.getPrncplRdctnAmt());
		newLoanSelectionCaseSummary.setPriorSaleRqrdInd(loanSelectionCaseSummary.getPriorSaleRqrdInd());
		newLoanSelectionCaseSummary.setPrcsngTyp(loanSelectionCaseSummary.getPrcsngTyp());
		newLoanSelectionCaseSummary.setProgIdF17(loanSelectionCaseSummary.getProgIdF17());
		newLoanSelectionCaseSummary.setDtAcq(loanSelectionCaseSummary.getDtAcq());
		newLoanSelectionCaseSummary.setPropAddrSt(loanSelectionCaseSummary.getPropAddrSt());
		newLoanSelectionCaseSummary.setPrprtyAprslVl(loanSelectionCaseSummary.getPrprtyAprslVl());
		newLoanSelectionCaseSummary.setPcCondoFeeAmt(loanSelectionCaseSummary.getPcCondoFeeAmt());
		newLoanSelectionCaseSummary.setPcFloodInsAmt(loanSelectionCaseSummary.getPcFloodInsAmt());
		newLoanSelectionCaseSummary.setPcHazInsAmt(loanSelectionCaseSummary.getPcHazInsAmt());
		newLoanSelectionCaseSummary.setPcOtherAmt(loanSelectionCaseSummary.getPcOtherAmt());
		newLoanSelectionCaseSummary.setPcReTaxAmt(loanSelectionCaseSummary.getPcReTaxAmt());
		newLoanSelectionCaseSummary.setPcSetasideTotAmt(loanSelectionCaseSummary.getPcSetasideTotAmt());
		newLoanSelectionCaseSummary.setPcTotalAmt(loanSelectionCaseSummary.getPcTotalAmt());
		newLoanSelectionCaseSummary.setPrprtyCnvrsnTyp(loanSelectionCaseSummary.getPrprtyCnvrsnTyp());
		newLoanSelectionCaseSummary.setPdStrmlnFlg(loanSelectionCaseSummary.getPdStrmlnFlg());
		newLoanSelectionCaseSummary.setRatioOreTei(loanSelectionCaseSummary.getRatioOreTei());
		newLoanSelectionCaseSummary.setPtiCat(loanSelectionCaseSummary.getPtiCat());
		newLoanSelectionCaseSummary.setReDebtCurrInd(loanSelectionCaseSummary.getReDebtCurrInd());
		newLoanSelectionCaseSummary.setReDebtLatePmtInd(loanSelectionCaseSummary.getReDebtLatePmtInd());
		newLoanSelectionCaseSummary.setReTaxCurrInd(loanSelectionCaseSummary.getReTaxCurrInd());
		newLoanSelectionCaseSummary.setReTaxDelinqInd(loanSelectionCaseSummary.getReTaxDelinqInd());
		newLoanSelectionCaseSummary.setRfncCd(loanSelectionCaseSummary.getRfncCd());
		newLoanSelectionCaseSummary.setRfncNextCaseNbr(loanSelectionCaseSummary.getRfncNextCaseNbr());
		newLoanSelectionCaseSummary.setRprSetAside(loanSelectionCaseSummary.getRprSetAside());
		newLoanSelectionCaseSummary.setRiFamilySize(loanSelectionCaseSummary.getRiFamilySize());
		newLoanSelectionCaseSummary.setRiShortfallAmt(loanSelectionCaseSummary.getRiShortfallAmt());
		newLoanSelectionCaseSummary.setRiStandardAmt(loanSelectionCaseSummary.getRiStandardAmt());
		newLoanSelectionCaseSummary.setRiTotalAmt(loanSelectionCaseSummary.getRiTotalAmt());
		newLoanSelectionCaseSummary.setRevolveDebtCurrInd(loanSelectionCaseSummary.getRevolveDebtCurrInd());
		newLoanSelectionCaseSummary.setRevolveDebtLatePmtInd(loanSelectionCaseSummary.getRevolveDebtLatePmtInd());
		newLoanSelectionCaseSummary.setNbrhdCd(loanSelectionCaseSummary.getNbrhdCd());
		newLoanSelectionCaseSummary.setRcvSaleDt(loanSelectionCaseSummary.getRcvSaleDt());
		newLoanSelectionCaseSummary.setScndryFncSrc(loanSelectionCaseSummary.getScndryFncSrc());
		newLoanSelectionCaseSummary.setSecondaryFinanceAmt(loanSelectionCaseSummary.getSecondaryFinanceAmt());
		newLoanSelectionCaseSummary.setSellerCntrbtn(loanSelectionCaseSummary.getSellerCntrbtn());
		newLoanSelectionCaseSummary.setSendMicInd(loanSelectionCaseSummary.getSendMicInd());
		newLoanSelectionCaseSummary.setOldSrvcrMtgee(loanSelectionCaseSummary.getOldSrvcrMtgee());
		newLoanSelectionCaseSummary.setSrvcrMtgee5A43(loanSelectionCaseSummary.getSrvcrMtgee5A43());
		newLoanSelectionCaseSummary.setSnglDsbrseLmpSumPmtOpt(loanSelectionCaseSummary.getSnglDsbrseLmpSumPmtOpt());
		newLoanSelectionCaseSummary.setPct1Fmly(loanSelectionCaseSummary.getPct1Fmly());
		newLoanSelectionCaseSummary.setSoaCd(loanSelectionCaseSummary.getSoaCd());
		newLoanSelectionCaseSummary.setSpnsrMtgee10Id(loanSelectionCaseSummary.getSpnsrMtgee10Id());
		newLoanSelectionCaseSummary.setStrmnlnRefiType(loanSelectionCaseSummary.getStrmnlnRefiType());
		newLoanSelectionCaseSummary.setSbdvsnSpotLot(loanSelectionCaseSummary.getSbdvsnSpotLot());
		newLoanSelectionCaseSummary.setTaxesInsrncFrstYrAmt(loanSelectionCaseSummary.getTaxesInsrncFrstYrAmt());
		newLoanSelectionCaseSummary.setTerm(loanSelectionCaseSummary.getTerm());
		newLoanSelectionCaseSummary.setTermTypCd(loanSelectionCaseSummary.getTermTypCd());
		newLoanSelectionCaseSummary.setTotAnnEffIncm(loanSelectionCaseSummary.getTotAnnEffIncm());
		newLoanSelectionCaseSummary.setTotMnthlyEffIncm(loanSelectionCaseSummary.getTotMnthlyEffIncm());
		newLoanSelectionCaseSummary.setTotAcqCosts(loanSelectionCaseSummary.getTotAcqCosts());
		newLoanSelectionCaseSummary.setTrnsmsnTyp(loanSelectionCaseSummary.getTrnsmsnTyp());
		newLoanSelectionCaseSummary.setUfmipPdAmt(loanSelectionCaseSummary.getUfmipPdAmt());
		newLoanSelectionCaseSummary.setUfmipPdCash(loanSelectionCaseSummary.getUfmipPdCash());
		newLoanSelectionCaseSummary.setUfmipEarnedCurrMm(loanSelectionCaseSummary.getUfmipEarnedCurrMm());
		newLoanSelectionCaseSummary.setUfmipFactor(loanSelectionCaseSummary.getUfmipFactor());
		newLoanSelectionCaseSummary.setUnderwriterId(loanSelectionCaseSummary.getUnderwriterId());
		newLoanSelectionCaseSummary.setUndrwrtingMtgee5(loanSelectionCaseSummary.getUndrwrtingMtgee5());
		newLoanSelectionCaseSummary.setUnpdBal(loanSelectionCaseSummary.getUnpdBal());
		newLoanSelectionCaseSummary.setValPlusClsng(loanSelectionCaseSummary.getValPlusClsng());
		newLoanSelectionCaseSummary.setBldgTyp(loanSelectionCaseSummary.getBldgTyp());
		newLoanSelectionCaseSummary.setReo100DownPmtProgInd(loanSelectionCaseSummary.getReo100DownPmtProgInd());
		newLoanSelectionCaseSummary.setAppraiserName(loanSelectionCaseSummary.getAppraiserName());
		newLoanSelectionCaseSummary.setAssetsAfterClosingUw(loanSelectionCaseSummary.getAssetsAfterClosingUw());
		newLoanSelectionCaseSummary.setAssumedLoanInd(loanSelectionCaseSummary.getAssumedLoanInd());
		newLoanSelectionCaseSummary.setRatioFixTeiEndrs(loanSelectionCaseSummary.getRatioFixTeiEndrs());
		newLoanSelectionCaseSummary.setRatioFixTeiUw(loanSelectionCaseSummary.getRatioFixTeiUw());
		newLoanSelectionCaseSummary.setBackToWorkInd(loanSelectionCaseSummary.getBackToWorkInd());
		newLoanSelectionCaseSummary.setBnkrptcyAnyInd(loanSelectionCaseSummary.getBnkrptcyAnyInd());
		newLoanSelectionCaseSummary.setBorr1FirstTimeBuyerInd(loanSelectionCaseSummary.getBorr1FirstTimeBuyerInd());
		newLoanSelectionCaseSummary.setBorr1Name(loanSelectionCaseSummary.getBorr1Name());
		newLoanSelectionCaseSummary.setBorr1RentingInd(loanSelectionCaseSummary.getBorr1RentingInd());
		newLoanSelectionCaseSummary.setBorr1SelfEmplInd(loanSelectionCaseSummary.getBorr1SelfEmplInd());
		newLoanSelectionCaseSummary.setBorr1Ssn(loanSelectionCaseSummary.getBorr1Ssn());
		newLoanSelectionCaseSummary.setBorr2Name(loanSelectionCaseSummary.getBorr2Name());
		newLoanSelectionCaseSummary.setBorr2Ssn(loanSelectionCaseSummary.getBorr2Ssn());
		newLoanSelectionCaseSummary.setBorrReqdInvestToClose(loanSelectionCaseSummary.getBorrReqdInvestToClose());
		newLoanSelectionCaseSummary.setBorrHsngExpEndrs(loanSelectionCaseSummary.getBorrHsngExpEndrs());
		newLoanSelectionCaseSummary.setBorrHsngExpUw(loanSelectionCaseSummary.getBorrHsngExpUw());
		newLoanSelectionCaseSummary.setBorrPaidClosingCosts(loanSelectionCaseSummary.getBorrPaidClosingCosts());
		newLoanSelectionCaseSummary.setBuildingOnOwnLandInd(loanSelectionCaseSummary.getBuildingOnOwnLandInd());
		newLoanSelectionCaseSummary.setBnkrptcyChptr13Ind(loanSelectionCaseSummary.getBnkrptcyChptr13Ind());
		newLoanSelectionCaseSummary.setBnkrptcyChptr7Ind(loanSelectionCaseSummary.getBnkrptcyChptr7Ind());
		newLoanSelectionCaseSummary.setCombinedLoanToValuePct(loanSelectionCaseSummary.getCombinedLoanToValuePct());
		newLoanSelectionCaseSummary.setApplicationDate(loanSelectionCaseSummary.getApplicationDate());
		newLoanSelectionCaseSummary.setDateOfPriorSale(loanSelectionCaseSummary.getDateOfPriorSale());
		newLoanSelectionCaseSummary.setDcsnCdEndrs(loanSelectionCaseSummary.getDcsnCdEndrs());
		newLoanSelectionCaseSummary.setDcsnCdUw(loanSelectionCaseSummary.getDcsnCdUw());
		newLoanSelectionCaseSummary.setDefaultEpisodeExistsInd(loanSelectionCaseSummary.getDefaultEpisodeExistsInd());
		newLoanSelectionCaseSummary.setDisasterInd(loanSelectionCaseSummary.getDisasterInd());
		newLoanSelectionCaseSummary.setEffDateAprslUpdate(loanSelectionCaseSummary.getEffDateAprslUpdate());
		newLoanSelectionCaseSummary.setEndrsBynd60DaysCloseInd(loanSelectionCaseSummary.getEndrsBynd60DaysCloseInd());
		newLoanSelectionCaseSummary.setEscrowAmount(loanSelectionCaseSummary.getEscrowAmount());
		newLoanSelectionCaseSummary.setFicoDecisionScoreUw(loanSelectionCaseSummary.getFicoDecisionScoreUw());
		newLoanSelectionCaseSummary.setFicoDecisionScoreEndrs(loanSelectionCaseSummary.getFicoDecisionScoreEndrs());
		newLoanSelectionCaseSummary.setFlippingCategory2Ind(loanSelectionCaseSummary.getFlippingCategory2Ind());
		newLoanSelectionCaseSummary.setRatioTotPmtToTotIncUw(loanSelectionCaseSummary.getRatioTotPmtToTotIncUw());
		newLoanSelectionCaseSummary.setRatioTotPmtToTotIncEndrs(loanSelectionCaseSummary.getRatioTotPmtToTotIncEndrs());
		newLoanSelectionCaseSummary.setGiftLtr2Source(loanSelectionCaseSummary.getGiftLtr2Source());
		newLoanSelectionCaseSummary.setHecmCounselCertNo(loanSelectionCaseSummary.getHecmCounselCertNo());
		newLoanSelectionCaseSummary.setHudReoRepairAmt(loanSelectionCaseSummary.getHudReoRepairAmt());
		newLoanSelectionCaseSummary.setInsurAppInTimeInd(loanSelectionCaseSummary.getInsurAppInTimeInd());
		newLoanSelectionCaseSummary.setInvest2ndResidInd(loanSelectionCaseSummary.getInvest2ndResidInd());
		newLoanSelectionCaseSummary.setLastServicingMrtgXferDt(loanSelectionCaseSummary.getLastServicingMrtgXferDt());
		newLoanSelectionCaseSummary.setLoanOfficer(loanSelectionCaseSummary.getLoanOfficer());
		newLoanSelectionCaseSummary.setLoanOfficerNmls(loanSelectionCaseSummary.getLoanOfficerNmls());
		newLoanSelectionCaseSummary.setMandatoryObligBorrAmt(loanSelectionCaseSummary.getMandatoryObligBorrAmt());
		newLoanSelectionCaseSummary.setMandatoryObligLendAmt(loanSelectionCaseSummary.getMandatoryObligLendAmt());
		newLoanSelectionCaseSummary.setManUwStretchRatioInd(loanSelectionCaseSummary.getManUwStretchRatioInd());
		newLoanSelectionCaseSummary.setMaxClaimAmt(loanSelectionCaseSummary.getMaxClaimAmt());
		newLoanSelectionCaseSummary.setMaxRate(loanSelectionCaseSummary.getMaxRate());
		newLoanSelectionCaseSummary.setCurrentAtEndorseInd(loanSelectionCaseSummary.getCurrentAtEndorseInd());
		newLoanSelectionCaseSummary.setNonOccupyingBorrInd(loanSelectionCaseSummary.getNonOccupyingBorrInd());
		newLoanSelectionCaseSummary.setOrgntngMtgeeNmlsId(loanSelectionCaseSummary.getOrgntngMtgeeNmlsId());
		newLoanSelectionCaseSummary.setOriginationFee(loanSelectionCaseSummary.getOriginationFee());
		newLoanSelectionCaseSummary.setPaymentPlan(loanSelectionCaseSummary.getPaymentPlan());
		newLoanSelectionCaseSummary.setPriceOfPriorSale(loanSelectionCaseSummary.getPriceOfPriorSale());
		newLoanSelectionCaseSummary.setPriorSaleWithinLast3yrInd(loanSelectionCaseSummary.getPriorSaleWithinLast3yrInd());
		newLoanSelectionCaseSummary.setQualifiedMrtgPointsAndFees(loanSelectionCaseSummary.getQualifiedMrtgPointsAndFees());
		newLoanSelectionCaseSummary.setRepairCompletionDate(loanSelectionCaseSummary.getRepairCompletionDate());
		newLoanSelectionCaseSummary.setSalesPrice(loanSelectionCaseSummary.getSalesPrice());
		newLoanSelectionCaseSummary.setSalePriceGtrAcqCostInd(loanSelectionCaseSummary.getSalePriceGtrAcqCostInd());
		newLoanSelectionCaseSummary.setSecondaryFinanceExistsInd(loanSelectionCaseSummary.getSecondaryFinanceExistsInd());
		newLoanSelectionCaseSummary.setSellerCntrbtnPcnt(loanSelectionCaseSummary.getSellerCntrbtnPcnt());
		newLoanSelectionCaseSummary.setSiteType(loanSelectionCaseSummary.getSiteType());
		newLoanSelectionCaseSummary.setSpecialProgram(loanSelectionCaseSummary.getSpecialProgram());
		newLoanSelectionCaseSummary.setTenYrRateLockInd(loanSelectionCaseSummary.getTenYrRateLockInd());
		newLoanSelectionCaseSummary.setTotAssetsEndrs(loanSelectionCaseSummary.getTotAssetsEndrs());
		newLoanSelectionCaseSummary.setTotAssetsUw(loanSelectionCaseSummary.getTotAssetsUw());
		newLoanSelectionCaseSummary.setTotClsngCstsEndrs(loanSelectionCaseSummary.getTotClsngCstsEndrs());
		newLoanSelectionCaseSummary.setTotClsngCstsUw(loanSelectionCaseSummary.getTotClsngCstsUw());
		newLoanSelectionCaseSummary.setTotFixedPymtEndrs(loanSelectionCaseSummary.getTotFixedPymtEndrs());
		newLoanSelectionCaseSummary.setTotFixedPymtUw(loanSelectionCaseSummary.getTotFixedPymtUw());
		newLoanSelectionCaseSummary.setTotMnthlyMtgPymtEndrs(loanSelectionCaseSummary.getTotMnthlyMtgPymtEndrs());
		newLoanSelectionCaseSummary.setTotMnthlyMtgPymtUw(loanSelectionCaseSummary.getTotMnthlyMtgPymtUw());
		newLoanSelectionCaseSummary.setTotalRequiredFundsToClose(loanSelectionCaseSummary.getTotalRequiredFundsToClose());
		newLoanSelectionCaseSummary.setUnderwritingMethod(loanSelectionCaseSummary.getUnderwritingMethod());
		newLoanSelectionCaseSummary.setYearBuilt(loanSelectionCaseSummary.getYearBuilt());
		newLoanSelectionCaseSummary.setFrclsrStrtDt(loanSelectionCaseSummary.getFrclsrStrtDt());
		newLoanSelectionCaseSummary.setRefinanceInd(loanSelectionCaseSummary.getRefinanceInd());
		newLoanSelectionCaseSummary.setPropAddr1(loanSelectionCaseSummary.getPropAddr1());
		newLoanSelectionCaseSummary.setPropAddr2(loanSelectionCaseSummary.getPropAddr2());
		newLoanSelectionCaseSummary.setPropAddrCity(loanSelectionCaseSummary.getPropAddrCity());
		newLoanSelectionCaseSummary.setPropAddrZip(loanSelectionCaseSummary.getPropAddrZip());
		newLoanSelectionCaseSummary.setCoborr1FicoCnt(loanSelectionCaseSummary.getCoborr1FicoCnt());
		newLoanSelectionCaseSummary.setCndtnlCmmtmntRvwr(loanSelectionCaseSummary.getCndtnlCmmtmntRvwr());
		newLoanSelectionCaseSummary.setCndInd(loanSelectionCaseSummary.getCndInd());
		newLoanSelectionCaseSummary.setCurrDlnqncyMnthsDlnqnt(loanSelectionCaseSummary.getCurrDlnqncyMnthsDlnqnt());
		newLoanSelectionCaseSummary.setEndrsAprslDayDiff(loanSelectionCaseSummary.getEndrsAprslDayDiff());
		newLoanSelectionCaseSummary.setEndrsPrcsngDt(loanSelectionCaseSummary.getEndrsPrcsngDt());
		newLoanSelectionCaseSummary.setEscrwFlag(loanSelectionCaseSummary.getEscrwFlag());
		newLoanSelectionCaseSummary.setGeocdFlag(loanSelectionCaseSummary.getGeocdFlag());
		newLoanSelectionCaseSummary.setIndemDt(loanSelectionCaseSummary.getIndemDt());
		newLoanSelectionCaseSummary.setIndemStat(loanSelectionCaseSummary.getIndemStat());
		newLoanSelectionCaseSummary.setIndemTerm(loanSelectionCaseSummary.getIndemTerm());
		newLoanSelectionCaseSummary.setIndemType(loanSelectionCaseSummary.getIndemType());
		newLoanSelectionCaseSummary.setLastCaseQlfctnEvntDt(loanSelectionCaseSummary.getLastCaseQlfctnEvntDt());
		newLoanSelectionCaseSummary.setMiscEbndrInd(loanSelectionCaseSummary.getMiscEbndrInd());
		newLoanSelectionCaseSummary.setNonperfLoanInd(loanSelectionCaseSummary.getNonperfLoanInd());
		newLoanSelectionCaseSummary.setNorIssueDt(loanSelectionCaseSummary.getNorIssueDt());
		newLoanSelectionCaseSummary.setOrgntngMrtggeeInstStat(loanSelectionCaseSummary.getOrgntngMrtggeeInstStat());
		newLoanSelectionCaseSummary.setPmtsBfrFrstMissdPmt(loanSelectionCaseSummary.getPmtsBfrFrstMissdPmt());
		newLoanSelectionCaseSummary.setPrequalCnt(loanSelectionCaseSummary.getPrequalCnt());
		newLoanSelectionCaseSummary.setPriceExcldngClsngCosts(loanSelectionCaseSummary.getPriceExcldngClsngCosts());
		newLoanSelectionCaseSummary.setPriceIncldngClsngCosts(loanSelectionCaseSummary.getPriceIncldngClsngCosts());
		newLoanSelectionCaseSummary.setPrrCaseMxmmClmAmt(loanSelectionCaseSummary.getPrrCaseMxmmClmAmt());
		newLoanSelectionCaseSummary.setRefiPrrCaseNbr(loanSelectionCaseSummary.getRefiPrrCaseNbr());
		newLoanSelectionCaseSummary.setSpnsrMrtggeeIdInst(loanSelectionCaseSummary.getSpnsrMrtggeeIdInst());
		newLoanSelectionCaseSummary.setTerm15YrInd(loanSelectionCaseSummary.getTerm15YrInd());
		newLoanSelectionCaseSummary.setUndrsrvdInd(loanSelectionCaseSummary.getUndrsrvdInd());
		newLoanSelectionCaseSummary.setBorr1YrsAtCurrJob(loanSelectionCaseSummary.getBorr1YrsAtCurrJob());
		newLoanSelectionCaseSummary.setGiftLtr2Tin(loanSelectionCaseSummary.getGiftLtr2Tin());
		newLoanSelectionCaseSummary.setMnfctrdHusngInd(loanSelectionCaseSummary.getMnfctrdHusngInd());
		newLoanSelectionCaseSummary.setMnthlyPrncplAndIntrst(loanSelectionCaseSummary.getMnthlyPrncplAndIntrst());
		newLoanSelectionCaseSummary.setProdType(loanSelectionCaseSummary.getProdType());
		newLoanSelectionCaseSummary.setExpectedRate(loanSelectionCaseSummary.getExpectedRate());
		newLoanSelectionCaseSummary.setYrMoConstCmpltd(loanSelectionCaseSummary.getYrMoConstCmpltd());

		return newLoanSelectionCaseSummary;
	}

	@Transactional
	public LoanSelectionCaseSummary createLoanSelectionCaseSummary(LoanSelectionCaseSummary loanSelectionCaseSummary, SelectionRequest selectionRequest) {
		loanSelectionCaseSummary.setSelectionRequest(selectionRequest);
		loanSelectionCaseSummary.setCreatedBy(securityService.getUserId());
		loanSelectionCaseSummary.setCreatedTs(new Date());
		loanSelectionCaseSummary = loanSelectionCaseSummaryRepository.save(loanSelectionCaseSummary);

		return loanSelectionCaseSummary;
	}

	@Transactional
	public LoanSelectionPending createLoanSelectionPending(
		LoanSelectionCaseSummary loanSelectionCaseSummary,
		Batch batch,
		ReviewTypeRef reviewType,
		SelectionReason selectionReason,
		ReviewScopeRef reviewScope,
		ReviewLocation reviewLocation
	) {
		Date now = new Date();

		LoanSelectionPending loanSelectionPending = new LoanSelectionPending();
		loanSelectionPending.setLoanSelectionCaseSummary(loanSelectionCaseSummary);
		loanSelectionPending.setCaseNumber(loanSelectionCaseSummary.getCaseNumber());
		loanSelectionPending.setBatch(batch);
		loanSelectionPending.setReviewTypeRef(reviewType);
		String mtgee5;
		if (
			reviewType.getReviewTypeCd().equals(ReviewTypeCodes.UNDERWRITING) ||
			reviewType.getReviewTypeCd().equals(ReviewTypeCodes.COMPREHENSIVE)
		) {
			mtgee5 = loanSelectionCaseSummary.getUndrwrtingMtgee5();
		} else if (reviewType.getReviewTypeCd().equals(ReviewTypeCodes.SERVICING)) {
			mtgee5 = loanSelectionCaseSummary.getSrvcrMtgee5A43();
		} else {
			throw new RuntimeException("Unhandled reviewTypeCd: " + reviewType.getReviewTypeCd() + " for ReviewTypeRef " + reviewType.getReviewTypeId());
		}
		loanSelectionPending.setLender(lenderService.getOrCreateDummyLender(mtgee5));
		loanSelectionPending.setSelectionReason(selectionReason);
		loanSelectionPending.setReviewScopeRef(reviewScope);
		loanSelectionPending.setReviewLocation(reviewLocation);
		loanSelectionPending.setProductTypeRef(productTypeRefRepository.findByProductTypeCd(loanSelectionCaseSummary.getProdType()));
		loanSelectionPending.setSelectionDt(now);
		loanSelectionPending.setCreatedBy(securityService.getUserId());
		loanSelectionPending.setCreatedTs(now);
		loanSelectionPending.setAutoSelectionIndicator('N');
		loanSelectionPending = loanSelectionPendingRepository.save(loanSelectionPending);

		if (batch != null) {
			batch.getLoanSelectionPendings().add(loanSelectionPending);
		}

		return loanSelectionPending;
	}

	@Transactional
	public LoanSelection createLoanSelection(
		LoanSelectionCaseSummary loanSelectionCaseSummary,
		Batch batch,
		ReviewTypeRef reviewType,
		SelectionReason selectionReason,
		ReviewScopeRef reviewScope,
		ReviewLocation reviewLocation,
		Review completedReview
	) {

		LoanSelection loanSelection = new LoanSelection();
		String mtgee5;
		if (
			reviewType.getReviewTypeCd().equals(ReviewTypeCodes.UNDERWRITING) ||
			reviewType.getReviewTypeCd().equals(ReviewTypeCodes.COMPREHENSIVE)
		) {
			mtgee5 = loanSelectionCaseSummary.getUndrwrtingMtgee5();
		} else if (reviewType.getReviewTypeCd().equals(ReviewTypeCodes.SERVICING)) {
			mtgee5 = loanSelectionCaseSummary.getSrvcrMtgee5A43();
		} else {
			throw new RuntimeException("Unhandled reviewTypeCd: " + reviewType.getReviewTypeCd() + " for ReviewTypeRef " + reviewType.getReviewTypeId());
		}
		Date now = new Date();
		loanSelection.setLender(lenderService.getOrCreateDummyLender(mtgee5));
		loanSelection.setBatch(batch);
		loanSelection.setLoanSelectionCaseSummary(loanSelectionCaseSummary);
		loanSelection.setLoanSelectionStatusRef(loanSelectionStatusRefRepository.findByCode(LoanSelectionStatusCodes.SELECTED));
		loanSelection.setReviewTypeRef(reviewType);
		loanSelection.setProductTypeRef(productTypeRefRepository.findByProductTypeCd(loanSelectionCaseSummary.getProdType()));
		loanSelection.setSelectionReason(selectionReason);
		loanSelection.setReviewScopeRef(reviewScope);
		loanSelection.setReviewLocation(reviewLocation);
		loanSelection.setDistributionDt(now);
		loanSelection.setCaseNumber(loanSelectionCaseSummary.getCaseNumber());
		loanSelection.setQcReview(completedReview);
		loanSelection.setSelectionDt(now);
		loanSelection.setCreatedBy(securityService.getUserId());
		loanSelection.setCreatedTs(now);
		loanSelection.setAutoSelectionIndicator('N');
		loanSelection.setBorrName(loanSelectionCaseSummary.getBorr1Name());
		loanSelection.setCsEstabDt(loanSelectionCaseSummary.getCsEstabDt());
		loanSelection.setEndrsmntDt(loanSelectionCaseSummary.getEndrsmntDt());
		loanSelection.setPropAddr1(loanSelectionCaseSummary.getPropAddr1());
		loanSelection.setSelectionRequest(loanSelectionCaseSummary.getSelectionRequest());
		loanSelection = loanSelectionRepository.save(loanSelection);

		if (batch != null) {
			batch.getLoanSelections().add(loanSelection);
		}

		return loanSelection;
	}

	@Transactional
	public LoanSelectionPending distributeLoanSelectionPending(LoanSelectionPending loanSelectionPending, ReviewLocation reviewLocation) {
		loanSelectionPending.setReviewLocation(reviewLocation);
		loanSelectionPending.setUpdatedBy(securityService.getUserId());
		loanSelectionPending.setUpdatedTs(new Date());
		loanSelectionPending = loanSelectionPendingRepository.save(loanSelectionPending);

		return loanSelectionPending;
	}

	@Transactional
	public LoanSelection distributeLoanSelection(LoanSelection loanSelection, String reviewLocationId, boolean isAutoSeleted) {
		ReviewLocation reviewLocation = reviewLocationRepository.findOne(reviewLocationId);
		if (reviewLocation == null) {
			throw new BadRequestException("No ReviewLocation for id " + reviewLocationId);
		}

		return distributeLoanSelection(loanSelection, reviewLocation, isAutoSeleted);
	}

	@Transactional
	public LoanSelection distributeLoanSelection(LoanSelection loanSelection, ReviewLocation reviewLocation, boolean isAutoSeleted) {
		loanSelection.setReviewLocation(reviewLocation);
		loanSelection.setLoanSelectionStatusRef(loanSelectionStatusRefRepository.findByCode(LoanSelectionStatusCodes.DISTRIBUTED));
		loanSelection.setUpdatedBy(securityService.getUserId());
		loanSelection.setUpdatedTs(new Date());
		if (isAutoSeleted) {
			loanSelection.setDistributionDt(DateUtils.getLastMonthLastDayLastHour());
		} else {
			loanSelection.setDistributionDt(new Date());
		}
		loanSelection = loanSelectionRepository.save(loanSelection);

		return loanSelection;
	}

	@Transactional
	public LoanSelection cancelLoanSelection(LoanSelection loanSelection) {
		loanSelection.setLoanSelectionStatusRef(loanSelectionStatusRefRepository.findByCode(LoanSelectionStatusCodes.CANCELLED));
		loanSelection.setUpdatedBy(securityService.getUserId());
		loanSelection.setUpdatedTs(new Date());
		loanSelection = loanSelectionRepository.save(loanSelection);

		return loanSelection;
	}
}
