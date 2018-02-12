package gov.hud.lrs.services.bizservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import gov.hud.lrs.common.dto.FhacUserDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.DevLoginDetailsDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.LiteLenderDTO;
import gov.hud.lrs.common.entity.Personnel;
import gov.hud.lrs.common.repository.PersonnelRepository;

@Service
public class DevToolsService {

	@Autowired private PersonnelRepository personnelRepository;
	@Autowired private DevMuleService devMuleService;

	public List<DevLoginDetailsDTO> findAllUsers() {
		List<DevLoginDetailsDTO> devLoginDetailsDTOs = new ArrayList<DevLoginDetailsDTO>();

		// reviewers
		List<Personnel> allPersonnel = personnelRepository.findActive();
		for (Personnel personnel : allPersonnel) {
			DevLoginDetailsDTO devLoginDetailsDTO = new DevLoginDetailsDTO();
			devLoginDetailsDTO.setUserId(personnel.getLoginCredential());
			Personnel reportsTo = personnel.getReportsToPersonnel();
			if (reportsTo != null) {
				devLoginDetailsDTO.setReportsToId(reportsTo.getPersonnelId());
				devLoginDetailsDTO.setReportsToName(reportsTo.getFirstName() +
					(StringUtils.isNotBlank(reportsTo.getMiddleName()) ? " " + reportsTo.getMiddleName() + " " : " ") +
					reportsTo.getLastName());
			}
			Personnel vetter = personnel.getVettingPersonnel();
			if (vetter != null) {
				devLoginDetailsDTO.setVetterId(vetter.getPersonnelId());
				devLoginDetailsDTO.setVetterName(vetter.getFirstName() +
					(StringUtils.isNotBlank(vetter.getMiddleName()) ? " " + vetter.getMiddleName() + " " : " ") +
					vetter.getLastName());

			}
			devLoginDetailsDTO.setDescription("");
			FhacUserDTO fhacUserDTO = devMuleService.getFhacUser(devLoginDetailsDTO.getUserId());
			if (fhacUserDTO != null) {
				devLoginDetailsDTO.setAuthorities(fhacUserDTO.getRoles());
				devLoginDetailsDTO.setFirstName(personnel.getFirstName() +
					(StringUtils.isNotBlank(personnel.getMiddleName()) ? " " + personnel.getMiddleName() : ""));
				devLoginDetailsDTO.setLastName(personnel.getLastName());
			}
			devLoginDetailsDTO.setReviewLocation(personnel.getReviewLocation().getLocationName());
			devLoginDetailsDTOs.add(devLoginDetailsDTO);
		}

		// lenders
		List<FhacUserDTO> fhacUserDTOs = devMuleService.getAllFhacUsers();
		for (FhacUserDTO fhacUserDTO : fhacUserDTOs) {
			if (fhacUserDTO.getUserId().charAt(0) == 'M') {
				List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
				for (String role : fhacUserDTO.getRoles()) {
					authorities.add(new SimpleGrantedAuthority(role));
				}

				DevLoginDetailsDTO devLoginDetailsDTO = new DevLoginDetailsDTO();
				devLoginDetailsDTO.setUserId(fhacUserDTO.getUserId());
				devLoginDetailsDTO.setDescription("");
				devLoginDetailsDTO.setAuthorities(fhacUserDTO.getRoles());
				devLoginDetailsDTO.setFirstName(fhacUserDTO.getFirstName());
				devLoginDetailsDTO.setLastName(fhacUserDTO.getLastName());
				LiteLenderDTO liteLenderDTO = new LiteLenderDTO();
				liteLenderDTO.setLenderId(fhacUserDTO.getLenderId());
				devLoginDetailsDTO.setLender(liteLenderDTO);
				devLoginDetailsDTOs.add(devLoginDetailsDTO);
			}
		}

		return devLoginDetailsDTOs;
	}

}
