// THIS FILE IS AUTO-GENERATED. DO NOT MODIFY.  RUN 'grunt server:apidesigner' TO EDIT THE SPECIFICATION
package gov.hud.lrs.services.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.security.access.prepost.PreAuthorize;

import gov.hud.lrs.common.dto.LrsApiV1DTOs; // The code-gen'd DTOs based on the same RAML as this file

import gov.hud.lrs.services.security.PolicyImpl; // Implementations of security policies associated with options defined in /raml/api/securitySchemes.raml

@SuppressWarnings("unused")
@Controller
public class LrsApiV1Controller {
	@Autowired
	private PolicyImpl policyImpl;

    @Autowired
    private AdminControllerImpl adminControllerImpl;
    @Autowired
    private BindersControllerImpl bindersControllerImpl;
    @Autowired
    private CapacityControllerImpl capacityControllerImpl;
    @Autowired
    private CasesControllerImpl casesControllerImpl;
    @Autowired
    private ConfigControllerImpl configControllerImpl;
    @Autowired
    private CurrentUserControllerImpl currentUserControllerImpl;
    @Autowired
    private DevLoginControllerImpl devLoginControllerImpl;
    @Autowired
    private DictionaryControllerImpl dictionaryControllerImpl;
    @Autowired
    private ErrorsControllerImpl errorsControllerImpl;
    @Autowired
    private ExceptionsControllerImpl exceptionsControllerImpl;
    @Autowired
    private FhaConnectionControllerImpl fhaConnectionControllerImpl;
    @Autowired
    private JobsControllerImpl jobsControllerImpl;
    @Autowired
    private LenderMonitoringControllerImpl lenderMonitoringControllerImpl;
    @Autowired
    private LenderSelfReportControllerImpl lenderSelfReportControllerImpl;
    @Autowired
    private LocationsControllerImpl locationsControllerImpl;
    @Autowired
    private ManualSelectionControllerImpl manualSelectionControllerImpl;
    @Autowired
    private ReviewersControllerImpl reviewersControllerImpl;
    @Autowired
    private ReviewsControllerImpl reviewsControllerImpl;

    @PreAuthorize("hasRole('ROLE_PROGRAM_ASSISTANT') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/binders/fha",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> binders_getBinderFha(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return bindersControllerImpl.getBinderFha(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_PROGRAM_ASSISTANT') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/binders/fha/{binderId}/receive",
        method = RequestMethod.PUT,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> binders_putBinderFhaReceive(
        
        @PathVariable("binderId") String binderId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "requestGeography"
        )),
             null, // json body dto 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
             binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return bindersControllerImpl.putBinderFhaReceive(
            binderId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_LRS_READ_ONLY') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER')")
    @RequestMapping(
        value = "/api/v1/binders/lender",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> binders_getBinderLender(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return bindersControllerImpl.getBinderLender(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER')")
    @RequestMapping(
        value = "/api/v1/binders/lender/{binderId}/send",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> binders_putBinderLenderSend(
        
        @PathVariable("binderId") String binderId,
        
        @RequestBody LrsApiV1DTOs.BinderDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return bindersControllerImpl.putBinderLenderSend(
            binderId,
            data,
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/config",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> config_getConfig(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return configControllerImpl.getConfig(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/reviewTypes",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryReviewTypes(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryReviewTypes(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/loanTypes",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryLoanTypes(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryLoanTypes(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/productTypes",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryProductTypes(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryProductTypes(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/consolidatedSelectionReasons",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryConsolidatedSelectionReasons(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryConsolidatedSelectionReasons(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/selectionReasons",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionarySelectionReasons(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionarySelectionReasons(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/selectionSubreasons",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionarySelectionSubreasons(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionarySelectionSubreasons(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/reassignmentReasons",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryReassignmentReasons(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryReassignmentReasons(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/reviewCancelReasons",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryReviewCancelReasons(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryReviewCancelReasons(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/ratingCodes",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryRatingCodes(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryRatingCodes(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/documentLocations",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryDocumentLocations(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryDocumentLocations(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/fraudTypes",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryFraudTypes(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryFraudTypes(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/fraudParticipants",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryFraudParticipants(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryFraudParticipants(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/dictionary/exceptionTypes",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryExceptionTypes(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryExceptionTypes(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/dictionary/factors",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryFactors(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryFactors(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/dictionary/selectionModelCategories",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionarySelectionModelCategories(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionarySelectionModelCategories(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/dictionary/assignmentModelCategories",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryAssignmentModelCategories(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryAssignmentModelCategories(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/dictionary/distributionModelCategories",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> dictionary_getDictionaryDistributionModelCategories(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return dictionaryControllerImpl.getDictionaryDistributionModelCategories(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/errors",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> errors_postError(
        @RequestBody LrsApiV1DTOs.ErrorDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return errorsControllerImpl.postError(
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/manualSelection",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> manualSelection_postManualSelection(
        @RequestBody LrsApiV1DTOs.ManualSelectionDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "requestGeography", "batchOwnerGeographyAndSkill", "batchParticipantsGeography"
        )),
            data, 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return manualSelectionControllerImpl.postManualSelection(
            data,
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/devLogin",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> devLogin_getDevLogin(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "devMode"
        )),
             null, // json body dto 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return devLoginControllerImpl.getDevLogin(
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/devLogin",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> devLogin_postDevLogin(
        @RequestBody LrsApiV1DTOs.DevLoginDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "devMode"
        )),
            data, 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return devLoginControllerImpl.postDevLogin(
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviews",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviews(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviews(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/my",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewMy(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewMy(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/my/lender",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewMyLender(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewMyLender(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_REVIEWER') or hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviews/my/completed",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewMyCompleted(
        
        
        @RequestParam(value = "startDate", required = false) String startDate,
        
        
        @RequestParam(value = "endDate", required = false) String endDate,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewMyCompleted(
            startDate,
            endDate,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_REVIEWER') or hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviews/my/completed/location/{locationId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewMyCompletedLocationByLocationId(
        
        @PathVariable("locationId") String locationId,
        
        
        
        @RequestParam(value = "startDate", required = false) String startDate,
        
        
        @RequestParam(value = "endDate", required = false) String endDate,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewMyCompletedLocationByLocationId(
            locationId,
            startDate,
            endDate,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/my/completed/lender",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewMyCompletedLender(
        
        
        @RequestParam(value = "startDate", required = false) String startDate,
        
        
        @RequestParam(value = "endDate", required = false) String endDate,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewMyCompletedLender(
            startDate,
            endDate,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/my/completed/batches",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewMyCompletedBatches(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewMyCompletedBatches(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviews/my/completed/batches/location/{locationId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewMyCompletedBatchLocationByLocationId(
        
        @PathVariable("locationId") String locationId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewMyCompletedBatchLocationByLocationId(
            locationId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/my/completed/batches/lender",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewMyCompletedBatchLender(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewMyCompletedBatchLender(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviews/team",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewTeam(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewTeam(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/batches",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewBatches(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewBatches(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviews/batches/location/{locationId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewBatchLocationByLocationId(
        
        @PathVariable("locationId") String locationId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewBatchLocationByLocationId(
            locationId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/batches/lender",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewBatchLender(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewBatchLender(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/batches/{batchId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewBatchByBatchId(
        
        @PathVariable("batchId") String batchId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewBatchByBatchId(
            batchId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/batches/{batchId}/lenderByBatchId",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewBatchLenderByBatchId(
        
        @PathVariable("batchId") String batchId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "lenderBatchOwner"
        )),
             null, // json body dto 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
             batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewBatchLenderByBatchId(
            batchId,
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/reviews/batches/{batchId}/submit",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewBatchSubmit(
        
        @PathVariable("batchId") String batchId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "batchOwner"
        )),
             null, // json body dto 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
             batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewBatchSubmit(
            batchId,
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/reviews/batches/{batchId}/submit/lender",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewBatchSubmitLender(
        
        @PathVariable("batchId") String batchId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "lenderBatchOwnerActive"
        )),
             null, // json body dto 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
             batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewBatchSubmitLender(
            batchId,
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/reviews/batches/{batchId}/cancel",
        method = RequestMethod.PUT,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewBatchCancel(
        
        @PathVariable("batchId") String batchId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewBatchCancel(
            batchId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_RESPONSE_COORDINATOR')")
    @RequestMapping(
        value = "/api/v1/reviews/batches/{batchId}/operationalDocuments",
        method = RequestMethod.POST,
        consumes ={ MediaType.MULTIPART_FORM_DATA_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewBatchOperationalDocument(
        
        @PathVariable("batchId") String batchId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "lenderBatchOwner"
        )),
             null, // json body dto 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
             batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewBatchOperationalDocument(
            batchId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_LRS_READ_ONLY') or hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/batches/{batchId}/operationalDocuments/{documentId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewBatchOperationalDocumentByDocumentId(
        
        @PathVariable("batchId") String batchId,
        
        
        @PathVariable("documentId") String documentId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "lenderBatchOwner"
        )),
             null, // json body dto 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
             batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewBatchOperationalDocumentByDocumentId(
            batchId,
            documentId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_RESPONSE_COORDINATOR')")
    @RequestMapping(
        value = "/api/v1/reviews/batches/{batchId}/operationalDocuments/{documentId}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_deleteReviewBatchOperationalDocument(
        
        @PathVariable("batchId") String batchId,
        
        
        @PathVariable("documentId") String documentId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "lenderBatchOwner"
        )),
             null, // json body dto 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
             batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.deleteReviewBatchOperationalDocument(
            batchId,
            documentId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_RESPONSE_COORDINATOR')")
    @RequestMapping(
        value = "/api/v1/reviews/batches/{batchId}/submitOperationalDocuments",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewBatchSubmitOperationalDocument(
        
        @PathVariable("batchId") String batchId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "lenderBatchOwner"
        )),
             null, // json body dto 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
             batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewBatchSubmitOperationalDocument(
            batchId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_RESPONSE_COORDINATOR')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewByReviewId(
        
        @PathVariable("reviewId") String reviewId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender"
        )),
             null, // json body dto 
             reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewByReviewId(
            reviewId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/lender",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLender(
        
        @PathVariable("reviewId") String reviewId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender"
        )),
             null, // json body dto 
             reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLender(
            reviewId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/notes",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewNote(
        
        @PathVariable("reviewId") String reviewId,
        
        @RequestBody LrsApiV1DTOs.ReviewNotePostDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser"
        )),
            data, 
             reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewNote(
            reviewId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/notes",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewNotes(
        
        @PathVariable("reviewId") String reviewId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewNotes(
            reviewId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/referral",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewReferral(
        
        @PathVariable("reviewId") String reviewId,
        
        @RequestBody LrsApiV1DTOs.ReviewReferralDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser"
        )),
            data, 
             reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewReferral(
            reviewId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/referral",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_deleteReviewReferral(
        
        @PathVariable("reviewId") String reviewId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser"
        )),
             null, // json body dto 
             reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.deleteReviewReferral(
            reviewId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/referral",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewReferral(
        
        @PathVariable("reviewId") String reviewId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser"
        )),
             null, // json body dto 
             reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewReferral(
            reviewId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/indemnification/prePopulated",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLevelIndemnificationPrePopulated(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLevelIndemnificationPrePopulated(
            reviewId,
            reviewLevelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_INDEMNIFIER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/indemnification/preSigned/lender",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLevelIndemnificationPreSignedLender(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLevelIndemnificationPreSignedLender(
            reviewId,
            reviewLevelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/indemnification/preSigned/reviewer",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLevelIndemnificationPreSignedReviewer(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLevelIndemnificationPreSignedReviewer(
            reviewId,
            reviewLevelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_INDEMNIFIER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/indemnification/preSigned/both",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLevelIndemnificationPreSignedBoth(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLevelIndemnificationPreSignedBoth(
            reviewId,
            reviewLevelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_INDEMNIFIER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/indemnification/lenderSubmit",
        method = RequestMethod.PUT,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewLevelIndemnificationLenderSubmit(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender", "activeLenderRequest"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewLevelIndemnificationLenderSubmit(
            reviewId,
            reviewLevelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/indemnification/reviewerForceSubmit",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewLevelIndemnificationReviewerForceSubmit(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        @RequestBody LrsApiV1DTOs.ReviewLevelInfoDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "forcedIndemnificationSkill"
        )),
            data, 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewLevelIndemnificationReviewerForceSubmit(
            reviewId,
            reviewLevelId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/indemnification/reviewerAccept",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewLevelIndemnificationReviewerAccept(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        @RequestBody LrsApiV1DTOs.IndemAcceptDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser", "indemnificationSkill"
        )),
            data, 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewLevelIndemnificationReviewerAccept(
            reviewId,
            reviewLevelId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/indemnification/reviewerReject",
        method = RequestMethod.PUT,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewLevelIndemnificationReviewerReject(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser", "indemnificationSkill"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewLevelIndemnificationReviewerReject(
            reviewId,
            reviewLevelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/indemnification/final",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLevelIndemnificationFinal(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLevelIndemnificationFinal(
            reviewId,
            reviewLevelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/confirmVetting",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewLevelConfirmVetting(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewLevelConfirmVetting(
            reviewId,
            reviewLevelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/submit",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewLevelSubmit(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        @RequestBody LrsApiV1DTOs.ReviewLevelInfoDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser"
        )),
            data, 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewLevelSubmit(
            reviewId,
            reviewLevelId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/indemnificationType",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewLevelIndemnificationType(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        @RequestBody LrsApiV1DTOs.ReviewLevelInfoDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser", "batchOwner"
        )),
            data, 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewLevelIndemnificationType(
            reviewId,
            reviewLevelId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/lenderSubmit",
        method = RequestMethod.PUT,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewLevelLenderSubmit(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeLenderRequest", "reviewForLender"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewLevelLenderSubmit(
            reviewId,
            reviewLevelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/cancel",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewLevelCancel(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        @RequestBody LrsApiV1DTOs.SimpleSelectionDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevel"
        )),
            data, 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewLevelCancel(
            reviewId,
            reviewLevelId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/reassign",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewLevelReassign(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        @RequestBody LrsApiV1DTOs.SimpleSelectionDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevel"
        )),
            data, 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewLevelReassign(
            reviewId,
            reviewLevelId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/fields",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLevelFields(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLevelFields(
            reviewId,
            reviewLevelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/fields/updateField",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewLevelFieldUpdateField(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        @RequestBody LrsApiV1DTOs.ReviewFieldDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser"
        )),
            data, 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewLevelFieldUpdateField(
            reviewId,
            reviewLevelId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/findings",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewLevelFinding(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        @RequestBody LrsApiV1DTOs.ReviewFindingDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser"
        )),
            data, 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewLevelFinding(
            reviewId,
            reviewLevelId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/findings",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLevelFindings(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLevelFindings(
            reviewId,
            reviewLevelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/findings/lender",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLevelFindingLender(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLevelFindingLender(
            reviewId,
            reviewLevelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/findings/{findingId}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewLevelFinding(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        
        @PathVariable("findingId") String findingId,
        
        @RequestBody LrsApiV1DTOs.ReviewFindingDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser"
        )),
            data, 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewLevelFinding(
            reviewId,
            reviewLevelId,
            findingId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/findings/{findingId}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_deleteReviewLevelFinding(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        
        @PathVariable("findingId") String findingId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.deleteReviewLevelFinding(
            reviewId,
            reviewLevelId,
            findingId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/findings/{findingId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLevelFindingByFindingId(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        
        @PathVariable("findingId") String findingId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLevelFindingByFindingId(
            reviewId,
            reviewLevelId,
            findingId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/findings/{findingId}/lender",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLevelFindingLender(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        
        @PathVariable("findingId") String findingId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLevelFindingLender(
            reviewId,
            reviewLevelId,
            findingId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/findings/{findingId}/lender",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewLevelFindingLender(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        
        @PathVariable("findingId") String findingId,
        
        @RequestBody LrsApiV1DTOs.ReviewFindingDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender", "activeLenderRequest"
        )),
            data, 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewLevelFindingLender(
            reviewId,
            reviewLevelId,
            findingId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/findings/{findingId}/lenderResponseDocuments",
        method = RequestMethod.POST,
        consumes ={ MediaType.MULTIPART_FORM_DATA_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewLevelFindingLenderResponseDocument(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        
        @PathVariable("findingId") String findingId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender", "activeLenderRequest"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewLevelFindingLenderResponseDocument(
            reviewId,
            reviewLevelId,
            findingId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_LRS_READ_ONLY') or hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/findings/{findingId}/lenderResponseDocuments/{documentId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLevelFindingLenderResponseDocumentByDocumentId(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        
        @PathVariable("findingId") String findingId,
        
        
        @PathVariable("documentId") String documentId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLevelFindingLenderResponseDocumentByDocumentId(
            reviewId,
            reviewLevelId,
            findingId,
            documentId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/findings/{findingId}/lenderResponseDocuments/{documentId}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_deleteReviewLevelFindingLenderResponseDocument(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        
        @PathVariable("findingId") String findingId,
        
        
        @PathVariable("documentId") String documentId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewForLender", "activeLenderRequest"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.deleteReviewLevelFindingLenderResponseDocument(
            reviewId,
            reviewLevelId,
            findingId,
            documentId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/qaTree/{qaTreeId}/answers",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewLevelQaTreeAnswer(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        
        @PathVariable("qaTreeId") String qaTreeId,
        
        @RequestBody LrsApiV1DTOs.ReviewAnswerDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser"
        )),
            data, 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewLevelQaTreeAnswer(
            reviewId,
            reviewLevelId,
            qaTreeId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/qaTree/{qaTreeId}/answers",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewLevelQaTreeAnswers(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        
        @PathVariable("qaTreeId") String qaTreeId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewLevelQaTreeAnswers(
            reviewId,
            reviewLevelId,
            qaTreeId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/qaTree/{qaTreeId}/answers/{answerId}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_putReviewLevelQaTreeAnswer(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        
        @PathVariable("qaTreeId") String qaTreeId,
        
        
        @PathVariable("answerId") String answerId,
        
        @RequestBody LrsApiV1DTOs.ReviewAnswerDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser"
        )),
            data, 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.putReviewLevelQaTreeAnswer(
            reviewId,
            reviewLevelId,
            qaTreeId,
            answerId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/level/{reviewLevelId}/qaTree/{qaTreeId}/answers/{answerId}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_deleteReviewLevelQaTreeAnswer(
        
        @PathVariable("reviewId") String reviewId,
        
        
        @PathVariable("reviewLevelId") String reviewLevelId,
        
        
        @PathVariable("qaTreeId") String qaTreeId,
        
        
        @PathVariable("answerId") String answerId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser"
        )),
             null, // json body dto 
             reviewId,
             reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.deleteReviewLevelQaTreeAnswer(
            reviewId,
            reviewLevelId,
            qaTreeId,
            answerId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/managementDecision",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewManagementDecision(
        
        @PathVariable("reviewId") String reviewId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "hqEscalationSkill"
        )),
             null, // json body dto 
             reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewManagementDecision(
            reviewId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/forceEscalation",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewForceEscalation(
        
        @PathVariable("reviewId") String reviewId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "activeReviewLevelAssignedToUser", "anyEscalationSkill"
        )),
             null, // json body dto 
             reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewForceEscalation(
            reviewId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviews/{reviewId}/mrbReferral",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_postReviewMrbReferral(
        
        @PathVariable("reviewId") String reviewId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "hqEscalationSkill"
        )),
             null, // json body dto 
             reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.postReviewMrbReferral(
            reviewId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/qaTree/{qaTreeId}/questions",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewQaTreeQuestions(
        
        @PathVariable("qaTreeId") String qaTreeId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return reviewsControllerImpl.getReviewQaTreeQuestions(
            qaTreeId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/dictionary/qaModels/activeDefectAreas",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewDictionaryQaModelActiveDefectAreas(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewsControllerImpl.getReviewDictionaryQaModelActiveDefectAreas(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/dictionary/qaModels/{qaModelId}/defectAreas",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewDictionaryQaModelDefectAreas(
        
        @PathVariable("qaModelId") String qaModelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return reviewsControllerImpl.getReviewDictionaryQaModelDefectAreas(
            qaModelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviews/dictionary/qaModels/{qaModelId}/defectAreas/{qaTreeId}/remediationTypes",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewDictionaryQaModelDefectAreaRemediationTypes(
        
        @PathVariable("qaModelId") String qaModelId,
        
        
        @PathVariable("qaTreeId") String qaTreeId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return reviewsControllerImpl.getReviewDictionaryQaModelDefectAreaRemediationTypes(
            qaModelId,
            qaTreeId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/dictionary/qaModels/{qaModelId}/defectAreaCauses",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewDictionaryQaModelDefectAreaCauses(
        
        @PathVariable("qaModelId") String qaModelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return reviewsControllerImpl.getReviewDictionaryQaModelDefectAreaCauses(
            qaModelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/dictionary/qaModels/{qaModelId}/defectAreaSources",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewDictionaryQaModelDefectAreaSources(
        
        @PathVariable("qaModelId") String qaModelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return reviewsControllerImpl.getReviewDictionaryQaModelDefectAreaSources(
            qaModelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/reviews/dictionary/qaModels/{qaModelId}/defectAreaSeverities",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviews_getReviewDictionaryQaModelDefectAreaSeverities(
        
        @PathVariable("qaModelId") String qaModelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("Cache-Control", "max-age=86400");
    
        return reviewsControllerImpl.getReviewDictionaryQaModelDefectAreaSeverities(
            qaModelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviewers",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviewers_getReviewers(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewersControllerImpl.getReviewers(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviewers",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviewers_postReviewer(
        @RequestBody LrsApiV1DTOs.ReviewerCreationDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewerChainOfCommand", "reviewerSupervisorGeographyChainOfCommand"
        )),
            data, 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewersControllerImpl.postReviewer(
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviewers/active",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviewers_getReviewerActive(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewersControllerImpl.getReviewerActive(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviewers/hq",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviewers_getReviewerHq(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewersControllerImpl.getReviewerHq(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviewers/qualifiedToReview/{reviewId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviewers_getReviewerQualifiedToReviewByReviewId(
        
        @PathVariable("reviewId") String reviewId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewersControllerImpl.getReviewerQualifiedToReviewByReviewId(
            reviewId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviewers/qualifiedToReview/{reviewId}/myLocation",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviewers_getReviewerQualifiedToReviewMyLocation(
        
        @PathVariable("reviewId") String reviewId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewersControllerImpl.getReviewerQualifiedToReviewMyLocation(
            reviewId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviewers/{reviewerId}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviewers_putReviewer(
        
        @PathVariable("reviewerId") String reviewerId,
        
        @RequestBody LrsApiV1DTOs.ReviewerDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewerChainOfCommand", "reviewerSupervisorGeographyChainOfCommand", "reviewerVetterGeography"
        )),
            data, 
            null, //  reviewId,
            null, //  reviewLevelId,
             reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewersControllerImpl.putReviewer(
            reviewerId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/reviewers/{reviewerId}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviewers_deleteReviewer(
        
        @PathVariable("reviewerId") String reviewerId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "reviewerChainOfCommand"
        )),
             null, // json body dto 
            null, //  reviewId,
            null, //  reviewLevelId,
             reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewersControllerImpl.deleteReviewer(
            reviewerId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/reviewers/{reviewerId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> reviewers_getReviewerByReviewerId(
        
        @PathVariable("reviewerId") String reviewerId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return reviewersControllerImpl.getReviewerByReviewerId(
            reviewerId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_REVIEWER') or hasRole('ROLE_PROGRAM_ASSISTANT') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_LRS_READ_ONLY') or hasRole('ROLE_MONITOR_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/locations",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> locations_getLocations(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return locationsControllerImpl.getLocations(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_REVIEWER') or hasRole('ROLE_PROGRAM_ASSISTANT') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/locations/{id}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> locations_getLocationById(
        
        @PathVariable("id") String id,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return locationsControllerImpl.getLocationById(
            id,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/locations/{id}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> locations_putLocation(
        
        @PathVariable("id") String id,
        
        @RequestBody LrsApiV1DTOs.LocationDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return locationsControllerImpl.putLocation(
            id,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/fhaConnection/{hudId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> fhaConnection_getFhaConnectionByHudId(
        
        @PathVariable("hudId") String hudId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return fhaConnectionControllerImpl.getFhaConnectionByHudId(
            hudId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/cases/search",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> cases_postCaseSearch(
        @RequestBody LrsApiV1DTOs.CaseRequestDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return casesControllerImpl.postCaseSearch(
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER')")
    @RequestMapping(
        value = "/api/v1/cases/selfReportSearch",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> cases_postCaseSelfReportSearch(
        @RequestBody LrsApiV1DTOs.CaseRequestDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return casesControllerImpl.postCaseSelfReportSearch(
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/lenderMonitoring",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> lenderMonitoring_postLenderMonitoring(
        @RequestBody LrsApiV1DTOs.LenderMonitoringDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        policyImpl.validate(new ArrayList<String>(Arrays.asList(
            "requestGeography", "batchOwnerGeographyAndSkill", "batchParticipantsGeography"
        )),
            data, 
            null, //  reviewId,
            null, //  reviewLevelId,
            null, //  reviewerId,
            null, //  binderId,
            null //  batchId
        );
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return lenderMonitoringControllerImpl.postLenderMonitoring(
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER')")
    @RequestMapping(
        value = "/api/v1/lenderSelfReport",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> lenderSelfReport_postLenderSelfReport(
        @RequestBody LrsApiV1DTOs.LenderSelfReportDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return lenderSelfReportControllerImpl.postLenderSelfReport(
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/lenderSelfReport/{selectionRequestId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> lenderSelfReport_getLenderSelfReportBySelectionRequestId(
        
        @PathVariable("selectionRequestId") String selectionRequestId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return lenderSelfReportControllerImpl.getLenderSelfReportBySelectionRequestId(
            selectionRequestId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_PROGRAM_ASSISTANT')")
    @RequestMapping(
        value = "/api/v1/exceptions",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> exceptions_getExceptions(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return exceptionsControllerImpl.getExceptions(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_PROGRAM_ASSISTANT')")
    @RequestMapping(
        value = "/api/v1/exceptions/{exceptionId}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> exceptions_putException(
        
        @PathVariable("exceptionId") String exceptionId,
        
        @RequestBody LrsApiV1DTOs.ExceptionDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return exceptionsControllerImpl.putException(
            exceptionId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_PROGRAM_ASSISTANT')")
    @RequestMapping(
        value = "/api/v1/exceptions/{exceptionId}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> exceptions_deleteException(
        
        @PathVariable("exceptionId") String exceptionId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return exceptionsControllerImpl.deleteException(
            exceptionId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_PROGRAM_ASSISTANT')")
    @RequestMapping(
        value = "/api/v1/exceptions/{exceptionId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> exceptions_getExceptionByExceptionId(
        
        @PathVariable("exceptionId") String exceptionId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return exceptionsControllerImpl.getExceptionByExceptionId(
            exceptionId,
            request,
            response
        );
    }
    
    @RequestMapping(
        value = "/api/v1/currentUser",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> currentUser_getCurrentUser(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return currentUserControllerImpl.getCurrentUser(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_REVIEWER') or hasRole('ROLE_PROGRAM_ASSISTANT') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_INDEMNIFIER') or hasRole('ROLE_RESPONSE_COORDINATOR') or hasRole('ROLE_LRS_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/admin/reviewLevel/lenderResponseTimeLimits",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminReviewLevelLenderResponseTimeLimits(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminReviewLevelLenderResponseTimeLimits(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/reviewLevel/lenderResponseTimeLimits/{reviewLevelCode}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminReviewLevelLenderResponseTimeLimit(
        
        @PathVariable("reviewLevelCode") String reviewLevelCode,
        
        @RequestBody LrsApiV1DTOs.TimeLimitDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminReviewLevelLenderResponseTimeLimit(
            reviewLevelCode,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_REVIEWER') or hasRole('ROLE_PROGRAM_ASSISTANT') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/reviewLevel/reviewerTimeLimits",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminReviewLevelReviewerTimeLimits(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminReviewLevelReviewerTimeLimits(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/reviewLevel/reviewerTimeLimits/{reviewLevelCode}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminReviewLevelReviewerTimeLimit(
        
        @PathVariable("reviewLevelCode") String reviewLevelCode,
        
        @RequestBody LrsApiV1DTOs.TimeLimitDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminReviewLevelReviewerTimeLimit(
            reviewLevelCode,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/selectionModels",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminSelectionModels(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminSelectionModels(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/selectionModels/{id}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminSelectionModelById(
        
        @PathVariable("id") String id,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminSelectionModelById(
            id,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/selectionModels/{id}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminSelectionModel(
        
        @PathVariable("id") String id,
        
        @RequestBody LrsApiV1DTOs.SelectionModelDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminSelectionModel(
            id,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/selectionModels/{id}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_deleteAdminSelectionModel(
        
        @PathVariable("id") String id,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.deleteAdminSelectionModel(
            id,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/selectionModels/{id}/duplicate",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminSelectionModelDuplicate(
        
        @PathVariable("id") String id,
        
        @RequestBody LrsApiV1DTOs.ModelDuplicatorDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminSelectionModelDuplicate(
            id,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/assignmentModels",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminAssignmentModels(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminAssignmentModels(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/assignmentModels/{id}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminAssignmentModelById(
        
        @PathVariable("id") String id,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminAssignmentModelById(
            id,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/assignmentModels/{id}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminAssignmentModel(
        
        @PathVariable("id") String id,
        
        @RequestBody LrsApiV1DTOs.AssignmentModelDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminAssignmentModel(
            id,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/assignmentModels/{id}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_deleteAdminAssignmentModel(
        
        @PathVariable("id") String id,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.deleteAdminAssignmentModel(
            id,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/assignmentModels/{id}/duplicate",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminAssignmentModelDuplicate(
        
        @PathVariable("id") String id,
        
        @RequestBody LrsApiV1DTOs.ModelDuplicatorDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminAssignmentModelDuplicate(
            id,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/distributionModels",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminDistributionModels(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminDistributionModels(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/distributionModels/{id}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminDistributionModelById(
        
        @PathVariable("id") String id,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminDistributionModelById(
            id,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/distributionModels/{id}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminDistributionModel(
        
        @PathVariable("id") String id,
        
        @RequestBody LrsApiV1DTOs.DistributionModelDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminDistributionModel(
            id,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/distributionModels/{id}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_deleteAdminDistributionModel(
        
        @PathVariable("id") String id,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.deleteAdminDistributionModel(
            id,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/distributionModels/{id}/duplicate",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminDistributionModelDuplicate(
        
        @PathVariable("id") String id,
        
        @RequestBody LrsApiV1DTOs.ModelDuplicatorDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminDistributionModelDuplicate(
            id,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/emails",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminEmails(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminEmails(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/emails/{templateId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminEmailByTemplateId(
        
        @PathVariable("templateId") String templateId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminEmailByTemplateId(
            templateId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/emails/{templateId}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminEmail(
        
        @PathVariable("templateId") String templateId,
        
        @RequestBody LrsApiV1DTOs.EmailTemplateVersionDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminEmail(
            templateId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/emails/{templateId}/duplicate",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminEmailDuplicate(
        
        @PathVariable("templateId") String templateId,
        
        @RequestBody LrsApiV1DTOs.ModelDuplicatorDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminEmailDuplicate(
            templateId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/emails/{templateId}/activate",
        method = RequestMethod.PUT,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminEmailActivate(
        
        @PathVariable("templateId") String templateId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminEmailActivate(
            templateId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/emails/{templateId}/sendTest/{emailAddress}",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_postAdminEmailSendTest(
        
        @PathVariable("templateId") String templateId,
        
        
        @PathVariable("emailAddress") String emailAddress,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.postAdminEmailSendTest(
            templateId,
            emailAddress,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/lenderSuppressions",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminLenderSuppressions(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminLenderSuppressions(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/lenderSuppressions/{lenderId}",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_postAdminLenderSuppression(
        
        @PathVariable("lenderId") String lenderId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.postAdminLenderSuppression(
            lenderId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/lenderSuppressions/{lenderId}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_deleteAdminLenderSuppression(
        
        @PathVariable("lenderId") String lenderId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.deleteAdminLenderSuppression(
            lenderId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/lenderSelectionIncreases",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminLenderSelectionIncreases(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminLenderSelectionIncreases(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/lenderSelectionIncreases/{lenderId}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_deleteAdminLenderSelectionIncrease(
        
        @PathVariable("lenderId") String lenderId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.deleteAdminLenderSelectionIncrease(
            lenderId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/lenderSelectionIncreases/{lenderId}",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_postAdminLenderSelectionIncrease(
        
        @PathVariable("lenderId") String lenderId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.postAdminLenderSelectionIncrease(
            lenderId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/lenderSelectionIncreases/{lenderId}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminLenderSelectionIncrease(
        
        @PathVariable("lenderId") String lenderId,
        
        @RequestBody LrsApiV1DTOs.LenderSelectionAdjustmentDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminLenderSelectionIncrease(
            lenderId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/underwriterSelectionIncreases",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminUnderwriterSelectionIncreases(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminUnderwriterSelectionIncreases(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/underwriterSelectionIncreases/{lenderId}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_deleteAdminUnderwriterSelectionIncrease(
        
        @PathVariable("lenderId") String lenderId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.deleteAdminUnderwriterSelectionIncrease(
            lenderId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/underwriterSelectionIncreases/{lenderId}",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_postAdminUnderwriterSelectionIncrease(
        
        @PathVariable("lenderId") String lenderId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.postAdminUnderwriterSelectionIncrease(
            lenderId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/underwriterSelectionIncreases/{lenderId}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminUnderwriterSelectionIncrease(
        
        @PathVariable("lenderId") String lenderId,
        
        @RequestBody LrsApiV1DTOs.LenderSelectionAdjustmentDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminUnderwriterSelectionIncrease(
            lenderId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/fields",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminFields(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminFields(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN') or hasRole('ROLE_REVIEWER')")
    @RequestMapping(
        value = "/api/v1/admin/qaModels",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminQaModels(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminQaModels(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/qaModels/{qaModelId}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminQaModel(
        
        @PathVariable("qaModelId") String qaModelId,
        
        @RequestBody LrsApiV1DTOs.QaModelDetailDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminQaModel(
            qaModelId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/qaModels/{qaModelId}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_deleteAdminQaModel(
        
        @PathVariable("qaModelId") String qaModelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.deleteAdminQaModel(
            qaModelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/qaModels/{qaModelId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminQaModelByQaModelId(
        
        @PathVariable("qaModelId") String qaModelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminQaModelByQaModelId(
            qaModelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/qaModels/{qaModelId}/activate",
        method = RequestMethod.PUT,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminQaModelActivate(
        
        @PathVariable("qaModelId") String qaModelId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminQaModelActivate(
            qaModelId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/qaModels/{qaModelId}/duplicate",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminQaModelDuplicate(
        
        @PathVariable("qaModelId") String qaModelId,
        
        @RequestBody LrsApiV1DTOs.ModelDuplicatorDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminQaModelDuplicate(
            qaModelId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/qaModels/{qaModelId}/defectAreas",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_postAdminQaModelDefectArea(
        
        @PathVariable("qaModelId") String qaModelId,
        
        @RequestBody LrsApiV1DTOs.QaModelDefectAreaDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.postAdminQaModelDefectArea(
            qaModelId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/qaModels/{qaModelId}/defectAreas/{defectAreaId}",
        method = RequestMethod.PUT,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_putAdminQaModelDefectArea(
        
        @PathVariable("qaModelId") String qaModelId,
        
        
        @PathVariable("defectAreaId") String defectAreaId,
        
        @RequestBody LrsApiV1DTOs.QaModelDefectAreaDetailDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.putAdminQaModelDefectArea(
            qaModelId,
            defectAreaId,
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/qaModels/{qaModelId}/defectAreas/{defectAreaId}",
        method = RequestMethod.DELETE,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_deleteAdminQaModelDefectArea(
        
        @PathVariable("qaModelId") String qaModelId,
        
        
        @PathVariable("defectAreaId") String defectAreaId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.deleteAdminQaModelDefectArea(
            qaModelId,
            defectAreaId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/qaModels/{qaModelId}/defectAreas/{defectAreaId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminQaModelDefectAreaByDefectAreaId(
        
        @PathVariable("qaModelId") String qaModelId,
        
        
        @PathVariable("defectAreaId") String defectAreaId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminQaModelDefectAreaByDefectAreaId(
            qaModelId,
            defectAreaId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/admin/staffManagementReviewLevels",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> admin_getAdminStaffManagementReviewLevels(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return adminControllerImpl.getAdminStaffManagementReviewLevels(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/capacity/locations",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> capacity_getCapacityLocations(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return capacityControllerImpl.getCapacityLocations(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/capacity/reviewers",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> capacity_getCapacityReviewers(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return capacityControllerImpl.getCapacityReviewers(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_REVIEW_LOCATION_ADMIN')")
    @RequestMapping(
        value = "/api/v1/capacity/locations/{locationId}/reviewers",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> capacity_getCapacityLocationReviewers(
        
        @PathVariable("locationId") String locationId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return capacityControllerImpl.getCapacityLocationReviewers(
            locationId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_MONITOR_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/jobs",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_getJobs(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.getJobs(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_MONITOR_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/jobs/pending",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_getJobPending(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.getJobPending(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN') or hasRole('ROLE_MONITOR_READ_ONLY')")
    @RequestMapping(
        value = "/api/v1/jobs/{jobId}",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_getJobByJobId(
        
        @PathVariable("jobId") String jobId,
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.getJobByJobId(
            jobId,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/endorsementSelection",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobEndorsementSelection(
        @RequestBody LrsApiV1DTOs.DateRangeDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobEndorsementSelection(
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/earlyClaimSelection",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobEarlyClaimSelection(
        @RequestBody LrsApiV1DTOs.DateRangeDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobEarlyClaimSelection(
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/earlyPaymentDefaultSelection",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobEarlyPaymentDefaultSelection(
        @RequestBody LrsApiV1DTOs.DateRangeDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobEarlyPaymentDefaultSelection(
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/nationalQcSelection",
        method = RequestMethod.POST,
        consumes ={ MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobNationalQcSelection(
        @RequestBody LrsApiV1DTOs.DateRangeDTO data,
        
        
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobNationalQcSelection(
            data,
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/aggregation",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobAggregation(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobAggregation(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/distribution",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobDistribution(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobDistribution(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/binderRequest",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobBinderRequest(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobBinderRequest(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/binderRequest/throttled",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobBinderRequestThrottled(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobBinderRequestThrottled(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/binderReceipt",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobBinderReceipt(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobBinderReceipt(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/lateBinders",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobLateBinder(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobLateBinder(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/lateLenderRequests",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobLateLenderRequest(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobLateLenderRequest(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/lenderMonitoringEmail",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobLenderMonitoringEmail(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobLenderMonitoringEmail(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/dailyCombinedLenderEmail",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobDailyCombinedLenderEmail(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobDailyCombinedLenderEmail(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/sendEmail",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobSendEmail(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobSendEmail(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/refreshLenders",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobRefreshLender(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobRefreshLender(
            request,
            response
        );
    }
    
    @PreAuthorize("hasRole('ROLE_HQ_ADMIN')")
    @RequestMapping(
        value = "/api/v1/jobs/runPending",
        method = RequestMethod.POST,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public ResponseEntity<?> jobs_postJobRunPending(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
    
        response.setHeader("cache-control", "no-cache, no-store, must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setHeader("expires", "0");
    
        return jobsControllerImpl.postJobRunPending(
            request,
            response
        );
    }
    
}
