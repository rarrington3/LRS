package gov.hud.lrs.services.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.DevLoginDTO;
import gov.hud.lrs.common.dto.LrsApiV1DTOs.DevLoginDetailsDTO;
import gov.hud.lrs.services.bizservice.DevToolsService;

@Controller
public class DevLoginControllerImpl {

	@Autowired
	private DevToolsService devToolsService;

	public ResponseEntity<List<DevLoginDetailsDTO>> getDevLogin(HttpServletRequest request, HttpServletResponse response) {
		List<DevLoginDetailsDTO> devLoginDetailsDTOs = devToolsService.findAllUsers();
		return new ResponseEntity<List<DevLoginDetailsDTO>>(devLoginDetailsDTOs, HttpStatus.OK);
	}

	public ResponseEntity<String> postDevLogin(DevLoginDTO data, HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("lrs_dev_userid", data.getUserId());
		response.addCookie(cookie);

		return new ResponseEntity<String>(HttpStatus.OK);
	}
}