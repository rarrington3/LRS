package gov.hud.lrs.services.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.FhacUserDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.FHAConnectionReviewerDTO;
import gov.hud.lrs.common.exception.NotFoundException;
import gov.hud.lrs.common.service.FhacUserService;
import gov.hud.lrs.services.util.MessageService;

@Controller
public class FhaConnectionControllerImpl {

	@Autowired private FhacUserService fhacUserService;
	@Autowired private MessageService messageService;

	public ResponseEntity<FHAConnectionReviewerDTO> getFhaConnectionByHudId(String hudId, HttpServletRequest request, HttpServletResponse response) {
		FHAConnectionReviewerDTO fhaConnectionReviewerDTO = new FHAConnectionReviewerDTO();
		FhacUserDTO fhacUserDTO = fhacUserService.getFhacUser(hudId);
		if (fhacUserDTO == null) {
			throw new NotFoundException(messageService.getMessage("error.staffmanagement.fhacmissinguser", hudId));
		}

		fhaConnectionReviewerDTO.setHudId(fhacUserDTO.getUserId());
		fhaConnectionReviewerDTO.setLenderId(fhacUserDTO.getLenderId());
		fhaConnectionReviewerDTO.setNameFirst(fhacUserDTO.getFirstName());
		fhaConnectionReviewerDTO.setNameLast(fhacUserDTO.getLastName());
		// TODO: this is atrocious, we're exposing an internal java type. fix the RAML. should be a plain string.
		List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		for (String role : fhacUserDTO.getRoles()) {
			roles.add(new SimpleGrantedAuthority(role));
		}
		fhaConnectionReviewerDTO.setRoles(roles);
		return new ResponseEntity<FHAConnectionReviewerDTO>(fhaConnectionReviewerDTO, HttpStatus.OK);
	}
}
