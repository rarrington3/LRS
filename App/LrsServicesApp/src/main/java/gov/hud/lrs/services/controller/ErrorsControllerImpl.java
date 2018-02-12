package gov.hud.lrs.services.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import gov.hud.lrs.common.dto.LrsApiV1DTOs.ErrorDTO;

@Controller
public class ErrorsControllerImpl {

	private static Logger logger = LoggerFactory.getLogger("gov.hud.lrs.ui.error");

	public ResponseEntity<String> postError(ErrorDTO uiErrorDTO, HttpServletRequest request, HttpServletResponse response) {
	
		StringBuilder sb = new StringBuilder("errorId: " + uiErrorDTO.getErrorId() + "\n");
		sb.append("\terrorCode: " + uiErrorDTO.getErrorCode() + "\n");
		sb.append("\tmessage: " + uiErrorDTO.getMessage() + "\n");
		sb.append("\turl: " + uiErrorDTO.getUrl() + "\n");
		for (String errorLog : uiErrorDTO.getLogHistory()) {
			sb.append("\t" + errorLog + "\n");
		}
		logger.error(sb.toString());
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
