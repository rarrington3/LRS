package gov.hud.lrs.services.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.LocationDTO;
import gov.hud.lrs.common.entity.LocationAssignmentType;
import gov.hud.lrs.common.entity.ReviewLocation;
import gov.hud.lrs.common.repository.ProductTypeRefRepository;
import gov.hud.lrs.common.repository.ReviewLocationRepository;
import gov.hud.lrs.common.repository.ReviewTypeRefRepository;
import gov.hud.lrs.common.repository.SelectionReasonRepository;
import gov.hud.lrs.services.bizservice.ReviewLocationService;

@Controller
public class LocationsControllerImpl {
	@Autowired private ReviewLocationRepository reviewLocationRepository;
	@Autowired private SelectionReasonRepository selectionReasonRepository;
	@Autowired private ReviewTypeRefRepository reviewTypeRefRepository;
	@Autowired private ProductTypeRefRepository productTypeRefRepository;

	@Autowired private ReviewLocationService reviewLocationService;

	public ResponseEntity<List<LocationDTO>> getLocations(HttpServletRequest request, HttpServletResponse response) {
		List<ReviewLocation> reviewLocations = reviewLocationRepository.findAll();
		List<LocationDTO> dictReviewLocations = new ArrayList<LocationDTO>();
		for (ReviewLocation reviewLocation : reviewLocations) {
			LocationDTO locationDTO = new LocationDTO();
			locationDTO.setCapacity(reviewLocation.getCommittedMonthlyCapacity());
			locationDTO.setContactName(reviewLocation.getContactName());
			locationDTO.setContactPhone(reviewLocation.getContactPhoneNumber());
			locationDTO.setLocationId(reviewLocation.getReviewLocationId());
			locationDTO.setName(reviewLocation.getLocationName());
			locationDTO.setAddress(reviewLocation.getMailingAddress());
			locationDTO.setAddress2(reviewLocation.getMailingAddress2());
			locationDTO.setCity(reviewLocation.getMailingCity());
			locationDTO.setState(reviewLocation.getMailingState());
			locationDTO.setZipCode(reviewLocation.getMailingZip());

			Set<LocationAssignmentType> locationAssignmentTypes = reviewLocation.getLocationAssignmentTypes();
			List<String> allowedSelectionReasons = new ArrayList<String>();
			List<String> allowedReviewTypes = new ArrayList<String>();
			List<String> allowedProductTypes = new ArrayList<String>();

			pouplateLocationAssignmentTypes(
				locationAssignmentTypes,
				allowedSelectionReasons,
				allowedReviewTypes,
				allowedProductTypes);
			locationDTO.setAllowedSelectionReasons(allowedSelectionReasons);
			locationDTO.setAllowedReviewTypes(allowedReviewTypes);
			locationDTO.setAllowedProductTypes(allowedProductTypes);

			dictReviewLocations.add(locationDTO);
		}
		return new ResponseEntity<List<LocationDTO>>(dictReviewLocations, HttpStatus.OK);
	}


	public ResponseEntity<LocationDTO> putLocation(
		String id,
		LocationDTO location,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		reviewLocationService.updateReviewLocation(id, location);
		return new ResponseEntity<LocationDTO>(location, HttpStatus.OK);
	}

	public ResponseEntity<LocationDTO> getLocationById(String reviewLocationId, HttpServletRequest request, HttpServletResponse response) {
		LocationDTO locationDTO = new LocationDTO();

		ReviewLocation reviewLocation = reviewLocationRepository.findOne(reviewLocationId);
		locationDTO.setLocationId(reviewLocationId);
		locationDTO.setName(reviewLocation.getLocationName());
		locationDTO.setAddress(reviewLocation.getMailingAddress());
		locationDTO.setAddress2(reviewLocation.getMailingAddress2());
		locationDTO.setCity(reviewLocation.getMailingCity());
		locationDTO.setState(reviewLocation.getMailingState());
		locationDTO.setZipCode(reviewLocation.getMailingZip());
		locationDTO.setContactName(reviewLocation.getContactName());
		locationDTO.setContactPhone(reviewLocation.getContactPhoneNumber());
		locationDTO.setContactPhoneExtension(reviewLocation.getPhoneExtension());
		locationDTO.setCapacity(reviewLocation.getCommittedMonthlyCapacity());

		Set<LocationAssignmentType> locationAssignmentTypes = reviewLocation.getLocationAssignmentTypes();
		List<String> allowedSelectionReasons = new ArrayList<String>();
		List<String> allowedReviewTypes = new ArrayList<String>();
		List<String> allowedProductTypes = new ArrayList<String>();

		pouplateLocationAssignmentTypes(
			locationAssignmentTypes,
			allowedSelectionReasons,
			allowedReviewTypes,
			allowedProductTypes);

		locationDTO.setAllowedSelectionReasons(allowedSelectionReasons);
		locationDTO.setAllowedReviewTypes(allowedReviewTypes);
		locationDTO.setAllowedProductTypes(allowedProductTypes);

		return new ResponseEntity<LocationDTO>(locationDTO, HttpStatus.OK);
	}

	private void pouplateLocationAssignmentTypes(
		Set<LocationAssignmentType> locationAssignmentTypes,
		List<String> allowedSelectionReasons,
		List<String> allowedReviewTypes,
		List<String> allowedProductTypes
	) {
		String assignmentTypeCategory = "";
		String assignmentTypeRefId = "";
		for (LocationAssignmentType locationAssignmentType : locationAssignmentTypes) {
			assignmentTypeCategory = locationAssignmentType.getAssignmentTypeAdmin().getAssignmentTypeCategory();
			assignmentTypeRefId = locationAssignmentType.getAssignmentTypeAdmin().getAssignmentTypeRefId();
			// TODO: refactor this atrocious variant FK
			if (assignmentTypeCategory.equals("Selection Reason")) {
				allowedSelectionReasons.add(selectionReasonRepository.findOne(assignmentTypeRefId).getCode());
			} else if (assignmentTypeCategory.equals("Review Type")) {
				allowedReviewTypes.add(reviewTypeRefRepository.findOne(assignmentTypeRefId).getReviewTypeCd());
			} else if (assignmentTypeCategory.equals("Product Type")) {
				allowedProductTypes.add(productTypeRefRepository.findOne(assignmentTypeRefId).getProductTypeCd());
			}
		}

	}
}
