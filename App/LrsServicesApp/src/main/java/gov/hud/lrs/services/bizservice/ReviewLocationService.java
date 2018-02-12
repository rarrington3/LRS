package gov.hud.lrs.services.bizservice;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.LocationDTO;
import gov.hud.lrs.common.entity.AssignmentTypeAdmin;
import gov.hud.lrs.common.entity.LocationAssignmentType;
import gov.hud.lrs.common.entity.LocationAssignmentTypeId;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.repository.AssignmentTypeAdminRepository;
import gov.hud.lrs.common.repository.LocationAssignmentTypeRepository;
import gov.hud.lrs.common.repository.ProductTypeRefRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.repository.ReviewTypeRefRepository;
import gov.hud.lrs.common.repository.SelectionReasonRepository;
import gov.hud.lrs.common.security.SecurityService;

@Service
public class ReviewLocationService {

	@Autowired private ReviewLocationRepository reviewLocationRepository;
	@Autowired private LocationAssignmentTypeRepository locationAssignmentTypeRepository;
	@Autowired private AssignmentTypeAdminRepository assignmentTypeAdminRepository;
	@Autowired private SelectionReasonRepository selectionReasonRepository;
	@Autowired private ReviewTypeRefRepository reviewTypeRefRepository;
	@Autowired private ProductTypeRefRepository productTypeRefRepository;

	@Autowired private SecurityService securityService;

	@Transactional
	public ReviewLocation createReviewLocation(LocationDTO locationDTO) {
		ReviewLocation reviewLocation = new ReviewLocation();
		populateLocationFields(reviewLocation, locationDTO);
		reviewLocation = reviewLocationRepository.save(reviewLocation);
		
		createLocationAssignmentType(
			locationDTO.getAllowedSelectionReasons(),
			locationDTO.getAllowedReviewTypes(),
			locationDTO.getAllowedProductTypes(),
			reviewLocation
		);

		return reviewLocation;
	}
	
	@Transactional
	public void updateReviewLocation(String reviewLocationId, LocationDTO locationDTO) {
		List<String> allowedSelectionReasons = locationDTO.getAllowedSelectionReasons();
		List<String> allowedReviewTypes = locationDTO.getAllowedReviewTypes();
		List<String> allowedProductTypes = locationDTO.getAllowedProductTypes();
		
		//delete all locationDTO assignment type, then insert the updated assignment type
		ReviewLocation reviewLocation = reviewLocationRepository.findByReviewLocationId(reviewLocationId);
		// TODO: this code is broken: deletes occur *after* inserts in hibernate
		// the code needs to make per-row updates, this is also important since it preserves CREATED_TS
		locationAssignmentTypeRepository.deleteByReviewLocationReviewLocationId(reviewLocationId);
		createLocationAssignmentType(allowedSelectionReasons, allowedReviewTypes, allowedProductTypes, reviewLocation);
		
		populateLocationFields(reviewLocation, locationDTO);
		if (reviewLocation.getDeactivationDate() != null && locationDTO.getDeActivationDate() == null) {
			reviewLocation.setDeactivationDate(null);
		} else if (reviewLocation.getDeactivationDate() == null && locationDTO.getDeActivationDate() != null) {
			reviewLocation.setDeactivationDate(new Date());
		}
		reviewLocation = reviewLocationRepository.save(reviewLocation);
	}
	
	@Transactional
	private void createLocationAssignmentType(
		List<String> allowedSelectionReasons,
		List<String> allowedReviewTypes,
		List<String> allowedProductTypes,
		ReviewLocation reviewLocation
	) {
		AssignmentTypeAdmin assignedType = null;
		LocationAssignmentType locationAssignmentType = null;
		LocationAssignmentTypeId locationAssignmentTypeId = null;
		String userId = securityService.getUserId();

		if (allowedSelectionReasons != null) {
			for (String selectionReasonCode : allowedSelectionReasons) {
				assignedType =  assignmentTypeAdminRepository.findByAssignmentTypeRefId(selectionReasonRepository.findByCode(selectionReasonCode).getSelectionReasonId());
				locationAssignmentType = new LocationAssignmentType();
				locationAssignmentTypeId = new LocationAssignmentTypeId();
				locationAssignmentTypeId.setReviewLocationId(reviewLocation.getReviewLocationId());
				locationAssignmentTypeId.setAssignmentTypeCd(assignedType.getAssignmentTypeCd());
				locationAssignmentType.setId(locationAssignmentTypeId);
				locationAssignmentType.setCreatedBy(userId);
				locationAssignmentType.setCreatedTs(new Date());
				locationAssignmentTypeRepository.save(locationAssignmentType);
			}
		}
		
		if (allowedReviewTypes != null) {
			for (String reviewTypeCode : allowedReviewTypes) {
				assignedType = assignmentTypeAdminRepository.findByAssignmentTypeRefId(reviewTypeRefRepository.findByReviewTypeCd(reviewTypeCode).getReviewTypeId());
				locationAssignmentType = new LocationAssignmentType();
				locationAssignmentTypeId = new LocationAssignmentTypeId();
				locationAssignmentTypeId.setReviewLocationId(reviewLocation.getReviewLocationId());
				locationAssignmentTypeId.setAssignmentTypeCd(assignedType.getAssignmentTypeCd());
				locationAssignmentType.setId(locationAssignmentTypeId);
				locationAssignmentType.setCreatedBy(userId);
				locationAssignmentType.setCreatedTs(new Date());
				locationAssignmentTypeRepository.save(locationAssignmentType);
			}
		}
		
		if (allowedProductTypes != null) {
			for (String productTypeCode: allowedProductTypes) {
				assignedType = assignmentTypeAdminRepository.findByAssignmentTypeRefId(productTypeRefRepository.findByProductTypeCd(productTypeCode).getProductTypeId());
				locationAssignmentType = new LocationAssignmentType();
				locationAssignmentTypeId = new LocationAssignmentTypeId();
				locationAssignmentTypeId.setReviewLocationId(reviewLocation.getReviewLocationId());
				locationAssignmentTypeId.setAssignmentTypeCd(assignedType.getAssignmentTypeCd());
				locationAssignmentType.setId(locationAssignmentTypeId);
				locationAssignmentType.setCreatedBy(userId);
				locationAssignmentType.setCreatedTs(new Date());
				locationAssignmentTypeRepository.save(locationAssignmentType);
			}
		}
		
	}
	
	private void populateLocationFields(ReviewLocation reviewLocation, LocationDTO locationDTO) {
		reviewLocation.setLocationName(locationDTO.getName());
		reviewLocation.setMailingAddress(locationDTO.getAddress());
		reviewLocation.setMailingAddress2(locationDTO.getAddress2());
		reviewLocation.setMailingCity(locationDTO.getCity());
		reviewLocation.setMailingState(locationDTO.getState());
		reviewLocation.setMailingZip(locationDTO.getZipCode());
		reviewLocation.setContactName(locationDTO.getContactName());
		reviewLocation.setContactPhoneNumber(locationDTO.getContactPhone());
		reviewLocation.setPhoneExtension(locationDTO.getContactPhoneExtension());
		reviewLocation.setCommittedMonthlyCapacity(locationDTO.getCapacity());
		reviewLocation.setCreatedBy(securityService.getUserId());
		reviewLocation.setCreatedTs(new Date());
	}

}
