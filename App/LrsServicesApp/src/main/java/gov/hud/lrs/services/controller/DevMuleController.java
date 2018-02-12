package gov.hud.lrs.services.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import gov.hud.lrs.common.dto.FhacUserDTO;
import gov.hud.lrs.common.dto.MuleAuthorizationDTO;
import gov.hud.lrs.common.dto.MuleAuthorizationsSearchResultsDTO;
import gov.hud.lrs.common.dto.MuleBinderReceiptDTO;
import gov.hud.lrs.common.dto.MuleBinderReceiptResponseDTO;
import gov.hud.lrs.common.dto.MuleBinderRequestDTO;
import gov.hud.lrs.common.dto.MuleBinderRequestResponseDTO;
import gov.hud.lrs.common.dto.MuleCaseGetDetailDTO;
import gov.hud.lrs.common.dto.MuleCaseGetOutputDTO;
import gov.hud.lrs.common.dto.MuleCaseGetOutputDTO.GetCaseResult;
import gov.hud.lrs.common.dto.MuleDocumentGetOutputDTO;
import gov.hud.lrs.common.dto.MuleDocumentPostInputDTO;
import gov.hud.lrs.common.dto.MuleIndemnificationInputDTO;
import gov.hud.lrs.common.dto.MuleIndemnificationOutputDTO;
import gov.hud.lrs.common.dto.MuleLenderDTO;
import gov.hud.lrs.common.dto.MuleLenderDTO.MuleLender;
import gov.hud.lrs.common.dto.MuleLenderSearchResultsDTO;
import gov.hud.lrs.common.dto.MuleLenderSearchResultsDTO.MuleLenderSearchResults;
import gov.hud.lrs.common.dto.MuleResponseBase;
import gov.hud.lrs.common.dto.MuleUnderwriterSearchResultDTO;
import gov.hud.lrs.common.dto.MuleUniverseGetCaseDetailDTO;
import gov.hud.lrs.common.dto.MuleUniverseGetOutputDTO;
import gov.hud.lrs.common.dto.MuleUniverseGetOutputDTO.GetUniverseResult;
import gov.hud.lrs.common.entity.ext.LenderLookup;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.repository.ext.LenderLookupRepository;
import gov.hud.lrs.common.repository.ext.LrsCaseRecordRepository;
import gov.hud.lrs.common.service.CaseUniverseService;
import gov.hud.lrs.services.bizservice.DevMuleService;

@RestController
@RequestMapping("/api/v1/devMule")
public class DevMuleController {

	@Autowired private DevMuleService devMuleService;
	@Autowired private LenderLookupRepository lenderLookupRepository;
	@Autowired private LrsCaseRecordRepository lrsCaseRecordRepository;

	private ObjectMapper camelCaseToLowerCaseWithUnderscoresObjectMapper = new ObjectMapper();

	public DevMuleController() {
		camelCaseToLowerCaseWithUnderscoresObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(CaseUniverseService.DATE_FORMAT_STRING), true));
	}

	@InitBinder("binderRequestDTO")
	protected void initBinderRequestDTOBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "caseNumber", "reason", "hoc" });
	}

	@InitBinder("binderReceiptDTO")
	protected void initBinderReceiptDTOBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "caseNumbers" });
	}

	@RequestMapping(value = "/security/api/v1.0/authorizations/{userId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleAuthorizationsSearchResultsDTO> getFhacUser(@PathVariable String userId) {
		FhacUserDTO fhacUserDTO = devMuleService.getFhacUser(userId);
		if (fhacUserDTO == null) {
			throw new NotFoundException("No FHAC User for userId " + userId);
		}

		// this is a reverse mapping of what we do in FhacUserService
		MuleAuthorizationsSearchResultsDTO muleAuthorizationsSearchResultsDTO = new MuleAuthorizationsSearchResultsDTO();
		muleAuthorizationsSearchResultsDTO.setUserId(fhacUserDTO.getUserId());
		muleAuthorizationsSearchResultsDTO.setFirstName(fhacUserDTO.getFirstName());
		muleAuthorizationsSearchResultsDTO.setLastName(fhacUserDTO.getLastName());
		MuleAuthorizationDTO[] muleAuthorizationDTOs = new MuleAuthorizationDTO[fhacUserDTO.getRoles().size()];
		for (int i = 0; i < fhacUserDTO.getRoles().size(); i++) {
			muleAuthorizationDTOs[i] = new MuleAuthorizationDTO();
			muleAuthorizationDTOs[i].setAuthorizationName(fhacUserDTO.getRoles().get(i));
			muleAuthorizationDTOs[i].setAgencyId(fhacUserDTO.getLenderId());
		}
		muleAuthorizationsSearchResultsDTO.setSecurityAuthorizations(new ObjectMapper().convertValue(muleAuthorizationDTOs, JsonNode.class));
		muleAuthorizationsSearchResultsDTO.setStatusCode("0");
		muleAuthorizationsSearchResultsDTO.setStatusMessage("");

		return new ResponseEntity<MuleAuthorizationsSearchResultsDTO>(muleAuthorizationsSearchResultsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/v1.1/lenders/{id}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleLenderSearchResultsDTO> getLender(@PathVariable String id) {
		LenderLookup lenderLookup = lenderLookupRepository.findOne(id);
		if (lenderLookup == null) {
			throw new NotFoundException("No Lender for lenderId " + id);
		}

		MuleLenderDTO muleLenderDTO = new MuleLenderDTO();
		MuleLender lenderDetail = muleLenderDTO.new MuleLender();
		lenderDetail.setInstitutionId(lenderLookup.getSrvcrMrtgeNbr());
		lenderDetail.setInstitutionName(lenderLookup.getInstitutionName());
		muleLenderDTO.setLender(lenderDetail);

		MuleLenderSearchResultsDTO muleLenderSearchResultsDTO = new MuleLenderSearchResultsDTO();
		MuleLenderSearchResults lenderSearchResults = muleLenderSearchResultsDTO.new MuleLenderSearchResults();
		lenderSearchResults.setLenders(Arrays.asList(muleLenderDTO));
		lenderSearchResults.setStatusCode("0");
		lenderSearchResults.setStatusMessage("");
		muleLenderSearchResultsDTO.setLenderSearchResults(lenderSearchResults);

		return new ResponseEntity<MuleLenderSearchResultsDTO>(muleLenderSearchResultsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "partner/api/v1.0/underwriter", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleUnderwriterSearchResultDTO> getUnderwriter(@RequestParam(name = "id") String id) {
		MuleUnderwriterSearchResultDTO muleUnderwriterSearchResultDTO = devMuleService.getUnderwriter(id);
		if (muleUnderwriterSearchResultDTO == null) {
			throw new NotFoundException("No underwriter found for underwriterId " + id);
		}
		return new ResponseEntity<MuleUnderwriterSearchResultDTO>(muleUnderwriterSearchResultDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/edw/api/v1.0/universe/monthly", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleUniverseGetOutputDTO> getUniverseMonthly(
		@RequestParam(name = "start_date") Date startDate,
		@RequestParam(name = "end_date") Date endDate
	) {
		List<String> caseNumbers = lrsCaseRecordRepository.findCaseNumbersByEndrsmntDtBetweenInclusive(startDate, endDate);
		MuleUniverseGetOutputDTO muleUniverseGetOutputDTO = caseNumbersToMuleUniverseGetOutputDTO(caseNumbers);
		return new ResponseEntity<MuleUniverseGetOutputDTO>(muleUniverseGetOutputDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/edw/api/v1.0/universe/ec", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleUniverseGetOutputDTO> getUniverseEarlyClaim(
		@RequestParam(name = "start_date") Date startDate,
		@RequestParam(name = "end_date") Date endDate
	) {
		List<String> caseNumbers = lrsCaseRecordRepository.findCaseNumbersByEndrsmntDtBetweenInclusive(startDate, endDate);
		MuleUniverseGetOutputDTO muleUniverseGetOutputDTO = caseNumbersToMuleUniverseGetOutputDTO(caseNumbers);
		return new ResponseEntity<MuleUniverseGetOutputDTO>(muleUniverseGetOutputDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/edw/api/v1.0/universe/epd", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleUniverseGetOutputDTO> getUniverseEarlyPaymentDefault(
		@RequestParam(name = "start_date") Date startDate,
		@RequestParam(name = "end_date") Date endDate
	) {
		//List<String> caseNumbers = lrsCaseRecordRepository.findCaseNumbersByEndrsmntDtBetweenInclusive(startDate, endDate);
		List<String> caseNumbers = new ArrayList<String>();
		MuleUniverseGetOutputDTO muleUniverseGetOutputDTO = caseNumbersToMuleUniverseGetOutputDTO(caseNumbers);
		return new ResponseEntity<MuleUniverseGetOutputDTO>(muleUniverseGetOutputDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/edw/api/v1.0/universe/lenderMonitoring", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleUniverseGetOutputDTO> getLenderMonitorSelectiont(
		@RequestParam(name = "mortgee_id") String lenderId,
		@RequestParam(name = "review_type") String reviewType,
		@RequestParam(name = "start_date") Date startDate,
		@RequestParam(name = "end_date") Date endDate,
		@RequestParam(name = "Loan_Type") String loanType
	) {
		// for the local data we don't know how to filter by review type and loan type
		List<String> caseNumbers = lrsCaseRecordRepository.findCaseNumbersByLenderIdAndEndrsmntDtBetweenInclusiveLenderMonitoringRequest(lenderId, startDate, endDate);
		MuleUniverseGetOutputDTO muleUniverseGetOutputDTO = caseNumbersToMuleUniverseGetOutputDTO(caseNumbers);
		return new ResponseEntity<MuleUniverseGetOutputDTO>(muleUniverseGetOutputDTO, HttpStatus.OK);
	}

	private MuleUniverseGetOutputDTO caseNumbersToMuleUniverseGetOutputDTO(List<String> caseNumbers) {
		MuleUniverseGetOutputDTO muleUniverseGetOutputDTO = new MuleUniverseGetOutputDTO();
		GetUniverseResult getUniverseResult = new GetUniverseResult();
		MuleUniverseGetCaseDetailDTO[] muleUniverseGetCaseDetailDTOs = new MuleUniverseGetCaseDetailDTO[caseNumbers.size()];
		for (int i = 0; i < caseNumbers.size(); i++) {
			String caseNumber = caseNumbers.get(i);
			muleUniverseGetCaseDetailDTOs[i] = new MuleUniverseGetCaseDetailDTO();
			muleUniverseGetCaseDetailDTOs[i].setCase_number(caseNumber);
		}
		getUniverseResult.setStatusCode("0");
		getUniverseResult.setCaseDetail(muleUniverseGetCaseDetailDTOs);
		muleUniverseGetOutputDTO.setGetUniverseResult(getUniverseResult);

		return muleUniverseGetOutputDTO;
	}

	// see DevMuleService for an explanation of why we use a string here instead of LoanSelectionCaseSummary
	@RequestMapping(value = "/edw/api/v1.0/case", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleCaseGetOutputDTO> getCase(@RequestParam(name = "caseid") String caseNumber) {
		MuleCaseGetDetailDTO muleCaseGetDetailDTO = devMuleService.getLoanSelectionCaseSummary(caseNumber);
		if (muleCaseGetDetailDTO == null) {
			throw new NotFoundException("No case for caseid " + caseNumber);
		}

		MuleCaseGetOutputDTO muleCaseGetOutputDTO = new MuleCaseGetOutputDTO();
		GetCaseResult result = new GetCaseResult();
		result.setStatusCode("0");
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		mapper.setDateFormat(df);
		result.setCaseDetail(mapper.convertValue(muleCaseGetDetailDTO, JsonNode.class));
		muleCaseGetOutputDTO.setGetCaseResult(result);

		return new ResponseEntity<MuleCaseGetOutputDTO>(muleCaseGetOutputDTO, HttpStatus.OK);
	}

	// see DevMuleService for an explanation of why we use a string here instead of LoanSelectionCaseSummary
	@RequestMapping(value = "/edw/api/v1.0/batchCase", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleCaseGetOutputDTO[]> getCase(@RequestParam(name = "caseid") String[] caseNumbers) {
		List<MuleCaseGetDetailDTO> muleCaseGetDetailDTOs = devMuleService.getLoanSelectionCaseSummaries(caseNumbers);

		MuleCaseGetOutputDTO[] muleCaseGetOutputDTOs = new MuleCaseGetOutputDTO[muleCaseGetDetailDTOs.size()];
		for (int i = 0; i < muleCaseGetDetailDTOs.size(); i++) {
			MuleCaseGetDetailDTO muleCaseGetDetailDTO = muleCaseGetDetailDTOs.get(i);
			MuleCaseGetOutputDTO muleCaseGetOutputDTO = new MuleCaseGetOutputDTO();
			GetCaseResult result = new GetCaseResult();
			result.setStatusCode("0");
			ObjectMapper mapper = new ObjectMapper();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			mapper.setDateFormat(df);
			result.setCaseDetail(mapper.convertValue(muleCaseGetDetailDTO, JsonNode.class));
			muleCaseGetOutputDTO.setGetCaseResult(result);
			muleCaseGetOutputDTOs[i] = muleCaseGetOutputDTO;
		}
		return new ResponseEntity<MuleCaseGetOutputDTO[]>(muleCaseGetOutputDTOs, HttpStatus.OK);
	}

	@RequestMapping(value = "/sfo/api/v1.0/binderRequest", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleBinderRequestResponseDTO> binderRequest(@RequestBody MuleBinderRequestDTO binderRequestDTO) {
		MuleBinderRequestResponseDTO binderRequestResponseDTO = devMuleService.binderRequest(binderRequestDTO);
		return new ResponseEntity<MuleBinderRequestResponseDTO>(binderRequestResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/sfo/api/v1.0/binderReceipt", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleBinderReceiptResponseDTO> binderReceipt(@RequestBody MuleBinderReceiptDTO binderReceiptDTO) {
		MuleBinderReceiptResponseDTO binderReceiptResponseDTO = devMuleService.binderReceipt(binderReceiptDTO);
		return new ResponseEntity<MuleBinderReceiptResponseDTO>(binderReceiptResponseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/receiveEcaseBinder/{caseNumber}", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> receiveEcaseBinder(@PathVariable String caseNumber) {
		devMuleService.receiveBinder(caseNumber, new Date());
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/dms/api/v1.0/document", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleDocumentGetOutputDTO> getDocument(
		@RequestParam(name = "documentName") String documentName,
		@RequestParam(name = "documentKey") String documentKey,
		@RequestParam(name = "documentType") String documentType
	) {
		byte[] bytes = devMuleService.getDocument(documentKey, documentName);
		MuleDocumentGetOutputDTO muleDocumentGetOutputDTO = new MuleDocumentGetOutputDTO();
		if (bytes != null) {
			muleDocumentGetOutputDTO.setDocumentType(documentType);
			muleDocumentGetOutputDTO.setDocumentKey(documentKey);
			muleDocumentGetOutputDTO.setDocumentName(documentName);
			muleDocumentGetOutputDTO.setDocument(Base64.getEncoder().encodeToString(bytes));
			muleDocumentGetOutputDTO.setStatusCode("0");
			return new ResponseEntity<MuleDocumentGetOutputDTO>(HttpStatus.OK);
		} else {
			muleDocumentGetOutputDTO.setStatusCode("1");
			muleDocumentGetOutputDTO.setStatusMessage("Not found");
			return new ResponseEntity<MuleDocumentGetOutputDTO>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/dms/api/v1.0/document", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleResponseBase> putDocument(@RequestBody MuleDocumentPostInputDTO muleDocumentPostInputDTO) {
		devMuleService.saveDocument(
			muleDocumentPostInputDTO.getDocumentKey(),
			muleDocumentPostInputDTO.getDocumentName(),
			Base64.getDecoder().decode(muleDocumentPostInputDTO.getDocument())
 		);
		MuleResponseBase muleResponseBase = new MuleResponseBase();
		muleResponseBase.setStatusCode("0");
		return new ResponseEntity<MuleResponseBase>(muleResponseBase, HttpStatus.OK);
	}

	@RequestMapping(value = "/sfo/api/v1.0/indemnification", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MuleIndemnificationOutputDTO> postIndeminification(@RequestBody MuleIndemnificationInputDTO muleIndemnificationInputDTO) {
		MuleIndemnificationOutputDTO muleIndemnificationOutputDTO = devMuleService.indemnification(muleIndemnificationInputDTO);
		return new ResponseEntity<MuleIndemnificationOutputDTO>(muleIndemnificationOutputDTO, HttpStatus.OK);
	}

}
