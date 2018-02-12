package gov.hud.lrs.services.bizservice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.io.FilenameUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.hud.lrs.common.dto.FhacUserDTO;
import gov.hud.lrs.common.dto.MuleBinderReceiptDTO;
import gov.hud.lrs.common.dto.MuleBinderReceiptResponseDTO;
import gov.hud.lrs.common.dto.MuleBinderReceiptResponseDTO.CaseBinderDTO;
import gov.hud.lrs.common.dto.MuleBinderRequestDTO;
import gov.hud.lrs.common.dto.MuleBinderRequestResponseDTO;
import gov.hud.lrs.common.dto.MuleBinderRequestResponseDTO.EsbExceptionDTO;
import gov.hud.lrs.common.dto.MuleCaseGetDetailDTO;
import gov.hud.lrs.common.dto.MuleIndemnificationFileDTO;
import gov.hud.lrs.common.dto.MuleIndemnificationInputDTO;
import gov.hud.lrs.common.dto.MuleIndemnificationOutputDTO;
import gov.hud.lrs.common.dto.MuleUnderwriterSearchResultDTO;
import gov.hud.lrs.common.entity.LoanSelectionCaseSummary;
import gov.hud.lrs.common.entity.ext.LrsCaseRecord;
import gov.hud.lrs.common.repository.ext.LrsCaseRecordRepository;

@Service
public class DevMuleService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired private LrsCaseRecordRepository lrsCaseRecordRepository;

 	private Map<String, FhacUserDTO> userIdToFhacUserDTO;
 	
 	private Map<String, MuleUnderwriterSearchResultDTO> underwriterIdToUnderwriterDTO;
 	
 	private DozerBeanMapper dozerMapper = new DozerBeanMapper();

	private Map<String, Date> binderReceivedDateByCaseNumber = new HashMap<String, Date>();	// used to simulate incoming binders

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
 	public DevMuleService() {
 		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream fhacUserStream = null;
			List<FhacUserDTO> fhacUserDTOs = new ArrayList<FhacUserDTO>();
			try {
				fhacUserStream = getClass().getClassLoader().getResourceAsStream("fhacusers.json");
				logger.debug("Reading fhac users from json file.");
				userIdToFhacUserDTO = new HashMap<String, FhacUserDTO>();
				fhacUserDTOs = mapper.readValue(fhacUserStream, new TypeReference<List<FhacUserDTO>>() {});
			} finally {
				if(fhacUserStream != null) {
					try {
						fhacUserStream.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
			for (FhacUserDTO fhacUserDTO : fhacUserDTOs) {
				userIdToFhacUserDTO.put(fhacUserDTO.getUserId(), fhacUserDTO);
			}
			
			InputStream underwriterStream = null;
			List<MuleUnderwriterSearchResultDTO> underwriterDTOs = new ArrayList<MuleUnderwriterSearchResultDTO>();
			try {
				underwriterStream = getClass().getClassLoader().getResourceAsStream("underwriters.json");
				logger.debug("Reading underwriters from json file.");
				underwriterIdToUnderwriterDTO = new HashMap<String, MuleUnderwriterSearchResultDTO>();
				underwriterDTOs = mapper.readValue(underwriterStream, new TypeReference<List<MuleUnderwriterSearchResultDTO>>() {});
			} finally {
				if(underwriterStream != null) {
					try {
						underwriterStream.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
			for (MuleUnderwriterSearchResultDTO underwriterDTO : underwriterDTOs) {
				underwriterDTO.getProperties().setStatusCode("0");
				underwriterIdToUnderwriterDTO.put(underwriterDTO.getProperties().getUnderwriterId(), underwriterDTO);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		BeanMappingBuilder beanMappingBuilder = new BeanMappingBuilder() {
			protected void configure() {
				mapping(LrsCaseRecord.class, LoanSelectionCaseSummary.class, TypeMappingOptions.trimStrings(true))
					.exclude("selectionId")
				;
			}
		};
		dozerMapper.addMapping(beanMappingBuilder);
 	}

	public FhacUserDTO getFhacUser(String userId) {
		return userIdToFhacUserDTO.get(userId);
	}

	public List<FhacUserDTO> getAllFhacUsers() {
		return new ArrayList<FhacUserDTO>(userIdToFhacUserDTO.values());
	}
	
	public MuleUnderwriterSearchResultDTO getUnderwriter(String underwriterId) {
		return underwriterIdToUnderwriterDTO.get(underwriterId);
	}
	
	public MuleCaseGetDetailDTO getLoanSelectionCaseSummary(String caseNumber) {
		LrsCaseRecord lrsCaseRecord = lrsCaseRecordRepository.findByCaseNumber(caseNumber);
		return (lrsCaseRecord == null) ? null : convertLoanSelectionCaseSummaryToMuleCaseGetDetailDTO(lrsCaseRecord);
	}

	public List<MuleCaseGetDetailDTO> getLoanSelectionCaseSummaries(String[] caseNumbers) {
		List<LrsCaseRecord> lrsCaseRecords = lrsCaseRecordRepository.findByCaseNumberIn(Arrays.asList(caseNumbers));
		List<MuleCaseGetDetailDTO> muleCaseGetDetailDTOs = new ArrayList<MuleCaseGetDetailDTO>(); 
		for (LrsCaseRecord lrsCaseRecord : lrsCaseRecords) {
			try {
				muleCaseGetDetailDTOs.add(convertLoanSelectionCaseSummaryToMuleCaseGetDetailDTO(lrsCaseRecord));
			} catch (Throwable t) {
				logger.warn("Couldn't convert LrsCaseRecord " + lrsCaseRecord.getCaseNumber());
			}
		}
		
		return muleCaseGetDetailDTOs;
	}
	
	private MuleCaseGetDetailDTO convertLoanSelectionCaseSummaryToMuleCaseGetDetailDTO(LrsCaseRecord lrsCaseRecord) {
		MuleCaseGetDetailDTO muleCaseGetDetailDTO = new MuleCaseGetDetailDTO();
		muleCaseGetDetailDTO.case_number = (lrsCaseRecord.getCaseNumber() != null) ? lrsCaseRecord.getCaseNumber().toString().trim() : null;
		muleCaseGetDetailDTO.prod_type = (lrsCaseRecord.getProdType() != null) ? lrsCaseRecord.getProdType().toString().trim() : null;

		muleCaseGetDetailDTO.addtnl_10pct_ipl_usage_amt = (lrsCaseRecord.getAddtnl10pctIplUsageAmt() != null) ? lrsCaseRecord.getAddtnl10pctIplUsageAmt().toString().trim() : null;
		muleCaseGetDetailDTO.addtnl_10pct_ipl_usage_ind = (lrsCaseRecord.getAddtnl10pctIplUsageInd() != null) ? lrsCaseRecord.getAddtnl10pctIplUsageInd().toString().trim() : null;
		muleCaseGetDetailDTO.adp_code = (lrsCaseRecord.getAdpCode() != null) ? lrsCaseRecord.getAdpCode().toString().trim() : null;
		muleCaseGetDetailDTO.allow_clsg_cost = (lrsCaseRecord.getAllowClsgCost() != null) ? lrsCaseRecord.getAllowClsgCost().toString().trim() : null;
		muleCaseGetDetailDTO.amort_typ_cd = (lrsCaseRecord.getAmortTypCd() != null) ? lrsCaseRecord.getAmortTypCd().toString().trim() : null;
		muleCaseGetDetailDTO.application_date = (lrsCaseRecord.getApplicationDate() != null) ? lrsCaseRecord.getApplicationDate().toString().trim() : null;
		muleCaseGetDetailDTO.aplctn_mthd = (lrsCaseRecord.getAplctnMthd() != null) ? lrsCaseRecord.getAplctnMthd().toString().trim() : null;
		muleCaseGetDetailDTO.aprsl_cmpltn_dt = (lrsCaseRecord.getAprslCmpltnDt() != null) ? df.format(lrsCaseRecord.getAprslCmpltnDt()) : null;
		muleCaseGetDetailDTO.appraiser_name = (lrsCaseRecord.getAppraiserName() != null) ? lrsCaseRecord.getAppraiserName().toString().trim() : null;
		muleCaseGetDetailDTO.arm_adj_prd = (lrsCaseRecord.getArmAdjPrd() != null) ? lrsCaseRecord.getArmAdjPrd().toString().trim() : null;
		muleCaseGetDetailDTO.arm_ind = (lrsCaseRecord.getArmInd() != null) ? lrsCaseRecord.getArmInd().toString().trim() : null;
		muleCaseGetDetailDTO.arm_indx_ind = (lrsCaseRecord.getArmIndxInd() != null) ? lrsCaseRecord.getArmIndxInd().toString().trim() : null;
		muleCaseGetDetailDTO.arm_indx_expctd_rt = (lrsCaseRecord.getArmIndxExpctdRt() != null) ? lrsCaseRecord.getArmIndxExpctdRt().toString().trim() : null;
		muleCaseGetDetailDTO.arm_mrgn_amt = (lrsCaseRecord.getArmMrgnAmt() != null) ? lrsCaseRecord.getArmMrgnAmt().toString().trim() : null;
		muleCaseGetDetailDTO.arm_dt = (lrsCaseRecord.getArmDt() != null) ? df.format(lrsCaseRecord.getArmDt()) : null;
		muleCaseGetDetailDTO.assumed_loan_ind = (lrsCaseRecord.getAssumedLoanInd() != null) ? lrsCaseRecord.getAssumedLoanInd().toString().trim() : null;
		muleCaseGetDetailDTO.assets_after_closing_uw = (lrsCaseRecord.getAssetsAfterClosingUw() != null) ? lrsCaseRecord.getAssetsAfterClosingUw().toString().trim() : null;
		muleCaseGetDetailDTO.count_aus = (lrsCaseRecord.getCountAus() != null) ? lrsCaseRecord.getCountAus().toString().trim() : null;
		muleCaseGetDetailDTO.ratio_fix_tei_endrs = (lrsCaseRecord.getRatioFixTeiEndrs() != null) ? lrsCaseRecord.getRatioFixTeiEndrs().toString().trim() : null;
		muleCaseGetDetailDTO.ratio_fix_tei_uw = (lrsCaseRecord.getRatioFixTeiUw() != null) ? lrsCaseRecord.getRatioFixTeiUw().toString().trim() : null;
		muleCaseGetDetailDTO.back_to_work_ind = (lrsCaseRecord.getBackToWorkInd() != null) ? lrsCaseRecord.getBackToWorkInd().toString().trim() : null;
		muleCaseGetDetailDTO.buy_dwn_ind = (lrsCaseRecord.getBuyDwnInd() != null) ? lrsCaseRecord.getBuyDwnInd().toString().trim() : null;
		muleCaseGetDetailDTO.building_on_own_land_ind = (lrsCaseRecord.getBuildingOnOwnLandInd() != null) ? lrsCaseRecord.getBuildingOnOwnLandInd().toString().trim() : null;
		muleCaseGetDetailDTO.bldg_typ = (lrsCaseRecord.getBldgTyp() != null) ? lrsCaseRecord.getBldgTyp().toString().trim() : null;
		muleCaseGetDetailDTO.bnkrptcy_any_ind = (lrsCaseRecord.getBnkrptcyAnyInd() != null) ? lrsCaseRecord.getBnkrptcyAnyInd().toString().trim() : null;
		muleCaseGetDetailDTO.bnkrptcy_cd = (lrsCaseRecord.getBnkrptcyCd() != null) ? lrsCaseRecord.getBnkrptcyCd().toString().trim() : null;
		muleCaseGetDetailDTO.bnkrptcy_chptr13_ind = (lrsCaseRecord.getBnkrptcyChptr13Ind() != null) ? lrsCaseRecord.getBnkrptcyChptr13Ind().toString().trim() : null;
		muleCaseGetDetailDTO.bnkrptcy_chptr7_ind = (lrsCaseRecord.getBnkrptcyChptr7Ind() != null) ? lrsCaseRecord.getBnkrptcyChptr7Ind().toString().trim() : null;
		muleCaseGetDetailDTO.bnkrptcy_dt = (lrsCaseRecord.getBnkrptcyDt() != null) ? df.format(lrsCaseRecord.getBnkrptcyDt()) : null;
		muleCaseGetDetailDTO.borr_brth_dt = (lrsCaseRecord.getBorrBrthDt() != null) ? df.format(lrsCaseRecord.getBorrBrthDt()) : null;
		muleCaseGetDetailDTO.borr_gender = (lrsCaseRecord.getBorrGender() != null) ? lrsCaseRecord.getBorrGender().toString().trim() : null;
		muleCaseGetDetailDTO.borr_1_name = (lrsCaseRecord.getBorr1Name() != null) ? lrsCaseRecord.getBorr1Name().toString().trim() : null;
		muleCaseGetDetailDTO.borr_1_ssn = (lrsCaseRecord.getBorr1Ssn() != null) ? lrsCaseRecord.getBorr1Ssn().toString().trim() : null;
		muleCaseGetDetailDTO.borr_1_yrs_at_curr_job = (lrsCaseRecord.getBorr1YearsCurrentJob() != null) ? lrsCaseRecord.getBorr1YearsCurrentJob().toString().trim() : null;
		muleCaseGetDetailDTO.borr_2_name = (lrsCaseRecord.getBorr2Name() != null) ? lrsCaseRecord.getBorr2Name().toString().trim() : null;
		muleCaseGetDetailDTO.borr_2_ssn = (lrsCaseRecord.getBorr2Ssn() != null) ? lrsCaseRecord.getBorr2Ssn().toString().trim() : null;
		muleCaseGetDetailDTO.borr_cnsl_typ = (lrsCaseRecord.getBorrCnslTyp() != null) ? lrsCaseRecord.getBorrCnslTyp().toString().trim() : null;
		muleCaseGetDetailDTO.borr_1_self_empl_ind = (lrsCaseRecord.getBorr1SelfEmplInd() != null) ? lrsCaseRecord.getBorr1SelfEmplInd().toString().trim() : null;
		muleCaseGetDetailDTO.borr_1_first_time_buyer_ind = (lrsCaseRecord.getBorr1FirstTimeBuyerInd() != null) ? lrsCaseRecord.getBorr1FirstTimeBuyerInd().toString().trim() : null;
		muleCaseGetDetailDTO.borr_hsng_exp_endrs = (lrsCaseRecord.getBorrHsngExpEndrs() != null) ? lrsCaseRecord.getBorrHsngExpEndrs().toString().trim() : null;
		muleCaseGetDetailDTO.borr_hsng_exp_uw = (lrsCaseRecord.getBorrHsngExpUw() != null) ? lrsCaseRecord.getBorrHsngExpUw().toString().trim() : null;
		muleCaseGetDetailDTO.borr_paid_closing_costs = (lrsCaseRecord.getBorrPaidClosingCosts() != null) ? lrsCaseRecord.getBorrPaidClosingCosts().toString().trim() : null;
		muleCaseGetDetailDTO.borr_1_renting_ind = (lrsCaseRecord.getBorr1RentingInd() != null) ? lrsCaseRecord.getBorr1RentingInd().toString().trim() : null;
		muleCaseGetDetailDTO.borr_reqd_invest_to_close = (lrsCaseRecord.getBorrReqdInvestToClose() != null) ? lrsCaseRecord.getBorrReqdInvestToClose().toString().trim() : null;
		muleCaseGetDetailDTO.borr_typ = (lrsCaseRecord.getBorrTyp() != null) ? lrsCaseRecord.getBorrTyp().toString().trim() : null;
		muleCaseGetDetailDTO.bsmt_cd = (lrsCaseRecord.getBsmtCd() != null) ? lrsCaseRecord.getBsmtCd().toString().trim() : null;
		muleCaseGetDetailDTO.cs_estab_dt = (lrsCaseRecord.getCsEstabDt() != null) ? df.format(lrsCaseRecord.getCsEstabDt()) : null;
		muleCaseGetDetailDTO.cct_reins_dt = (lrsCaseRecord.getCctReinsDt() != null) ? df.format(lrsCaseRecord.getCctReinsDt()) : null;
		muleCaseGetDetailDTO.cs_typ = (lrsCaseRecord.getCsTyp() != null) ? lrsCaseRecord.getCsTyp().toString().trim() : null;
		muleCaseGetDetailDTO.claim_type = (lrsCaseRecord.getClaimType() != null) ? lrsCaseRecord.getClaimType().toString().trim() : null;
		muleCaseGetDetailDTO.clsng_dt = (lrsCaseRecord.getClsngDt() != null) ? df.format(lrsCaseRecord.getClsngDt()) : null;
		muleCaseGetDetailDTO.combined_loan_to_value_pct = (lrsCaseRecord.getCombinedLoanToValuePct() != null) ? lrsCaseRecord.getCombinedLoanToValuePct().toString().trim() : null;
		muleCaseGetDetailDTO.condo_fee_curr_ind = (lrsCaseRecord.getCondoFeeCurrInd() != null) ? lrsCaseRecord.getCondoFeeCurrInd().toString().trim() : null;
		muleCaseGetDetailDTO.condo_fee_delinq_ind = (lrsCaseRecord.getCondoFeeDelinqInd() != null) ? lrsCaseRecord.getCondoFeeDelinqInd().toString().trim() : null;
		//muleCaseGetDetailDTO.cnd_ind = (lrsCaseRecord.getCond getCndInd() != null) ? lrsCaseRecord.getCndInd().toString().trim() : null;
		//muleCaseGetDetailDTO.cndtnl_cmmtmnt_rvwr = (lrsCaseRecord.getRvw getCndtnlCmmtmntRvwr() != null) ? lrsCaseRecord.getCndtnlCmmtmntRvwr().toString().trim() : null;
		muleCaseGetDetailDTO.const_cd = (lrsCaseRecord.getConstCd() != null) ? lrsCaseRecord.getConstCd().toString().trim() : null;
		muleCaseGetDetailDTO.const_sts_cd = (lrsCaseRecord.getConstStsCd() != null) ? lrsCaseRecord.getConstStsCd().toString().trim() : null;
		//muleCaseGetDetailDTO.coborr_1_fico_cnt = (lrsCaseRecord.getFic Coborr1FicoCnt() != null) ? lrsCaseRecord.getCoborr1FicoCnt().toString().trim() : null;
		muleCaseGetDetailDTO.cf_equivical_assets_ind = (lrsCaseRecord.getCfEquivicalAssetsInd() != null) ? lrsCaseRecord.getCfEquivicalAssetsInd().toString().trim() : null;
		muleCaseGetDetailDTO.cf_expected_ssi_ind = (lrsCaseRecord.getCfExpectedSsiInd() != null) ? lrsCaseRecord.getCfExpectedSsiInd().toString().trim() : null;
		muleCaseGetDetailDTO.cf_hecm_sufficient_ind = (lrsCaseRecord.getCfHecmSufficientInd() != null) ? lrsCaseRecord.getCfHecmSufficientInd().toString().trim() : null;
		muleCaseGetDetailDTO.cf_imputed_income_ind = (lrsCaseRecord.getCfImputedIncomeInd() != null) ? lrsCaseRecord.getCfImputedIncomeInd().toString().trim() : null;
		muleCaseGetDetailDTO.cf_nbs_income_ind = (lrsCaseRecord.getCfNbsIncomeInd() != null) ? lrsCaseRecord.getCfNbsIncomeInd().toString().trim() : null;
		muleCaseGetDetailDTO.cf_other_income_ind = (lrsCaseRecord.getCfOtherIncomeInd() != null) ? lrsCaseRecord.getCfOtherIncomeInd().toString().trim() : null;
		muleCaseGetDetailDTO.cf_ot_income_ind = (lrsCaseRecord.getCfOtIncomeInd() != null) ? lrsCaseRecord.getCfOtIncomeInd().toString().trim() : null;
		muleCaseGetDetailDTO.cf_prop_pmt_hist_ind = (lrsCaseRecord.getCfPropPmtHistInd() != null) ? lrsCaseRecord.getCfPropPmtHistInd().toString().trim() : null;
		muleCaseGetDetailDTO.corp_advnc_amt = (lrsCaseRecord.getCorpAdvncAmt() != null) ? lrsCaseRecord.getCorpAdvncAmt().toString().trim() : null;
		muleCaseGetDetailDTO.cashout_refi_ind = (lrsCaseRecord.getCashoutRefiInd() != null) ? lrsCaseRecord.getCashoutRefiInd().toString().trim() : null;
		muleCaseGetDetailDTO.current_at_endorse_ind = (lrsCaseRecord.getCurrentAtEndorseInd() != null) ? lrsCaseRecord.getCurrentAtEndorseInd().toString().trim() : null;
		muleCaseGetDetailDTO.curr_dflt_90day_ind = (lrsCaseRecord.getCurrDflt90dayInd() != null) ? lrsCaseRecord.getCurrDflt90dayInd().toString().trim() : null;
		muleCaseGetDetailDTO.curr_dflt_cyc_dt = (lrsCaseRecord.getCurrDfltCycDt() != null) ? df.format(lrsCaseRecord.getCurrDfltCycDt()) : null;
		muleCaseGetDetailDTO.curr_dflt_sts_cd = (lrsCaseRecord.getCurrDfltStsCd() != null) ? lrsCaseRecord.getCurrDfltStsCd().toString().trim() : null;
		muleCaseGetDetailDTO.curr_dflt_episode_nbr = (lrsCaseRecord.getCurrDfltEpisodeNbr() != null) ? lrsCaseRecord.getCurrDfltEpisodeNbr().toString().trim() : null;
		//muleCaseGetDetailDTO.curr_dlnqncy_mnths_dlnqnt = (lrsCaseRecord.getDeli CurrDlnqncyMnthsDlnqnt() != null) ? lrsCaseRecord.getCurrDlnqncyMnthsDlnqnt().toString().trim() : null;
		muleCaseGetDetailDTO.curr_dflt_rsn_cd = (lrsCaseRecord.getCurrDfltRsnCd() != null) ? lrsCaseRecord.getCurrDfltRsnCd().toString().trim() : null;
		muleCaseGetDetailDTO.curr_dflt_sts_dt = (lrsCaseRecord.getCurrDfltStsDt() != null) ? df.format(lrsCaseRecord.getCurrDfltStsDt()) : null;
		muleCaseGetDetailDTO.curr_dflt_sts_summary_cd = (lrsCaseRecord.getCurrDfltStsSummaryCd() != null) ? lrsCaseRecord.getCurrDfltStsSummaryCd().toString().trim() : null;
		muleCaseGetDetailDTO.curr_mnthly_mip = (lrsCaseRecord.getCurrMnthlyMip() != null) ? lrsCaseRecord.getCurrMnthlyMip().toString().trim() : null;
		muleCaseGetDetailDTO.updated_ts = (lrsCaseRecord.getUpdatedTs() != null) ? lrsCaseRecord.getUpdatedTs().toString().trim() : null;
		muleCaseGetDetailDTO.dcsn_cd_endrs = (lrsCaseRecord.getDcsnCdEndrs() != null) ? lrsCaseRecord.getDcsnCdEndrs().toString().trim() : null;
		muleCaseGetDetailDTO.dcsn_cd_uw = (lrsCaseRecord.getDcsnCdUw() != null) ? lrsCaseRecord.getDcsnCdUw().toString().trim() : null;
		muleCaseGetDetailDTO.default_episode_exists_ind = (lrsCaseRecord.getDefaultEpisodeExistsInd() != null) ? lrsCaseRecord.getDefaultEpisodeExistsInd().toString().trim() : null;
		muleCaseGetDetailDTO.disaster_ind = (lrsCaseRecord.getDisasterInd() != null) ? lrsCaseRecord.getDisasterInd().toString().trim() : null;
		muleCaseGetDetailDTO.dpndnt_cnt = (lrsCaseRecord.getDpndntCnt() != null) ? lrsCaseRecord.getDpndntCnt().toString().trim() : null;
		muleCaseGetDetailDTO.dir_lending_branch_ind = (lrsCaseRecord.getDirLendingBranchInd() != null) ? lrsCaseRecord.getDirLendingBranchInd().toString().trim() : null;
		muleCaseGetDetailDTO.date_of_prior_sale = (lrsCaseRecord.getDateOfPriorSale() != null) ? lrsCaseRecord.getDateOfPriorSale().toString().trim() : null;
		muleCaseGetDetailDTO.eff_date_aprsl_update = (lrsCaseRecord.getEffDateAprslUpdate() != null) ? lrsCaseRecord.getEffDateAprslUpdate().toString().trim() : null;
		//muleCaseGetDetailDTO.endrs_aprsl_day_diff = (lrsCaseRecord.getEnd getEndrsAprslDayDiff() != null) ? lrsCaseRecord.getEndrsAprslDayDiff().toString().trim() : null;
		muleCaseGetDetailDTO.endrsmnt_dt = (lrsCaseRecord.getEndrsmntDt() != null) ? df.format(lrsCaseRecord.getEndrsmntDt()) : null;
		//muleCaseGetDetailDTO.endrs_prcsng_dt = (lrsCaseRecord.getEndr EndrsPrcsngDt() != null) ? df.format(lrsCaseRecord.getEndrsPrcsngDt()) : null;
		muleCaseGetDetailDTO.endrsmnt_rvw_prsnnl_id = (lrsCaseRecord.getEndrsmntRvwPrsnnlId() != null) ? lrsCaseRecord.getEndrsmntRvwPrsnnlId().toString().trim() : null;
		muleCaseGetDetailDTO.endrs_bynd_60_days_close_ind = (lrsCaseRecord.getEndrsBynd60DaysCloseInd() != null) ? lrsCaseRecord.getEndrsBynd60DaysCloseInd().toString().trim() : null;
		muleCaseGetDetailDTO.energy_eff_mrtg = (lrsCaseRecord.getEnergyEffMrtg() != null) ? lrsCaseRecord.getEnergyEffMrtg().toString().trim() : null;
		muleCaseGetDetailDTO.early_claim_ind = (lrsCaseRecord.getEarlyClaimInd() != null) ? lrsCaseRecord.getEarlyClaimInd().toString().trim() : null;
		muleCaseGetDetailDTO.early_default_ind = (lrsCaseRecord.getEarlyDefaultInd() != null) ? lrsCaseRecord.getEarlyDefaultInd().toString().trim() : null;
		muleCaseGetDetailDTO.escrow_amount = (lrsCaseRecord.getEscrowAmount() != null) ? lrsCaseRecord.getEscrowAmount().toString().trim() : null;
		//muleCaseGetDetailDTO.escrw_flag = (lrsCaseRecord.getEsc EscrwFlag() != null) ? lrsCaseRecord.getEscrwFlag().toString().trim() : null;
		muleCaseGetDetailDTO.expected_rate = (lrsCaseRecord.getExpectedRate() != null) ? lrsCaseRecord.getExpectedRate().toString().trim() : null;
		muleCaseGetDetailDTO.fctry_fbrct = (lrsCaseRecord.getFctryFbrct() != null) ? lrsCaseRecord.getFctryFbrct().toString().trim() : null;
		muleCaseGetDetailDTO.fema_flood_area_ind = (lrsCaseRecord.getFemaFloodAreaInd() != null) ? lrsCaseRecord.getFemaFloodAreaInd().toString().trim() : null;
		muleCaseGetDetailDTO.fhac_addr_chg = (lrsCaseRecord.getFhacAddrChg() != null) ? lrsCaseRecord.getFhacAddrChg().toString().trim() : null;
		muleCaseGetDetailDTO.fico_decision_score_endrs = (lrsCaseRecord.getFicoDecisionScoreEndrs() != null) ? lrsCaseRecord.getFicoDecisionScoreEndrs().toString().trim() : null;
		muleCaseGetDetailDTO.fico_decision_score_uw = (lrsCaseRecord.getFicoDecisionScoreUw() != null) ? lrsCaseRecord.getFicoDecisionScoreUw().toString().trim() : null;
		muleCaseGetDetailDTO.fncng_typ = (lrsCaseRecord.getFncngTyp() != null) ? lrsCaseRecord.getFncngTyp().toString().trim() : null;
		muleCaseGetDetailDTO.flipping_category_2_ind = (lrsCaseRecord.getFlippingCategory2Ind() != null) ? lrsCaseRecord.getFlippingCategory2Ind().toString().trim() : null;
		muleCaseGetDetailDTO.frclsr_ind = (lrsCaseRecord.getFrclsrInd() != null) ? lrsCaseRecord.getFrclsrInd().toString().trim() : null;
		muleCaseGetDetailDTO.frclsr_strt_dt = (lrsCaseRecord.getFrclsrStrtDt() != null) ? df.format(lrsCaseRecord.getFrclsrStrtDt()) : null;
		muleCaseGetDetailDTO.ratio_tot_pmt_to_tot_inc_endrs = (lrsCaseRecord.getRatioTotPmtToTotIncEndrs() != null) ? lrsCaseRecord.getRatioTotPmtToTotIncEndrs().toString().trim() : null;
		muleCaseGetDetailDTO.ratio_tot_pmt_to_tot_inc_uw = (lrsCaseRecord.getRatioTotPmtToTotIncUw() != null) ? lrsCaseRecord.getRatioTotPmtToTotIncUw().toString().trim() : null;
		muleCaseGetDetailDTO.ft_in_eps_3mnth_delq_dt = (lrsCaseRecord.getFtInEps3mnthDelqDt() != null) ? df.format(lrsCaseRecord.getFtInEps3mnthDelqDt()) : null;
		//muleCaseGetDetailDTO.geocd_flag = (lrsCaseRecord.get GeocdFlag() != null) ? lrsCaseRecord.getGeocdFlag().toString().trim() : null;
		muleCaseGetDetailDTO.gift_ltr_amt = (lrsCaseRecord.getGiftLtrAmt() != null) ? lrsCaseRecord.getGiftLtrAmt().toString().trim() : null;
		muleCaseGetDetailDTO.gift_ltr_src = (lrsCaseRecord.getGiftLtrSrc() != null) ? lrsCaseRecord.getGiftLtrSrc().toString().trim() : null;
		muleCaseGetDetailDTO.gift_ltr_tin = (lrsCaseRecord.getGiftLtrTin() != null) ? lrsCaseRecord.getGiftLtrTin().toString().trim() : null;
		muleCaseGetDetailDTO.gift_ltr_2_amt = (lrsCaseRecord.getGiftLtr2Amt() != null) ? lrsCaseRecord.getGiftLtr2Amt().toString().trim() : null;
		muleCaseGetDetailDTO.gift_ltr_2_source = (lrsCaseRecord.getGiftLtr2Source() != null) ? lrsCaseRecord.getGiftLtr2Source().toString().trim() : null;
		//muleCaseGetDetailDTO.gift_ltr_2_tin = (lrsCaseRecord.getGift GiftLtr2Tin() != null) ? lrsCaseRecord.getGiftLtr2Tin().toString().trim() : null;
		muleCaseGetDetailDTO.hecm_assets = (lrsCaseRecord.getHecmAssets() != null) ? lrsCaseRecord.getHecmAssets().toString().trim() : null;
		muleCaseGetDetailDTO.hecm_counsel_cert_no = (lrsCaseRecord.getHecmCounselCertNo() != null) ? lrsCaseRecord.getHecmCounselCertNo().toString().trim() : null;
		muleCaseGetDetailDTO.hecm_counsel_dt = (lrsCaseRecord.getHecmCounselDt() != null) ? df.format(lrsCaseRecord.getHecmCounselDt()) : null;
		muleCaseGetDetailDTO.hecm_liens = (lrsCaseRecord.getHecmLiens() != null) ? lrsCaseRecord.getHecmLiens().toString().trim() : null;
		muleCaseGetDetailDTO.hecm_prncpl_lmt = (lrsCaseRecord.getHecmPrncplLmt() != null) ? lrsCaseRecord.getHecmPrncplLmt().toString().trim() : null;
		muleCaseGetDetailDTO.hldr_mtgee5_a43 = (lrsCaseRecord.getHldrMtgee5A43() != null) ? lrsCaseRecord.getHldrMtgee5A43().toString().trim() : null;
		muleCaseGetDetailDTO.hud_reo_repair_amt = (lrsCaseRecord.getHudReoRepairAmt() != null) ? lrsCaseRecord.getHudReoRepairAmt().toString().trim() : null;
		muleCaseGetDetailDTO.hsng_pgm_cd = (lrsCaseRecord.getHsngPgmCd() != null) ? lrsCaseRecord.getHsngPgmCd().toString().trim() : null;
		//muleCaseGetDetailDTO.indem_dt = (lrsCaseRecord.getIndem IndemDt() != null) ? df.format(lrsCaseRecord.getIndemDt()) : null;
		//muleCaseGetDetailDTO.indem_stat = (lrsCaseRecord.getIndemStat() != null) ? lrsCaseRecord.getIndemStat().toString().trim() : null;
		//muleCaseGetDetailDTO.indem_term = (lrsCaseRecord.getIndemTerm() != null) ? lrsCaseRecord.getIndemTerm().toString().trim() : null;
		//muleCaseGetDetailDTO.indem_type = (lrsCaseRecord.getIndemType() != null) ? lrsCaseRecord.getIndemType().toString().trim() : null;
		muleCaseGetDetailDTO.insur_app_in_time_ind = (lrsCaseRecord.getInsurAppInTimeInd() != null) ? lrsCaseRecord.getInsurAppInTimeInd().toString().trim() : null;
		muleCaseGetDetailDTO.insrnc_status_cd = (lrsCaseRecord.getInsrncStatusCd() != null) ? lrsCaseRecord.getInsrncStatusCd().toString().trim() : null;
		muleCaseGetDetailDTO.init_disbursement_limit = (lrsCaseRecord.getInitDisbursementLimit() != null) ? lrsCaseRecord.getInitDisbursementLimit().toString().trim() : null;
		muleCaseGetDetailDTO.init_fee = (lrsCaseRecord.getInitFee() != null) ? lrsCaseRecord.getInitFee().toString().trim() : null;
		muleCaseGetDetailDTO.int_rt = (lrsCaseRecord.getIntRt() != null) ? lrsCaseRecord.getIntRt().toString().trim() : null;
		muleCaseGetDetailDTO.invest_2nd_resid_ind = (lrsCaseRecord.getInvest2ndResidInd() != null) ? lrsCaseRecord.getInvest2ndResidInd().toString().trim() : null;
		//muleCaseGetDetailDTO.last_case_qlfctn_evnt_dt = (lrsCaseRecord.getLastCaseQlfctnEvntDt() != null) ? df.format(lrsCaseRecord.getLastCaseQlfctnEvntDt()) : null;
		muleCaseGetDetailDTO.last_servicing_mrtg_xfer_dt = (lrsCaseRecord.getLastServicingMrtgXferDt() != null) ? df.format(lrsCaseRecord.getLastServicingMrtgXferDt()) : null;
		muleCaseGetDetailDTO.le_compound_rate = (lrsCaseRecord.getLeCompoundRate() != null) ? lrsCaseRecord.getLeCompoundRate().toString().trim() : null;
		muleCaseGetDetailDTO.le_expected_rate = (lrsCaseRecord.getLeExpectedRate() != null) ? lrsCaseRecord.getLeExpectedRate().toString().trim() : null;
		muleCaseGetDetailDTO.le_projected_amt = (lrsCaseRecord.getLeProjectedAmt() != null) ? lrsCaseRecord.getLeProjectedAmt().toString().trim() : null;
		muleCaseGetDetailDTO.le_setaside_amt = (lrsCaseRecord.getLeSetasideAmt() != null) ? lrsCaseRecord.getLeSetasideAmt().toString().trim() : null;
		muleCaseGetDetailDTO.lesa_funding_amt = (lrsCaseRecord.getLesaFundingAmt() != null) ? lrsCaseRecord.getLesaFundingAmt().toString().trim() : null;
		muleCaseGetDetailDTO.lesa_funding_type = (lrsCaseRecord.getLesaFundingType() != null) ? lrsCaseRecord.getLesaFundingType().toString().trim() : null;
		muleCaseGetDetailDTO.le_talc_months = (lrsCaseRecord.getLeTalcMonths() != null) ? lrsCaseRecord.getLeTalcMonths().toString().trim() : null;
		muleCaseGetDetailDTO.misc_lndr_insrnc_ind = (lrsCaseRecord.getMiscLndrInsrncInd() != null) ? lrsCaseRecord.getMiscLndrInsrncInd().toString().trim() : null;
		muleCaseGetDetailDTO.loan_nbr = (lrsCaseRecord.getLoanNbr() != null) ? lrsCaseRecord.getLoanNbr().toString().trim() : null;
		muleCaseGetDetailDTO.loan_officer = (lrsCaseRecord.getLoanOfficer() != null) ? lrsCaseRecord.getLoanOfficer().toString().trim() : null;
		muleCaseGetDetailDTO.loan_officer_nmls = (lrsCaseRecord.getLoanOfficerNmls() != null) ? lrsCaseRecord.getLoanOfficerNmls().toString().trim() : null;
		muleCaseGetDetailDTO.loan_prps = (lrsCaseRecord.getLoanPrps() != null) ? lrsCaseRecord.getLoanPrps().toString().trim() : null;
		muleCaseGetDetailDTO.loan_prps_frwd_pymt_ind = (lrsCaseRecord.getLoanPrpsFrwdPymtInd() != null) ? lrsCaseRecord.getLoanPrpsFrwdPymtInd().toString().trim() : null;
		muleCaseGetDetailDTO.loan_prps_imprvmnt_ind = (lrsCaseRecord.getLoanPrpsImprvmntInd() != null) ? lrsCaseRecord.getLoanPrpsImprvmntInd().toString().trim() : null;
		muleCaseGetDetailDTO.loan_prps_incm_ind = (lrsCaseRecord.getLoanPrpsIncmInd() != null) ? lrsCaseRecord.getLoanPrpsIncmInd().toString().trim() : null;
		muleCaseGetDetailDTO.loan_prps_insrnc_ind = (lrsCaseRecord.getLoanPrpsInsrncInd() != null) ? lrsCaseRecord.getLoanPrpsInsrncInd().toString().trim() : null;
		muleCaseGetDetailDTO.loan_prps_leisure_ind = (lrsCaseRecord.getLoanPrpsLeisureInd() != null) ? lrsCaseRecord.getLoanPrpsLeisureInd().toString().trim() : null;
		muleCaseGetDetailDTO.loan_prps_medcl_ind = (lrsCaseRecord.getLoanPrpsMedclInd() != null) ? lrsCaseRecord.getLoanPrpsMedclInd().toString().trim() : null;
		muleCaseGetDetailDTO.loan_prps_othr_ind = (lrsCaseRecord.getLoanPrpsOthrInd() != null) ? lrsCaseRecord.getLoanPrpsOthrInd().toString().trim() : null;
		muleCaseGetDetailDTO.prop_typ = (lrsCaseRecord.getPropTyp() != null) ? lrsCaseRecord.getPropTyp().toString().trim() : null;
		muleCaseGetDetailDTO.loan_prps_taxes_ind = (lrsCaseRecord.getLoanPrpsTaxesInd() != null) ? lrsCaseRecord.getLoanPrpsTaxesInd().toString().trim() : null;
		muleCaseGetDetailDTO.loan_prps_text = (lrsCaseRecord.getLoanPrpsText() != null) ? lrsCaseRecord.getLoanPrpsText().toString().trim() : null;
		muleCaseGetDetailDTO.ltv_cat = (lrsCaseRecord.getLtvCat() != null) ? lrsCaseRecord.getLtvCat().toString().trim() : null;
		muleCaseGetDetailDTO.ratio_loan_to_vl_new = (lrsCaseRecord.getRatioLoanToVlNew() != null) ? lrsCaseRecord.getRatioLoanToVlNew().toString().trim() : null;
		muleCaseGetDetailDTO.loan_type = (lrsCaseRecord.getLoanType() != null) ? lrsCaseRecord.getLoanType().toString().trim() : null;
		muleCaseGetDetailDTO.lossmit_cd = (lrsCaseRecord.getLossmitCd() != null) ? lrsCaseRecord.getLossmitCd().toString().trim() : null;
		muleCaseGetDetailDTO.married_to_nbs_ind = (lrsCaseRecord.getMarriedToNbsInd() != null) ? lrsCaseRecord.getMarriedToNbsInd().toString().trim() : null;
		muleCaseGetDetailDTO.mip_financed_ind = (lrsCaseRecord.getMipFinancedInd() != null) ? lrsCaseRecord.getMipFinancedInd().toString().trim() : null;
		muleCaseGetDetailDTO.misc_aus_dcsn_cd = (lrsCaseRecord.getMiscAusDcsnCd() != null) ? lrsCaseRecord.getMiscAusDcsnCd().toString().trim() : null;
		muleCaseGetDetailDTO.misc_aus_ind = (lrsCaseRecord.getMiscAusInd() != null) ? lrsCaseRecord.getMiscAusInd().toString().trim() : null;
		//muleCaseGetDetailDTO.misc_ebndr_ind = (lrsCaseRecord.getMiscEbndrInd() != null) ? lrsCaseRecord.getMiscEbndrInd().toString().trim() : null;
		muleCaseGetDetailDTO.mndtry_oblgtns_amt = (lrsCaseRecord.getMndtryOblgtnsAmt() != null) ? lrsCaseRecord.getMndtryOblgtnsAmt().toString().trim() : null;
		muleCaseGetDetailDTO.mandatory_oblig_borr_amt = (lrsCaseRecord.getMandatoryObligBorrAmt() != null) ? lrsCaseRecord.getMandatoryObligBorrAmt().toString().trim() : null;
		muleCaseGetDetailDTO.mandatory_oblig_lend_amt = (lrsCaseRecord.getMandatoryObligLendAmt() != null) ? lrsCaseRecord.getMandatoryObligLendAmt().toString().trim() : null;
		//muleCaseGetDetailDTO.mnfctrd_husng_ind = (lrsCaseRecord.getMnfctrdHusngInd() != null) ? lrsCaseRecord.getMnfctrdHusngInd().toString().trim() : null;
		muleCaseGetDetailDTO.man_uw_stretch_ratio_ind = (lrsCaseRecord.getManUwStretchRatioInd() != null) ? lrsCaseRecord.getManUwStretchRatioInd().toString().trim() : null;
		muleCaseGetDetailDTO.me_debt_amt = (lrsCaseRecord.getMeDebtAmt() != null) ? lrsCaseRecord.getMeDebtAmt().toString().trim() : null;
		muleCaseGetDetailDTO.me_other_amt = (lrsCaseRecord.getMeOtherAmt() != null) ? lrsCaseRecord.getMeOtherAmt().toString().trim() : null;
		muleCaseGetDetailDTO.me_re_amt = (lrsCaseRecord.getMeReAmt() != null) ? lrsCaseRecord.getMeReAmt().toString().trim() : null;
		muleCaseGetDetailDTO.me_total_amt = (lrsCaseRecord.getMeTotalAmt() != null) ? lrsCaseRecord.getMeTotalAmt().toString().trim() : null;
		muleCaseGetDetailDTO.mi_imputed_amt = (lrsCaseRecord.getMiImputedAmt() != null) ? lrsCaseRecord.getMiImputedAmt().toString().trim() : null;
		muleCaseGetDetailDTO.mi_other_amt = (lrsCaseRecord.getMiOtherAmt() != null) ? lrsCaseRecord.getMiOtherAmt().toString().trim() : null;
		muleCaseGetDetailDTO.mi_total_amt = (lrsCaseRecord.getMiTotalAmt() != null) ? lrsCaseRecord.getMiTotalAmt().toString().trim() : null;
		muleCaseGetDetailDTO.mnthly_prncpl_and_intrst = (lrsCaseRecord.getMonthlyPI() != null) ? lrsCaseRecord.getMonthlyPI().toString().trim() : null;
		muleCaseGetDetailDTO.mnthly_set_aside = (lrsCaseRecord.getMnthlySetAside() != null) ? lrsCaseRecord.getMnthlySetAside().toString().trim() : null;
		muleCaseGetDetailDTO.mort_excld_fncd_mip = (lrsCaseRecord.getMortExcldFncdMip() != null) ? lrsCaseRecord.getMortExcldFncdMip().toString().trim() : null;
		muleCaseGetDetailDTO.prequal_dt = (lrsCaseRecord.getPrequalDt() != null) ? df.format(lrsCaseRecord.getPrequalDt()) : null;
		muleCaseGetDetailDTO.max_claim_amt = (lrsCaseRecord.getMaxClaimAmt() != null) ? lrsCaseRecord.getMaxClaimAmt().toString().trim() : null;
		muleCaseGetDetailDTO.max_rate = (lrsCaseRecord.getMaxRate() != null) ? lrsCaseRecord.getMaxRate().toString().trim() : null;
		muleCaseGetDetailDTO.nbr_bdrm = (lrsCaseRecord.getNbrBdrm() != null) ? lrsCaseRecord.getNbrBdrm().toString().trim() : null;
		muleCaseGetDetailDTO.nbr_bthrms = (lrsCaseRecord.getNbrBthrms() != null) ? lrsCaseRecord.getNbrBthrms().toString().trim() : null;
		muleCaseGetDetailDTO.num_living_units = (lrsCaseRecord.getNumLivingUnits() != null) ? lrsCaseRecord.getNumLivingUnits().toString().trim() : null;
		muleCaseGetDetailDTO.nbr_rms = (lrsCaseRecord.getNbrRms() != null) ? lrsCaseRecord.getNbrRms().toString().trim() : null;
		muleCaseGetDetailDTO.nbrhd_pct_owned = (lrsCaseRecord.getNbrhdPctOwned() != null) ? lrsCaseRecord.getNbrhdPctOwned().toString().trim() : null;
		muleCaseGetDetailDTO.nbrhd_price = (lrsCaseRecord.getNbrhdPrice() != null) ? lrsCaseRecord.getNbrhdPrice().toString().trim() : null;
		muleCaseGetDetailDTO.non_occupying_borr_ind = (lrsCaseRecord.getNonOccupyingBorrInd() != null) ? lrsCaseRecord.getNonOccupyingBorrInd().toString().trim() : null;
		muleCaseGetDetailDTO.nbs_first_middle_last = (lrsCaseRecord.getNbsFirstMiddleLast() != null) ? lrsCaseRecord.getNbsFirstMiddleLast().toString().trim() : null;
		//muleCaseGetDetailDTO.nonperf_loan_ind = (lrsCaseRecord.getNonperfLoanInd() != null) ? lrsCaseRecord.getNonperfLoanInd().toString().trim() : null;
		//muleCaseGetDetailDTO.nor_issue_dt = (lrsCaseRecord.getNorIssueDt() != null) ? df.format(lrsCaseRecord.getNorIssueDt()) : null;
		muleCaseGetDetailDTO.nor_iss_rpt_dt = (lrsCaseRecord.getNorIssRptDt() != null) ? df.format(lrsCaseRecord.getNorIssRptDt()) : null;
		muleCaseGetDetailDTO.ocpncy_sts_cd = (lrsCaseRecord.getOcpncyStsCd() != null) ? lrsCaseRecord.getOcpncyStsCd().toString().trim() : null;
		muleCaseGetDetailDTO.ocpncy_sts_dt = (lrsCaseRecord.getOcpncyStsDt() != null) ? df.format(lrsCaseRecord.getOcpncyStsDt()) : null;
		muleCaseGetDetailDTO.oldst_unpd_dt = (lrsCaseRecord.getOldstUnpdDt() != null) ? df.format(lrsCaseRecord.getOldstUnpdDt()) : null;
		muleCaseGetDetailDTO.orig_mrtg_amt = (lrsCaseRecord.getOrigMrtgAmt() != null) ? lrsCaseRecord.getOrigMrtgAmt().toString().trim() : null;
		muleCaseGetDetailDTO.origination_fee = (lrsCaseRecord.getOriginationFee() != null) ? lrsCaseRecord.getOriginationFee().toString().trim() : null;
		muleCaseGetDetailDTO.orgntng_mtgee10_id = (lrsCaseRecord.getOrgntngMtgee10Id() != null) ? lrsCaseRecord.getOrgntngMtgee10Id().toString().trim() : null;
		muleCaseGetDetailDTO.orgntng_mtgee5 = (lrsCaseRecord.getOrgntngMtgee5() != null) ? lrsCaseRecord.getOrgntngMtgee5().toString().trim() : null;
		//muleCaseGetDetailDTO.orgntng_mrtggee_inst_stat = (lrsCaseRecord.getOrgntngMrtggeeInstStat() != null) ? lrsCaseRecord.getOrgntngMrtggeeInstStat().toString().trim() : null;
		muleCaseGetDetailDTO.orgntng_mtgee_nmls_id = (lrsCaseRecord.getOrgntngMtgeeNmlsId() != null) ? lrsCaseRecord.getOrgntngMtgeeNmlsId().toString().trim() : null;
		muleCaseGetDetailDTO.typ_orgntng_mtgee = (lrsCaseRecord.getTypOrgntngMtgee() != null) ? lrsCaseRecord.getTypOrgntngMtgee().toString().trim() : null;
		muleCaseGetDetailDTO.other_debt_curr_ind = (lrsCaseRecord.getOtherDebtCurrInd() != null) ? lrsCaseRecord.getOtherDebtCurrInd().toString().trim() : null;
		muleCaseGetDetailDTO.other_debt_late_pmt_ind = (lrsCaseRecord.getOtherDebtLatePmtInd() != null) ? lrsCaseRecord.getOtherDebtLatePmtInd().toString().trim() : null;
		muleCaseGetDetailDTO.payment_plan = (lrsCaseRecord.getPaymentPlan() != null) ? lrsCaseRecord.getPaymentPlan().toString().trim() : null;
		//muleCaseGetDetailDTO.pmts_bfr_frst_missd_pmt = (lrsCaseRecord.getPmtsBfrFrstMissdPmt() != null) ? lrsCaseRecord.getPmtsBfrFrstMissdPmt().toString().trim() : null;
		muleCaseGetDetailDTO.pct_1_fmly = (lrsCaseRecord.getPct1Fmly() != null) ? lrsCaseRecord.getPct1Fmly().toString().trim() : null;
		muleCaseGetDetailDTO.prcsng_typ = (lrsCaseRecord.getPrcsngTyp() != null) ? lrsCaseRecord.getPrcsngTyp().toString().trim() : null;
		muleCaseGetDetailDTO.pre_rvw_dcsn = (lrsCaseRecord.getPreRvwDcsn() != null) ? lrsCaseRecord.getPreRvwDcsn().toString().trim() : null;
		muleCaseGetDetailDTO.pre_clsng_ind = (lrsCaseRecord.getPreClsngInd() != null) ? lrsCaseRecord.getPreClsngInd().toString().trim() : null;
		//muleCaseGetDetailDTO.prequal_cnt = (lrsCaseRecord.getPrequalCnt() != null) ? lrsCaseRecord.getPrequalCnt().toString().trim() : null;
		muleCaseGetDetailDTO.old_srvcr_mtgee = (lrsCaseRecord.getOldSrvcrMtgee() != null) ? lrsCaseRecord.getOldSrvcrMtgee().toString().trim() : null;
		muleCaseGetDetailDTO.prog_id_f17 = (lrsCaseRecord.getProgIdF17() != null) ? lrsCaseRecord.getProgIdF17().toString().trim() : null;
		//muleCaseGetDetailDTO.price_excldng_clsng_costs = (lrsCaseRecord.getPriceExcldngClsngCosts() != null) ? lrsCaseRecord.getPriceExcldngClsngCosts().toString().trim() : null;
		//muleCaseGetDetailDTO.price_incldng_clsng_costs = (lrsCaseRecord.getPriceIncldngClsngCosts() != null) ? lrsCaseRecord.getPriceIncldngClsngCosts().toString().trim() : null;
		muleCaseGetDetailDTO.price_of_prior_sale = (lrsCaseRecord.getPriceOfPriorSale() != null) ? lrsCaseRecord.getPriceOfPriorSale().toString().trim() : null;
		muleCaseGetDetailDTO.prncpl_rdctn_amt = (lrsCaseRecord.getPrncplRdctnAmt() != null) ? lrsCaseRecord.getPrncplRdctnAmt().toString().trim() : null;
		muleCaseGetDetailDTO.dt_acq = (lrsCaseRecord.getDtAcq() != null) ? lrsCaseRecord.getDtAcq().toString().trim() : null;
		//muleCaseGetDetailDTO.prop_addr_city = (lrsCaseRecord.getPropAddrCity() != null) ? lrsCaseRecord.getPropAddrCity().toString().trim() : null;
		//muleCaseGetDetailDTO.prop_addr_1 = (lrsCaseRecord.getPropAddr1() != null) ? lrsCaseRecord.getPropAddr1().toString().trim() : null;
		//muleCaseGetDetailDTO.prop_addr_2 = (lrsCaseRecord.getPropAddr2() != null) ? lrsCaseRecord.getPropAddr2().toString().trim() : null;
		muleCaseGetDetailDTO.prop_addr_st = (lrsCaseRecord.getPropAddrSt() != null) ? lrsCaseRecord.getPropAddrSt().toString().trim() : null;
		//muleCaseGetDetailDTO.prop_addr_zip = (lrsCaseRecord.getPropAddrZip() != null) ? lrsCaseRecord.getPropAddrZip().toString().trim() : null;
		muleCaseGetDetailDTO.prprty_aprsl_vl = (lrsCaseRecord.getPrprtyAprslVl() != null) ? lrsCaseRecord.getPrprtyAprslVl().toString().trim() : null;
		muleCaseGetDetailDTO.pc_condo_fee_amt = (lrsCaseRecord.getPcCondoFeeAmt() != null) ? lrsCaseRecord.getPcCondoFeeAmt().toString().trim() : null;
		muleCaseGetDetailDTO.pc_flood_ins_amt = (lrsCaseRecord.getPcFloodInsAmt() != null) ? lrsCaseRecord.getPcFloodInsAmt().toString().trim() : null;
		muleCaseGetDetailDTO.pc_haz_ins_amt = (lrsCaseRecord.getPcHazInsAmt() != null) ? lrsCaseRecord.getPcHazInsAmt().toString().trim() : null;
		muleCaseGetDetailDTO.pc_other_amt = (lrsCaseRecord.getPcOtherAmt() != null) ? lrsCaseRecord.getPcOtherAmt().toString().trim() : null;
		muleCaseGetDetailDTO.pc_re_tax_amt = (lrsCaseRecord.getPcReTaxAmt() != null) ? lrsCaseRecord.getPcReTaxAmt().toString().trim() : null;
		muleCaseGetDetailDTO.pc_setaside_tot_amt = (lrsCaseRecord.getPcSetasideTotAmt() != null) ? lrsCaseRecord.getPcSetasideTotAmt().toString().trim() : null;
		muleCaseGetDetailDTO.pc_total_amt = (lrsCaseRecord.getPcTotalAmt() != null) ? lrsCaseRecord.getPcTotalAmt().toString().trim() : null;
		muleCaseGetDetailDTO.prprty_cnvrsn_typ = (lrsCaseRecord.getPrprtyCnvrsnTyp() != null) ? lrsCaseRecord.getPrprtyCnvrsnTyp().toString().trim() : null;
		muleCaseGetDetailDTO.pd_strmln_flg = (lrsCaseRecord.getPdStrmlnFlg() != null) ? lrsCaseRecord.getPdStrmlnFlg().toString().trim() : null;
		//muleCaseGetDetailDTO.prr_case_mxmm_clm_amt = (lrsCaseRecord.getPrrCaseMxmmClmAmt() != null) ? lrsCaseRecord.getPrrCaseMxmmClmAmt().toString().trim() : null;
		muleCaseGetDetailDTO.prior_sale_rqrd_ind = (lrsCaseRecord.getPriorSaleRqrdInd() != null) ? lrsCaseRecord.getPriorSaleRqrdInd().toString().trim() : null;
		muleCaseGetDetailDTO.prior_sale_within_last_3yr_ind = (lrsCaseRecord.getPriorSaleWithinLast3yrInd() != null) ? lrsCaseRecord.getPriorSaleWithinLast3yrInd().toString().trim() : null;
		muleCaseGetDetailDTO.prev_complt_sbdvsn_ind = (lrsCaseRecord.getPrevCompltSbdvsnInd() != null) ? lrsCaseRecord.getPrevCompltSbdvsnInd().toString().trim() : null;
		muleCaseGetDetailDTO.qualified_mrtg_points_and_fees = (lrsCaseRecord.getQualifiedMrtgPointsAndFees() != null) ? lrsCaseRecord.getQualifiedMrtgPointsAndFees().toString().trim() : null;
		muleCaseGetDetailDTO.pti_cat = (lrsCaseRecord.getPtiCat() != null) ? lrsCaseRecord.getPtiCat().toString().trim() : null;
		muleCaseGetDetailDTO.ratio_ore_tei = (lrsCaseRecord.getRatioOreTei() != null) ? lrsCaseRecord.getRatioOreTei().toString().trim() : null;
		muleCaseGetDetailDTO.re_debt_curr_ind = (lrsCaseRecord.getReDebtCurrInd() != null) ? lrsCaseRecord.getReDebtCurrInd().toString().trim() : null;
		muleCaseGetDetailDTO.re_debt_late_pmt_ind = (lrsCaseRecord.getReDebtLatePmtInd() != null) ? lrsCaseRecord.getReDebtLatePmtInd().toString().trim() : null;
		muleCaseGetDetailDTO.re_tax_curr_ind = (lrsCaseRecord.getReTaxCurrInd() != null) ? lrsCaseRecord.getReTaxCurrInd().toString().trim() : null;
		muleCaseGetDetailDTO.re_tax_delinq_ind = (lrsCaseRecord.getReTaxDelinqInd() != null) ? lrsCaseRecord.getReTaxDelinqInd().toString().trim() : null;
		muleCaseGetDetailDTO.rfnc_cd = (lrsCaseRecord.getRfncCd() != null) ? lrsCaseRecord.getRfncCd().toString().trim() : null;
		muleCaseGetDetailDTO.refinance_ind = (lrsCaseRecord.getRfncInd() != null) ? lrsCaseRecord.getRfncInd().toString().trim() : null;
		muleCaseGetDetailDTO.rfnc_next_case_nbr = (lrsCaseRecord.getRfncNextCaseNbr() != null) ? lrsCaseRecord.getRfncNextCaseNbr().toString().trim() : null;
		//muleCaseGetDetailDTO.refi_prr_case_nbr = (lrsCaseRecord.getRefiPrrCaseNbr() != null) ? lrsCaseRecord.getRefiPrrCaseNbr().toString().trim() : null;
		muleCaseGetDetailDTO.reo_100_down_pmt_prog_ind = (lrsCaseRecord.getReo100DownPmtProgInd() != null) ? lrsCaseRecord.getReo100DownPmtProgInd().toString().trim() : null;
		muleCaseGetDetailDTO.ri_family_size = (lrsCaseRecord.getRiFamilySize() != null) ? lrsCaseRecord.getRiFamilySize().toString().trim() : null;
		muleCaseGetDetailDTO.ri_shortfall_amt = (lrsCaseRecord.getRiShortfallAmt() != null) ? lrsCaseRecord.getRiShortfallAmt().toString().trim() : null;
		muleCaseGetDetailDTO.ri_standard_amt = (lrsCaseRecord.getRiStandardAmt() != null) ? lrsCaseRecord.getRiStandardAmt().toString().trim() : null;
		muleCaseGetDetailDTO.ri_total_amt = (lrsCaseRecord.getRiTotalAmt() != null) ? lrsCaseRecord.getRiTotalAmt().toString().trim() : null;
		muleCaseGetDetailDTO.repair_completion_date = (lrsCaseRecord.getRepairCompletionDate() != null) ? lrsCaseRecord.getRepairCompletionDate().toString().trim() : null;
		muleCaseGetDetailDTO.rpr_set_aside = (lrsCaseRecord.getRprSetAside() != null) ? lrsCaseRecord.getRprSetAside().toString().trim() : null;
		muleCaseGetDetailDTO.nbrhd_cd = (lrsCaseRecord.getNbrhdCd() != null) ? lrsCaseRecord.getNbrhdCd().toString().trim() : null;
		muleCaseGetDetailDTO.revolve_debt_curr_ind = (lrsCaseRecord.getRevolveDebtCurrInd() != null) ? lrsCaseRecord.getRevolveDebtCurrInd().toString().trim() : null;
		muleCaseGetDetailDTO.revolve_debt_late_pmt_ind = (lrsCaseRecord.getRevolveDebtLatePmtInd() != null) ? lrsCaseRecord.getRevolveDebtLatePmtInd().toString().trim() : null;
		muleCaseGetDetailDTO.rcv_sale_dt = (lrsCaseRecord.getRcvSaleDt() != null) ? df.format(lrsCaseRecord.getRcvSaleDt()) : null;
		muleCaseGetDetailDTO.sales_price = (lrsCaseRecord.getSalesPrice() != null) ? lrsCaseRecord.getSalesPrice().toString().trim() : null;
		muleCaseGetDetailDTO.sale_price_gtr_acq_cost_ind = (lrsCaseRecord.getSalePriceGtrAcqCostInd() != null) ? lrsCaseRecord.getSalePriceGtrAcqCostInd().toString().trim() : null;
		muleCaseGetDetailDTO.secondary_finance_amt = (lrsCaseRecord.getSecondaryFinanceAmt() != null) ? lrsCaseRecord.getSecondaryFinanceAmt().toString().trim() : null;
		muleCaseGetDetailDTO.secondary_finance_exists_ind = (lrsCaseRecord.getSecondaryFinanceExistsInd() != null) ? lrsCaseRecord.getSecondaryFinanceExistsInd().toString().trim() : null;
		muleCaseGetDetailDTO.scndry_fnc_src = (lrsCaseRecord.getScndryFncSrc() != null) ? lrsCaseRecord.getScndryFncSrc().toString().trim() : null;
		muleCaseGetDetailDTO.soa_cd = (lrsCaseRecord.getSoaCd() != null) ? lrsCaseRecord.getSoaCd().toString().trim() : null;
		muleCaseGetDetailDTO.send_mic_ind = (lrsCaseRecord.getSendMicInd() != null) ? lrsCaseRecord.getSendMicInd().toString().trim() : null;
		muleCaseGetDetailDTO.site_type = (lrsCaseRecord.getSiteType() != null) ? lrsCaseRecord.getSiteType().toString().trim() : null;
		muleCaseGetDetailDTO.seller_cntrbtn = (lrsCaseRecord.getSellerCntrbtn() != null) ? lrsCaseRecord.getSellerCntrbtn().toString().trim() : null;
		muleCaseGetDetailDTO.seller_cntrbtn_pcnt = (lrsCaseRecord.getSellerCntrbtnPcnt() != null) ? lrsCaseRecord.getSellerCntrbtnPcnt().toString().trim() : null;
		muleCaseGetDetailDTO.sngl_dsbrse_lmp_sum_pmt_opt = (lrsCaseRecord.getSnglDsbrseLmpSumPmtOpt() != null) ? lrsCaseRecord.getSnglDsbrseLmpSumPmtOpt().toString().trim() : null;
		muleCaseGetDetailDTO.special_program = (lrsCaseRecord.getSpecialProgram() != null) ? lrsCaseRecord.getSpecialProgram().toString().trim() : null;
		muleCaseGetDetailDTO.spnsr_mtgee10_id = (lrsCaseRecord.getSpnsrMtgee10Id() != null) ? lrsCaseRecord.getSpnsrMtgee10Id().toString().trim() : null;
		//muleCaseGetDetailDTO.spnsr_mrtggee_id_inst = (lrsCaseRecord.getSpnsrMtgee10Id() != null) ? lrsCaseRecord.getSpnsrMtgee10Id().toString().trim() : null;
		muleCaseGetDetailDTO.srvcr_mtgee5_a43 = (lrsCaseRecord.getSrvcrMtgee5A43() != null) ? lrsCaseRecord.getSrvcrMtgee5A43().toString().trim() : null;
		muleCaseGetDetailDTO.strmnln_refi_type = (lrsCaseRecord.getStrmnlnRefiType() != null) ? lrsCaseRecord.getStrmnlnRefiType().toString().trim() : null;
		muleCaseGetDetailDTO.sbdvsn_spot_lot = (lrsCaseRecord.getSbdvsnSpotLot() != null) ? lrsCaseRecord.getSbdvsnSpotLot().toString().trim() : null;
		muleCaseGetDetailDTO.ten_yr_rate_lock_ind = (lrsCaseRecord.getTenYrRateLockInd() != null) ? lrsCaseRecord.getTenYrRateLockInd().toString().trim() : null;
		muleCaseGetDetailDTO.term = (lrsCaseRecord.getTerm() != null) ? lrsCaseRecord.getTerm().toString().trim() : null;
		//muleCaseGetDetailDTO.term_15_yr_ind = (lrsCaseRecord.getTerm15YrInd() != null) ? lrsCaseRecord.getTerm15YrInd().toString().trim() : null;
		muleCaseGetDetailDTO.tot_acq_costs = (lrsCaseRecord.getTotAcqCosts() != null) ? lrsCaseRecord.getTotAcqCosts().toString().trim() : null;
		muleCaseGetDetailDTO.tot_ann_eff_incm = (lrsCaseRecord.getTotAnnEffIncm() != null) ? lrsCaseRecord.getTotAnnEffIncm().toString().trim() : null;
		muleCaseGetDetailDTO.tot_assets_endrs = (lrsCaseRecord.getTotAssetsEndrs() != null) ? lrsCaseRecord.getTotAssetsEndrs().toString().trim() : null;
		muleCaseGetDetailDTO.tot_assets_uw = (lrsCaseRecord.getTotAssetsUw() != null) ? lrsCaseRecord.getTotAssetsUw().toString().trim() : null;
		muleCaseGetDetailDTO.tot_clsng_csts_endrs = (lrsCaseRecord.getTotClsngCstsEndrs() != null) ? lrsCaseRecord.getTotClsngCstsEndrs().toString().trim() : null;
		muleCaseGetDetailDTO.tot_clsng_csts_uw = (lrsCaseRecord.getTotClsngCstsUw() != null) ? lrsCaseRecord.getTotClsngCstsUw().toString().trim() : null;
		muleCaseGetDetailDTO.tot_fixed_pymt_endrs = (lrsCaseRecord.getTotFixedPymtEndrs() != null) ? lrsCaseRecord.getTotFixedPymtEndrs().toString().trim() : null;
		muleCaseGetDetailDTO.tot_fixed_pymt_uw = (lrsCaseRecord.getTotFixedPymtUw() != null) ? lrsCaseRecord.getTotFixedPymtUw().toString().trim() : null;
		muleCaseGetDetailDTO.tot_mnthly_eff_incm = (lrsCaseRecord.getTotMnthlyEffIncm() != null) ? lrsCaseRecord.getTotMnthlyEffIncm().toString().trim() : null;
		muleCaseGetDetailDTO.tot_mnthly_mtg_pymt_endrs = (lrsCaseRecord.getTotMnthlyMtgPymtEndrs() != null) ? lrsCaseRecord.getTotMnthlyMtgPymtEndrs().toString().trim() : null;
		muleCaseGetDetailDTO.tot_mnthly_mtg_pymt_uw = (lrsCaseRecord.getTotMnthlyMtgPymtUw() != null) ? lrsCaseRecord.getTotMnthlyMtgPymtUw().toString().trim() : null;
		muleCaseGetDetailDTO.total_required_funds_to_close = (lrsCaseRecord.getTotalRequiredFundsToClose() != null) ? lrsCaseRecord.getTotalRequiredFundsToClose().toString().trim() : null;
		muleCaseGetDetailDTO.term_typ_cd = (lrsCaseRecord.getTermTypCd() != null) ? lrsCaseRecord.getTermTypCd().toString().trim() : null;
		muleCaseGetDetailDTO.trnsmsn_typ = (lrsCaseRecord.getTrnsmsnTyp() != null) ? lrsCaseRecord.getTrnsmsnTyp().toString().trim() : null;
		muleCaseGetDetailDTO.taxes_insrnc_frst_yr_amt = (lrsCaseRecord.getTaxesInsrncFrstYrAmt() != null) ? lrsCaseRecord.getTaxesInsrncFrstYrAmt().toString().trim() : null;
		muleCaseGetDetailDTO.ufmip_earned_curr_mm = (lrsCaseRecord.getUfmipEarnedCurrMm() != null) ? lrsCaseRecord.getUfmipEarnedCurrMm().toString().trim() : null;
		muleCaseGetDetailDTO.ufmip_factor = (lrsCaseRecord.getUfmipFactor() != null) ? lrsCaseRecord.getUfmipFactor().toString().trim() : null;
		muleCaseGetDetailDTO.ufmip_pd_amt = (lrsCaseRecord.getUfmipPdAmt() != null) ? lrsCaseRecord.getUfmipPdAmt().toString().trim() : null;
		muleCaseGetDetailDTO.ufmip_pd_cash = (lrsCaseRecord.getUfmipPdCash() != null) ? lrsCaseRecord.getUfmipPdCash().toString().trim() : null;
		//muleCaseGetDetailDTO.undrsrvd_ind = (lrsCaseRecord.getUndrsrvdInd() != null) ? lrsCaseRecord.getUndrsrvdInd().toString().trim() : null;
		muleCaseGetDetailDTO.unpd_bal = (lrsCaseRecord.getUnpdBal() != null) ? lrsCaseRecord.getUnpdBal().toString().trim() : null;
		muleCaseGetDetailDTO.undrwrting_mtgee5 = (lrsCaseRecord.getUndrwrtingMtgee5() != null) ? lrsCaseRecord.getUndrwrtingMtgee5().toString().trim() : null;
		muleCaseGetDetailDTO.underwriting_method = (lrsCaseRecord.getUnderwritingMethod() != null) ? lrsCaseRecord.getUnderwritingMethod().toString().trim() : null;
		muleCaseGetDetailDTO.underwriter_id = (lrsCaseRecord.getUnderwriterId() != null) ? lrsCaseRecord.getUnderwriterId().toString().trim() : null;
		muleCaseGetDetailDTO.val_plus_clsng = (lrsCaseRecord.getValPlusClsng() != null) ? lrsCaseRecord.getValPlusClsng().toString().trim() : null;
		muleCaseGetDetailDTO.year_built = (lrsCaseRecord.getYearBuilt() != null) ? lrsCaseRecord.getYearBuilt().toString().trim() : null;
		//muleCaseGetDetailDTO.yr_mo_const_cmpltd = (lrsCaseRecord.getYrMoConstCmpltd() != null) ? lrsCaseRecord.getYrMoConstCmpltd().toString().trim() : null;
		return muleCaseGetDetailDTO;
	}
	
	public MuleBinderRequestResponseDTO binderRequest(MuleBinderRequestDTO binderRequestDTO) {
		MuleBinderRequestResponseDTO binderRequestResponseDTO = new MuleBinderRequestResponseDTO();
		binderRequestResponseDTO.setStatusCode("0");
		binderRequestResponseDTO.setStatusMessage("");
		EsbExceptionDTO esbException = new EsbExceptionDTO();
		binderRequestResponseDTO.setEsbException(esbException);
		
		return binderRequestResponseDTO;
	}

	public MuleBinderReceiptResponseDTO binderReceipt(MuleBinderReceiptDTO binderReceiptDTO) {
		Set<String> caseNumbers = new HashSet<String>(binderReceiptDTO.getCaseNumbers());

		MuleBinderReceiptResponseDTO binderReceiptResponseDTO = new MuleBinderReceiptResponseDTO();
		binderReceiptResponseDTO.setStatusCode("0");
		binderReceiptResponseDTO.setStatusMessage("");
		List<CaseBinderDTO> caseBinderDTOs = new ArrayList<CaseBinderDTO>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		for (Map.Entry<String, Date> caseNumberReceivedDate : binderReceivedDateByCaseNumber.entrySet()) {
			String caseNumber = caseNumberReceivedDate.getKey();
			if (caseNumbers.contains(caseNumber)) {
				CaseBinderDTO caseBinderDTO = new CaseBinderDTO();
				caseBinderDTO.setCaseNumber(caseNumber);
				caseBinderDTO.setReceiveDate(dateFormat.format(caseNumberReceivedDate.getValue()));
				caseBinderDTO.setEbinderCase(CaseBinderDTO.EBINDER_CASE_ELECTRONIC);
				caseBinderDTO.setHoc("");	// shouldn't apply for e-case
				caseBinderDTOs.add(caseBinderDTO);
			}
		}
		binderReceiptResponseDTO.setCaseBinders(caseBinderDTOs);
		return binderReceiptResponseDTO;
	}
	
	public void receiveBinder(String caseNumber, Date date) {
		binderReceivedDateByCaseNumber.put(caseNumber, date);
	}
	
	private static final String DOCUMENT_DIR = "devMuleDocuments/";

	public byte[] getDocument(String documentKey, String documentName) {
		String fileName = FilenameUtils.getName(documentKey + "-" + documentName);
		Path path = Paths.get(DOCUMENT_DIR + fileName).normalize();
		try {
			return Files.readAllBytes(path);
		} catch (IOException e) {
			logger.error("Error reading document " + path, e);
			return null;
		}
	}
	
	public void saveDocument(String documentKey, String documentName, byte[] bytes) {
		Path documentDirPath = Paths.get(DOCUMENT_DIR);
		String fileName = FilenameUtils.getName(documentKey + "-" + documentName);
		Path path = Paths.get(DOCUMENT_DIR + fileName).normalize();
		try {
			File documentPathFile = documentDirPath.toFile();
			if (!documentPathFile.exists()) {
				documentPathFile.mkdirs();
			}
			Files.write(path, bytes);
		} catch (IOException e) {
			logger.error("Error saving document " + path, e);
			throw new RuntimeException(e);
		}
	}
	
	public MuleIndemnificationOutputDTO indemnification(MuleIndemnificationInputDTO muleIndemnificationInputDTO) {
		MuleIndemnificationOutputDTO muleIndemnificationOutputDTO = new MuleIndemnificationOutputDTO();
		muleIndemnificationOutputDTO.setStatusCode("0");
		muleIndemnificationOutputDTO.setStatusMessage("");
		
		// Simple validation
		if (!Arrays.asList("Originator", "Servicer", "Sponsor", "Holder").contains(muleIndemnificationInputDTO.getBillTo())) {
			muleIndemnificationOutputDTO.setStatusCode("1");
			muleIndemnificationOutputDTO.setStatusMessage("Invalid billTo: " + muleIndemnificationInputDTO.getBillTo() + ". Allowed values: ['Originator','Servicer','Sponsor','Holder'].");
			return muleIndemnificationOutputDTO;
		}
		
		if (!Arrays.asList("Agreement","Endorsement").contains(muleIndemnificationInputDTO.getStartDate())) {
			muleIndemnificationOutputDTO.setStatusCode("1");
			muleIndemnificationOutputDTO.setStatusMessage("Invalid startDate: " + muleIndemnificationInputDTO.getStartDate() + ". Allowed values: ['Agreement','Endorsement'].");
			return muleIndemnificationOutputDTO;
		}
		
		for (MuleIndemnificationFileDTO fileDTO : muleIndemnificationInputDTO.getDocuments().getFile()) {
			saveDocument(muleIndemnificationInputDTO.getCaseNumber(), fileDTO.getName(), Base64.getDecoder().decode(fileDTO.getDocument()));
		}
		return muleIndemnificationOutputDTO;
	}

}
